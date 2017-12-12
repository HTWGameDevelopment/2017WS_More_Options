package com.moreoptions.prototype.gameEngine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TileGraphicComponent implements Component {

    private TextureRegion textureRegion;
    private int zindex;

    public TileGraphicComponent(TextureRegion textureRegion, int zindex) {
        this.textureRegion = textureRegion;
        this.zindex = zindex;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public int getZIndex() {
        return zindex;
    }
}
