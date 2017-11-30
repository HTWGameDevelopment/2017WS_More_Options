package com.moreoptions.prototype.gameEngine.data.ai;

import com.badlogic.ashley.core.Entity;

/**
 * Created by Andreas on 30.11.2017.
 */

public class Node {

    private float x;
    private float y;

    private boolean blocked;

    private Entity blocker;

    public Node(float x, float y) {
        this.x = x;
        this.y = y;
        this.blocked = false;
        this.blocker = null;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Entity getBlocker() {
        return blocker;
    }

    public void setBlocker(Entity blocker) {
        this.blocker = blocker;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
