package com.nameless.game.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.nameless.game.MainGame;
import com.nameless.game.VirtualController;
import com.nameless.game.screens.BasicPlay;
import com.nameless.game.screens.Play;

public class HudMobileInput extends Hud {

    private Touchpad touchpad, touchpad2;

    public HudMobileInput(MainGame game, Play playScreen) {
        super(game, playScreen);

        touchpad2 = new Touchpad(10, game.getSkin());
        touchpad2.setBounds(hud.getViewport().getWorldWidth() - 300 - 35, 15, 300, 300);
        touchpad2.setColor(touchpad2.getColor().r, touchpad2.getColor().g, touchpad2.getColor().b, 1);
        hud.addActor(touchpad2);

        touchpad = new Touchpad(10, game.getSkin());
        touchpad.setBounds(35, 15, 300, 300);
        touchpad.setColor(touchpad.getColor().r, touchpad.getColor().g, touchpad.getColor().b, 1);
        hud.addActor(touchpad);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        controller.MovePercentX = touchpad.getKnobPercentX();
        controller.MovePercentY = touchpad.getKnobPercentY();

        controller.TurnPercentX = touchpad2.getKnobPercentX();
        controller.TurnPercentY = touchpad2.getKnobPercentY();
        controller.shoot = (touchpad2.getKnobPercentX() != 0 || touchpad2.getKnobPercentY() != 0);
    }
}
