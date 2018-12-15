package com.nameless.game.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.nameless.game.MainGame;
import com.nameless.game.VirtualController;
import com.nameless.game.screens.Play;

public class HudPC extends Hud implements InputProcessor {
    public HudPC(MainGame game, VirtualController controller, Play playScreen) {
        super(game, controller, playScreen);
    }

    @Override
    public boolean keyDown(int keycode) {
        controller.mouseMoving = false;
        if(keycode == Input.Keys.W)
            controller.MovePercentY = 1;
        if(keycode == Input.Keys.A)
            controller.MovePercentX = -1;
        if(keycode == Input.Keys.S)
            controller.MovePercentY = -1;
        if(keycode == Input.Keys.D)
            controller.MovePercentX = 1;
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        controller.shoot = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.mouseMoving = false;
        if((controller.MovePercentX == 0 && controller.MovePercentY == 0) || controller.shoot){
            float angle = com.badlogic.gdx.math.MathUtils.atan2(screenY - Gdx.app.getGraphics().getHeight()/2,
                    screenX - Gdx.app.getGraphics().getWidth()/2) * MathUtils.radDeg;
            playScreen.player.setRotation(-angle);
            controller.mouseMoving = true;
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
