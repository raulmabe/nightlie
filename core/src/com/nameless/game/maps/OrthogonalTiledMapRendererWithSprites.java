package com.nameless.game.maps;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.nameless.game.Constants;
import com.nameless.game.DayNightCycleManager;

import static com.nameless.game.Constants.PixelsPerMeter;

/**
 * Created by Raul on 27/06/2017.
 */
public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {

    float unitScale = 1f;

    private Color auxColor;

    public OrthogonalTiledMapRendererWithSprites(TiledMap map) {
        super(map);
    }

    public OrthogonalTiledMapRendererWithSprites(TiledMap map, float unitScale) {
        super(map, unitScale);
        this.unitScale = unitScale;
        auxColor = new Color();
    }


    @Override
    public void renderObject(MapObject object) {
        if (object instanceof TextureMapObject) {
            TextureMapObject textureObj = (TextureMapObject) object;
            batch.draw(textureObj.getTextureRegion(), textureObj.getX()*unitScale, textureObj.getY()*unitScale,textureObj.getOriginX(), textureObj.getOriginY(),
                    textureObj.getTextureRegion().getRegionWidth()*unitScale, textureObj.getTextureRegion().getRegionHeight()*unitScale,
                    textureObj.getScaleX(), textureObj.getScaleY(), -textureObj.getRotation());
        }
    }

    /**
     * Renders the objects changing its color to darker or lighter depending on the x
     * @param x  Day/Night cycle
     */
    public void renderLightsObject(MapLayer layer, float x){
        auxColor.set(batch.getColor());
        batch.setColor(batch.getColor().mul(x, x, x,1 ));

        for (MapObject object : layer.getObjects()) {
            if (object instanceof TextureMapObject) {
                TextureMapObject textureObj = (TextureMapObject) object;
                batch.draw(textureObj.getTextureRegion(), textureObj.getX()*unitScale, textureObj.getY()*unitScale,textureObj.getOriginX(), textureObj.getOriginY(),
                        textureObj.getTextureRegion().getRegionWidth()*unitScale, textureObj.getTextureRegion().getRegionHeight()*unitScale,
                        textureObj.getScaleX(), textureObj.getScaleY(), -textureObj.getRotation());
            }
        }

        batch.setColor(auxColor);
    }
}