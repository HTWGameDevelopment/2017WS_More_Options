package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.moreoptions.prototype.gameEngine.data.Statistics;

public class StatsComponent implements Component {

    private Statistics stats;

    public StatsComponent(Statistics stats) {
        this.stats = stats;
    }

    public StatsComponent() {
        stats = new Statistics();
    }

    public Statistics getStats() {
        return stats;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }
}
