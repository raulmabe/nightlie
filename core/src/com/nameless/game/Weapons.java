package com.nameless.game;

public class Weapons {

    /**
     * Number of weapons available and index of each one
     */
    public static final int NUMB_WEAPONS = 6;
    public static final int NONE = 0;
    public static final int PISTOL = 1;
    public static final int UZI = 2;
    public static final int SHOTGUN = 3;
    public static final int GRENADE = 4;
    public static final int ROCKET = 5;

    /**
     * Delay between shots
     */
    public static int PISTOL_DELAY = 350;
    public static int UZI_DELAY = 200;
    public static int SHOTGUN_DELAY = 500;
    public static int GRENADE_DELAY = 300;
    public static int ROCKET_DELAY = 800;

    /**
     * Damage
     */
    public static int PISTOL_DAMAGE = 50;
    public static int UZI_DAMAGE = 25;
    public static int SHOTGUN_DAMAGE = 10;
    public static int GRENADE_DAMAGE = 5;
    public static int ROCKET_DAMAGE = 200;


    /**
     * Amunnation capacity
     */
    public static int PISTOL_CAPACITY = 500 ; //50;
    public static int UZI_CAPACITY = 500 ; //100;
    public static int SHOTGUN_CAPACITY = 500 ; //10;
    public static int GRENADE_CAPACITY = 500 ; //10;
    public static int ROCKET_CAPACITY = 500 ; //10;

    /**
     * Weapons Level
     */
    public static int PISTOL_LVL = 1;
    public static int UZI_LVL = 0;
    public static int SHOTGUN_LVL = 0;
    public static int GRENADE_LVL = 0;
    public static int ROCKET_LVL = 0;

    /**
     * String names
     */
    public static final String PISTOL_STRING = "PISTOL";
    public static final String UZI_STRING = "UZI";
    public static final String SHOTGUN_STRING = "SHOTGUN";
    public static final String GRENADE_STRING = "GRENADE";
    public static final String ROCKET_STRING = "ROCKET";

    public static void LevelUp(int i){
        switch (i){
            case Weapons.ROCKET:
                Weapons.ROCKET_LVL++;
                break;
            case Weapons.GRENADE:
                Weapons.GRENADE_LVL++;
                break;
            case Weapons.SHOTGUN:
                Weapons.SHOTGUN_LVL++;
                break;
            case Weapons.UZI:
                Weapons.UZI_LVL++;
                break;
            case Weapons.PISTOL:
                Weapons.PISTOL_LVL++;
                break;
            default:
        }
    }

}
