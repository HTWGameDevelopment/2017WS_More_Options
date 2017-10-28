package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by denwe on 28.10.2017.
 */
public class CollisionComponent implements Component{

    private Shape shape;
    private int size;


    public CollisionComponent(Shape shape, int size) {
        this.shape = shape;
        this.size = size;
    }

    public Shape getShape() {
        return shape;
    }

    public int getSize() {
        return size;
    }


    public enum Shape {
        RECTANGLE, CIRCLE
    }


}
