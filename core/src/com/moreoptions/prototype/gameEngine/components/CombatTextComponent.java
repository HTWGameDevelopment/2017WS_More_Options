package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by denwe on 09.11.2017.
 */
public class CombatTextComponent implements Component {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CombatTextComponent(String text) {
        this.text = text;
    }
}
