package com.moreoptions.prototype.gameEngine.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by denwe on 24.12.2017.
 */
public class QuadTree {

    private int MAX_OBJECTS = 3;
    private int MAX_LEVELS = 100;

    private int level;
    private ArrayList<Entity> objects;
    private Rectangle bounds;
    private QuadTree[] nodes;


    /*
     * Constructor
     */
    public QuadTree(int pLevel, Rectangle pBounds) {
        level = pLevel;
        objects = new ArrayList();
        bounds = pBounds;
        nodes = new QuadTree[4];
    }

    public void clear() {
        objects.clear();

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    private void split() {
        int subWidth = (int)(bounds.getWidth() / 2);
        int subHeight = (int)(bounds.getHeight() / 2);
        int x = (int)bounds.getX();
        int y = (int)bounds.getY();

        nodes[0] = new QuadTree(level+1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new QuadTree(level+1, new Rectangle(x, y, subWidth, subHeight));
        nodes[2] = new QuadTree(level+1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new QuadTree(level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
    }

    private int getIndex(Entity e ) {
        Rectangle pRect = null;
        try {
            pRect = EntityTools.getBoundingRectangle(e);
            int index = -1;
            double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
            double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

            // Object can completely fit within the top quadrants
            boolean topQuadrant = (pRect.getY() < horizontalMidpoint && pRect.getY() + pRect.getHeight() < horizontalMidpoint);
            // Object can completely fit within the bottom quadrants
            boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);

            // Object can completely fit within the left quadrants
            if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint) {
                if (topQuadrant) {
                    index = 1;
                } else if (bottomQuadrant) {
                    index = 2;
                }
            }
            // Object can completely fit within the right quadrants
            else if (pRect.getX() > verticalMidpoint) {
                if (topQuadrant) {
                    index = 0;
                } else if (bottomQuadrant) {
                    index = 3;
                }
            }

            return index;
        } catch (EntityToolsException e1) {
            e1.printStackTrace();
        }
        return -1;

    }

    public void insert(Entity entity) {
        try {
            Rectangle pRect = EntityTools.getBoundingRectangle(entity);

            if (nodes[0] != null) {             //Wenn Unterbäume existieren
                int index = getIndex(entity);    //Dann bestimme Index.

                if (index != -1) {              //Wenn Entity in einen Unterbaum reinpasst
                    nodes[index].insert(entity); //Versuche hinzuzufügen

                    return;
                }
            }

            //Wenn kein Unterbaum existiert, füge hinzu
            objects.add(entity);

            //Überprüfe danach, ob Baum gesplittet werden muss
            if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
                if (nodes[0] == null) {
                    split();
                }

                int i = 0;
                //Füge Objekte, die in die Unterbäume passen, den Unterbäumen hinzu.
                while (i < objects.size()) {
                    int index = getIndex(objects.get(i));
                    if (index != -1) {
                        nodes[index].insert(objects.remove(i));
                    }
                    //Behalte Objekte im derzeitigen Baum, falls diese nicht in Unterbaum passen.
                    else {
                        i++;
                    }
                }
            }
        } catch (EntityToolsException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Entity> retrieve(ArrayList<Entity> returnObjects, Entity e) {
        int index = getIndex(e);
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnObjects, e);
        }

        returnObjects.addAll(objects);

        return returnObjects;
    }

    public void visualize(ShapeRenderer renderer) {

        renderer.setColor(Color.FOREST);

        renderer.rect(bounds.x,bounds.y, bounds.width, bounds.height);

        renderer.setColor(Color.YELLOW);
        for(Entity e : objects) {
            Rectangle r = null;
            try {
                r = EntityTools.getBoundingRectangle(e);
            } catch (EntityToolsException e1) {
                e1.printStackTrace();
            }
            renderer.rect(r.x,r.y, r.width, r.height);
        }



        for(QuadTree q : nodes) {
            if(q != null) q.visualize(renderer);
        }
    }

    public void drawNumbers(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "" + objects.size(), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
        for(QuadTree q : nodes) {
            if(q != null) q.drawNumbers(batch,font);
        }
    }
}
