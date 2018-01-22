package com.moreoptions.prototype.gameEngine.systems;

import com.badlogic.ashley.core.Component;
import javafx.scene.paint.Color;

/**
 * Created by denwe on 11.01.2018.
 */
public class ProgressComponent implements Component {

    float progress = 0;
    Color color = Color.LAVENDER;

    public ProgressComponent() {

    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
