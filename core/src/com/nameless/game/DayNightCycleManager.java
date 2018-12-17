package com.nameless.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;

public class DayNightCycleManager implements IObserver{


    // static variable single_instance of type Singleton
    private static DayNightCycleManager single_instance = null;

    /**
     * Day/Night time
     */
    public static float dayTime = 1f;
    public static boolean lightsOpen = false;
    public static boolean sum = false;

    public DayNightCycleManager() {
        dayTime = 1f;
    }

    /*
    public void updateDayTime() {
        if(sum) dayTime += timeSpeed;
        else dayTime -= timeSpeed;

        if(dayTime >= 1) sum = false;
        else if (dayTime <= .2f) sum = true;

        if(dayTime > 0.5f) lightsOpen = false;
        else lightsOpen = true;
    }*/

    private void makeSunrise(){
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                dayTime += 0.05f;
                dayTime = MathUtils.clamp(dayTime, .15f,1);
                lightsOpen = dayTime < 0.5f;
            }
        }, 0, .5f, 20);
    }

    private void makeSunset(){
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                dayTime -= 0.05f;
                dayTime = MathUtils.clamp(dayTime, .15f,1);
                lightsOpen = dayTime < 0.5f;
            }
        }, 0, .5f, 20);
    }

    @Override
    public void handleMessage(Object o, ISubject.type type) {
        Timer.instance().clear();
        if(type == ISubject.type.ROUND_FINNISH) makeSunrise();
        else if(type == ISubject.type.ROUND_START) makeSunset();
    }

    // static method to create instance of Singleton class
    public static DayNightCycleManager getInstance()
    {
        if (single_instance == null)
            single_instance = new DayNightCycleManager();

        return single_instance;
    }
}
