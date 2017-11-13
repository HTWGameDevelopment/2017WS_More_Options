package com.moreoptions.prototype.gameEngine.exceptions;

public class MissdefinedTileException extends Throwable {
    public MissdefinedTileException(String no_blocked_flag_set) {
        super(no_blocked_flag_set);
    }
}
