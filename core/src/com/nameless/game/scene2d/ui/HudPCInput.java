package com.nameless.game.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.nameless.game.MainGame;
import com.nameless.game.VirtualController;
import com.nameless.game.screens.BasicPlay;
import com.nameless.game.screens.Play;
import net.dermetfan.utils.math.MathUtils;

public class HudPCInput extends Hud implements InputProcessor {
    public HudPCInput(MainGame game, BasicPlay playScreen) {
        super(game, playScreen);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.W){
            controller.MovePercentY = 1;
            if(!controller.shoot){
                controller.TurnPercentX = 0;
                controller.TurnPercentY = 0;
            }
        }
        if(keycode == Input.Keys.A){
            controller.MovePercentX = -1;
            if(!controller.shoot){
                controller.TurnPercentX = 0;
                controller.TurnPercentY = 0;
            }
        }
        if(keycode == Input.Keys.S){
            controller.MovePercentY = -1;
            if(!controller.shoot){
                controller.TurnPercentX = 0;
                controller.TurnPercentY = 0;
            }
        }
        if(keycode == Input.Keys.D){
            controller.MovePercentX = 1;
            if(!controller.shoot){
                controller.TurnPercentX = 0;
                controller.TurnPercentY = 0;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.W && controller.MovePercentY == 1)
            controller.MovePercentY = 0;
        if(keycode == Input.Keys.A && controller.MovePercentX == -1)
            controller.MovePercentX = 0;
        if(keycode == Input.Keys.S && controller.MovePercentY == -1)
            controller.MovePercentY = 0;
        if(keycode == Input.Keys.D && controller.MovePercentX == 1)
            controller.MovePercentX = 0;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        controller.shoot = true;
        mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        controller.shoot = false;
        controller.TurnPercentX = 0;
        controller.TurnPercentY = 0;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if(!(controller.MovePercentX == 0 && controller.MovePercentY == 0)) {
            controller.TurnPercentX = 0;
            controller.TurnPercentY = 0;
        }
        if((controller.MovePercentX == 0 && controller.MovePercentY == 0) || controller.shoot){
            /*
            float angle = com.badlogic.gdx.math.MathUtils.atan2(screenY - Gdx.app.getGraphics().getHeight()/2,
                    screenX - Gdx.app.getGraphics().getWidth()/2) * MathUtils.radDeg;
            playScreen.player.setRotation(-angle);
            */
            Vector2 vec = new Vector2(screenX - Gdx.app.getGraphics().getWidth()/2, screenY - Gdx.app.getGraphics().getHeight()/2);
            vec = vec.nor();
            controller.TurnPercentX = vec.x;
            controller.TurnPercentY = -vec.y;
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
