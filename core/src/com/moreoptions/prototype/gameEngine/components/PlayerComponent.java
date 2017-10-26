package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;

/**
 * Component to map an Entity to a Player.
 *
 * id = 0 | Player 1
 * id = 1 | Player 2
 */
public class PlayerComponent implements Component {

    private int id;

    public PlayerComponent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
