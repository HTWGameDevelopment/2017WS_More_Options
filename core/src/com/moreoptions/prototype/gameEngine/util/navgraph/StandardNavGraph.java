package com.moreoptions.prototype.gameEngine.util.navgraph;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.components.*;
import com.moreoptions.prototype.gameEngine.data.pathfinding.Path;
import com.moreoptions.prototype.gameEngine.util.CollisionUtil;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by denwe on 25.12.2017.
 */
public class StandardNavGraph implements NavGraph {

    private ArrayList<Entity>   entities = new ArrayList<Entity>(); //The entities contained inside the navgraph.
    private ArrayList<Node>     nodes = new ArrayList<Node>();      //The nodes the graph is made up of.
    private ArrayList<Edge>     edges = new ArrayList<Edge>();      //The edges connecting the nodes.
    private ArrayList<Path>     paths = new ArrayList<Path>();      //Generated paths that can be used for caching later on.
    private HashMap<Entity,Obstacle> obstacles = new HashMap();

    private ComponentMapper<SquareCollisionComponent> sqcMapper = ComponentMapper.getFor(SquareCollisionComponent.class);
    private ComponentMapper<CircleCollisionComponent> ccc = ComponentMapper.getFor(CircleCollisionComponent.class);
    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<CollisionComponent> colMapper = ComponentMapper.getFor(CollisionComponent.class);
    private ComponentMapper<NavigationComponent> navMapper = ComponentMapper.getFor(NavigationComponent.class);
    private ComponentMapper<ObstacleComponent> obsMapper = ComponentMapper.getFor(ObstacleComponent.class);



    /**
     * Adds an entity to the navgraph.
     *
     * @param e
     */
    @Override
    public boolean addEntity(Entity e) {

        if(entities.contains(e)) return false;
        if(obsMapper.has(e)) return addObstacle(e); //Obstacles add a C-Spaced pathblocking rectangle to the navgraph
        if(ccc.has(e)) return addNodeForEntity(e);






        return false;

    }

    private boolean addObstacle(Entity e) {

        Obstacle o = new Obstacle(5,e);
        ObstacleComponent oc = obsMapper.get(e);

        ArrayList<Node> nodes = o.getNodes();
        obstacles.put(e,o);
        for(Node n : nodes) {
            connectNode(n);
        }


        for(Edge edge: edges) {
            if(edgeIntersectsObstacles(edge,o)) {
                edge.addBlocker(e);
                oc.addEdge(edge);

            }
        }
        e.add(oc);

        return true;


    }

    private boolean addNodeForEntity(Entity e) {
        PositionComponent pos = posMapper.get(e);
        NavigationComponent nav = navMapper.get(e);

        Node n = new Node(pos.getX(),pos.getY());
        nav.setNode(n);

        connectNode(n);
        return true;
    }

    private void connectNode(Node n) {
        for(Node graphNode : nodes) {
            Edge e = createEdge(n, graphNode);  //For this graph, all nodes are considered connected. Paths might be blocked though.
            edges.add(e);
        }
        nodes.add(n);
    }

    private Edge createEdge(Node n, Node graphNode) {

        Edge edge = new Edge(n, graphNode);

        for(Map.Entry<Entity, Obstacle> entry : obstacles.entrySet() ) {
            Rectangle r = entry.getValue();
            Entity e = entry.getKey();
            if(edgeIntersectsObstacles(edge,r)) {
                edge.addBlocker(e);
            }

        }
        return edge;
    }

    private boolean edgeIntersectsObstacles(Edge edge, Rectangle obstacleBoundingBox) {
        return CollisionUtil.intersectSegmentRectangle(edge.getStartNode().getX(),edge.getStartNode().getY(), edge.getEndNode().getX(), edge.getEndNode().getY(), obstacleBoundingBox);
    }

    @Override
    public boolean removeEntity(Entity e) {
        if(obstacles.containsKey(e)) {
            for(Node n : obstacles.get(e).getNodes()) {
                disconnectNode(n);
            }
        }

        obstacles.remove(e);

        if(navMapper.has(e)) {
            NavigationComponent nc = navMapper.get(e);
            Node n = nc.getNode();
            if(n != null) disconnectNode(n);
        }



        return false;
    }

    private void disconnectNode(Node n) {
        if(n.getNeighbors() != null)
        for(Node q : n.getNeighbors()) {
            q.removeNeighbor(n);
        }

        ArrayList<Edge> edge = new ArrayList<Edge>();
        for(Edge e : edges) {

            if(e.endNode == n) {
                edge.add(e);
            }
            if(e.startNode == n) {
                edge.add(e);
            }
        }

        edges.removeAll(edge);
        nodes.remove(n);


        System.out.println("Disconnecting nodes");

    }

    @Override
    public boolean updateEntity(Entity e) {

        //Need case for obstacles. They dont move right now. But they might.
        NavigationComponent nc = navMapper.get(e);
        nc.getNode();



        return false;
    }

    @Override
    public Path getPath(Entity start, Entity target) {
        return null;
    }

    @Override
    public Path getPath(Entity start, float endX, float endY) {
        return null;
    }

    @Override
    public Path getPath(float startX, float startY, float endX, float endY) {
        return null;
    }

    @Override
    public Path getPath(float startX, float startY, Entity target) {
        return null;
    }

    @Override
    public void draw(ShapeRenderer renderer, SpriteBatch batch, BitmapFont font) {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        for(Node n: nodes) {
            renderer.circle(n.getX(),n.getY(), 2);
        }

        for(Edge e: edges) {
            if(e.blockingEntities.size() == 0)
            renderer.line(e.startNode.getX(),e.getStartNode().getY(),e.getEndNode().getX(),e.getEndNode().getY());
        }
        renderer.end();

        batch.begin();
        font.draw(batch, "Nodes: " +nodes.size(), 0,25);
        font.draw(batch, "Edges: " +edges.size(), 0,50);
        font.draw(batch, "Entities: " +entities.size(), 0,100);

        batch.end();

        System.out.println(obstacles.size());

    }

    private class NavGraphException extends Throwable {
        public NavGraphException(String s) {

        }
    }
}
