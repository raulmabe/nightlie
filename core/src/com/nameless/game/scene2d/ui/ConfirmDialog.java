package com.nameless.game.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Raul on 13/06/2017.
 */

public class ConfirmDialog extends CommonDialog {

    private ConfirmCallback callback = null;

    public ConfirmDialog(Skin skin, String text, String okText, String cancelText) {
        super(skin, text);
        button(okText, "ok").button(cancelText, "cancel");

    }

    public void setCallback(ConfirmCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void result(Object object) {
        if (callback != null) {
            if (object.equals("ok")) {
                callback.ok();
            } else if (object.equals("cancel")) {
                callback.cancel();
            }
        }

        cancel();
        hide(null);
    }

    /**
     * This is the callback class that will subscribe to events happening in
     * this confirmation dialog class.
     */
    public interface ConfirmCallback {

        /**
         * This event is triggered when the OK button is pressed.
         */
        void ok();

        /**
         * This event is triggered when the CANCEL button is pressed.
         */
        void cancel();
    }
}