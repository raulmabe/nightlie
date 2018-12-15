package com.nameless.game;


public class VirtualController {

    // static variable single_instance of type Singleton
    private static VirtualController single_instance = null;

    public boolean shoot = false;
    public long lastTimeShot = 0;

    public boolean Light_on = true;

    public float MovePercentX = 0f;
    public float MovePercentY = 0f;

    public float TurnPercentX = 0f;
    public float TurnPercentY = 0f;

    public float CamX = 0;
    public float CamY = 0;

    public static int ACTUAL_WEAPON = 5;

    // static method to create instance of Singleton class
    public static VirtualController getInstance()
    {
        if (single_instance == null)
            single_instance = new VirtualController();

        return single_instance;
    }
}