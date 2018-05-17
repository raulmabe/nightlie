package com.nameless.game;

public class DayNightCycleManager {

    /**
     * Day/Night time
     */
    public static float dayTime = 1;
    public static boolean lightsOpen = false;
    private boolean sum = false;

    public DayNightCycleManager() {
    }

    public void updateDayTime() {
        if(sum) dayTime += .00001f;
        else dayTime -= .00001f;

        if(dayTime >= 1) sum = false;
        else if (dayTime <= .2f) sum = true;

        if(dayTime > 0.5f) lightsOpen = false;
        else lightsOpen = true;
    }

}
