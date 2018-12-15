package com.nameless.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.nameless.game.maps.LevelManager;

public class Constants {

    public static final String TITLE = "nightlie";
    public static final String VERSION = "v0.1a";

    /**
     * The width of the Scene2D stage. The bigger this value is, the greater
     * the space is going to be for child elements of this screen. However,
     * the smallest everything is going to be seen, specially in small screen.s
     */
    public static final int VIEWPORT_WIDTH = 1080;

    /**
     * The height of the Scene2D stage. The bigger this value is, the greater
     * the space is going to be for child elements of this screen. However,
     * the smallest everything is going to be seen, specially in small screens.
     */
    public static final int VIEWPORT_HEIGHT = 600;

    /**
     * The width and height of rendered map
     */
    public static final int RENDER_WIDTH = 30;

    /**
     * The padding for the table in every stage.
     */
    public static final int STAGE_PADDING = 20;

    /**
     * Unit scale for Box2D. 1 meter == 43 pixels
     */
    public static final float PixelsPerMeter = 43; //43;

    public static Color color1 = new Color(1, 112/255f, 112/255f, 1);
    public static Color color2 = new Color(86/255f, 170/255f, 255/255f, 1);
    public static Color color3 = new Color(205/255f, 112/255f, 255/255f, 1);
    public static Color color = new Color(Color.LIGHT_GRAY);

    /**
     * Multiplayer constants
     */
    public static String NAME = "Rahuvich";
    public static String IP = "localhost";

    /**
     * Number of skins available
     */
    public static int Characters = 16;
    public static String character = "manBrown";


    /*
    * Category bits for collision filtering box2d
     */
    public final static short NOTHING_BIT = 0x0000;
    public final static short NEUTRAL_BIT = 0x0001;
    public final static short PLAYER_BIT = 0x0002;
    public final static short ENEMY_BIT = 0x0004;
    public final static short BULLET_BIT = 0x008;
    public final static short LOW_FURNITURES_BIT = 0x0010;
    public final static short OBSTACLES_BIT = 0x0020;
    public final static short ENEMY_OBSTACLES_BIT = 0x0040;
    public final static short EVERYTHING_BIT = ENEMY_OBSTACLES_BIT | OBSTACLES_BIT | NEUTRAL_BIT | PLAYER_BIT | ENEMY_BIT | BULLET_BIT | LOW_FURNITURES_BIT;

    public static short everyOthersBit(short myCategory){
        return (short) (EVERYTHING_BIT ^ myCategory);
    }
}
