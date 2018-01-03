package com.moreoptions.prototype.gameEngine.data.pathfinding;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.Consts;
import com.moreoptions.prototype.gameEngine.data.exceptions.NoValidComponentException;
import com.moreoptions.prototype.gameEngine.util.CollisionUtil;

import java.util.*;

/**
 * Created by denwe on 02.12.2017.
 */
public class NavGraph {

    ArrayList<Node> nodes = new ArrayList<Node>();          //All nodes we have
    ArrayList<Entity> entities = new ArrayList<Entity>();     //All entities in our navgraph
    ArrayList<CSpaceRectangle> cSpaceRectangles = new ArrayList<CSpaceRectangle>();

    Random r = new Random();
    FPSLogger log = new FPSLogger();


    ComponentMapper<PositionComponent> cmp = ComponentMapper.getFor(PositionComponent.class);



    public NavGraph() {


    }

    public boolean addEntity(Entity entity) {

        ComponentMapper<EnemyComponent> ecm = ComponentMapper.getFor(EnemyComponent.class);
        ComponentMapper<PlayerComponent> pcm = ComponentMapper.getFor(PlayerComponent.class);
        ComponentMapper<InnerTileComponent> icm = ComponentMapper.getFor(InnerTileComponent.class);

        if(icm.has(entity)) {
            CSpaceRectangle c = new CSpaceRectangle(5, entity, entity.getComponent(SquareCollisionComponent.class).getHitbox());
            cSpaceRectangles.add(c);
            updateNodes(c);

            for(Node n : c.getNodes()) {
                connectNode(n);
            }
            updateBlockedNodes();
        }
        return true;
    }

    private void updateNodes(CSpaceRectangle c) {
        ArrayList<Node> toBeRemoved = new ArrayList<Node>();

        for(Node n : nodes) {
            toBeRemoved.clear();
            for(Node neighbor : n.getNeighbours()) {
                if(CollisionUtil.intersectSegmentRectangle(n.getX(),n.getY(), neighbor.getX(),neighbor.getY(), c)) {
                    toBeRemoved.add(neighbor);
                }
            }

            for(Node remove : toBeRemoved) {
                n.getNeighbours().remove(remove);
            }
        }

    }

    private void updateBlockedNodes() {

        for(Node n : nodes) {
            boolean blocked = false;
            for(CSpaceRectangle c : cSpaceRectangles) {
                if(c.contains(n.getX(),n.getY())) {
                    blocked = true;
                }
            }
            n.setBlocked(blocked);
        }

    }

    private void connectNode(Node n) {
        for(Node all : nodes) {
            boolean connected = true;
            for(CSpaceRectangle c : cSpaceRectangles) {
                if(CollisionUtil.intersectSegmentRectangle(n.getX(),n.getY(),all.getX(),all.getY(),c)) {
                    connected = false;
                    break;
                }
            }
            if(connected) {
                all.addNeighbor(n);
                n.addNeighbor(all);
            }
        }
        nodes.add(n);
    }

    private boolean removeEntity(Entity entity) {
        if(!entities.contains(entity)) return false;
        return true;
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(Color.BLUE);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        for(Node n : nodes) {
                renderer.circle(n.getX(),n.getY(),10);
                for(Node k : n.getNeighbours()) {
                    renderer.line(n.getX(),n.getY(),k.getX(),k.getY());
                }
        }

        renderer.end();
    }


    public Path getPath(float startX, float startY, float endX, float endY) {

        float startTime = System.nanoTime();

        ArrayList closedSet = new ArrayList();
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {

                if(o1.getPriority() < o2.getPriority()) return -1;
                if(o1.getPriority() > o2.getPriority()) return 1;

                return 0;
            }
        });

        Node start = new Node(startX,startY);
        start.setCost(0);
        closedSet.add(start);
        nodePriorityQueue.add(start);
        Node end = new Node(endX,endY);

        connectNode(start);
        connectNode(end);

        while(!nodePriorityQueue.isEmpty()) {
            Node current = nodePriorityQueue.poll();
            if(current == end) break;
            for(Node neighbor : current.getNeighbours()) {
                float cost = (float) (current.getCost() + getDistanceBetweenNodes(current, neighbor));
                if(!closedSet.contains(neighbor) || cost < neighbor.getCost()) {
                    closedSet.add(neighbor);
                    neighbor.setCost((float) cost);
                    neighbor.setPriority(cost + getDistanceBetweenNodes(neighbor, end));
                    nodePriorityQueue.add(neighbor);
                    neighbor.cameFrom(current);
                }
            }
        }

        Node k = end;
        ArrayList<Node> stack = new ArrayList<Node>();

        while(k.getCameFrom() != null) {
            k.setMarked(true);
            stack.add(k);
            k = k.getCameFrom();
        }

        float endTime = System.nanoTime();

        //System.out.println("It took "+ (endTime-startTime) / 1000000 + " milliseconds to calculate a path between " +nodes.size() + " +nodes");


        removeNode(start);
        removeNode(end);
        Collections.reverse(stack);
        Path p = new Path(stack,this);
        return p;

    }

    private void removeNode(Node start) {

        for(Node n : start.getNeighbours()) {
            n.getNeighbours().remove(start);
        }

        nodes.remove(start);

        start.getNeighbours().clear();

    }

    private double getDistanceBetweenNodes(Node a, Node b) {
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }


    public Path getPath(Entity self, Entity player) throws NoValidComponentException {
        if(!cmp.has(self)|| !cmp.has(player)) throw new NoValidComponentException("None of the given entities has a positioncomponent");

        PositionComponent selfPos = cmp.get(self);
        PositionComponent theirPos = cmp.get(player);

        return getPath(selfPos.getX(),selfPos.getY(), theirPos.getX(), theirPos.getY());


    }

    public boolean checkPath(Path path) {
        //TODO impl
        return false;
    }

    public Vector2 getRandomPosition(Entity self) {


        Node n = createRandomUnblockedNode();
        return new Vector2(n.getX(), n.getY());

    }

    private Node createRandomUnblockedNode() {

        Node n = new Node(0,0);
        connectNode(n);
        n.setBlocked(true);

        Random rand = new Random();
        while(n.isBlocked()) {
            removeNode(n);
            n.setX(rand.nextInt(Consts.GAME_WIDTH - Consts.TILE_SIZE * 4) + Consts.TILE_SIZE );
            n.setY(rand.nextInt(Consts.GAME_HEIGHT - Consts.TILE_SIZE * 4) + Consts.TILE_SIZE );
            connectNode(n);
            updateBlockedNodes();
        }
        removeNode(n);
        return n;

    }
}
