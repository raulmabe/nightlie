package com.nameless.game;

public class DayNightCycleManager {

    /**
     * Day/Night time
     */
    public static float dayTime = 1f;
    public static boolean lightsOpen = false;
    public static boolean sum = false;

    private static float timeSpeed = 0.00001f;

    public DayNightCycleManager() {
    }

    public void updateDayTime() {
        if(sum) dayTime += timeSpeed;
        else dayTime -= timeSpeed;

        if(dayTime >= 1) sum = false;
        else if (dayTime <= .2f) sum = true;

        if(dayTime > 0.5f) lightsOpen = false;
        else lightsOpen = true;
    }

}
