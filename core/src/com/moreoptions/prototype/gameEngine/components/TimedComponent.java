package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by denwe on 03.11.2017.
 */
public class TimedComponent implements Component {

    float currentTime = 0;
    float time = 0;

    /**
     * Creates a TimedComponent. If added to an entity causes it to destroy after {@link float}seconds.
     *
     * @param timeInSeconds Time to deletion in seconds.
     */
    public TimedComponent(float timeInSeconds) {
        this.time = timeInSeconds;
    }

    public float getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
