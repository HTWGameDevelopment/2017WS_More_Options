package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.moreoptions.prototype.gameEngine.data.Player;

/**
 * Component to map an Entity to a Player.
 * Component indicated that this entity is either controlled by or belongs to a player
 *
 */
public class PlayerComponent implements Component {

    private Player player;

    public PlayerComponent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
