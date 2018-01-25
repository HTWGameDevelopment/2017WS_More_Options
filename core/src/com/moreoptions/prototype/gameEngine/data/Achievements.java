package com.moreoptions.prototype.gameEngine.data;

/**
 * Created by denwe on 18.01.2018.
 */
public class Achievements {

    boolean killingMachine = false;
    boolean explorer = false;
    boolean bountyhunter = false;
    boolean bosskiller = false;

    @Override
    public String toString() {
        return ("km" + killingMachine + "ex" +  explorer + "bh"+ bountyhunter + "bk" + bosskiller);
    }
}
