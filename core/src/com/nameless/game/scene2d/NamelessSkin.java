package com.nameless.game.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nameless.game.Constants;
import com.nameless.game.MainGame;

/**
 * Created by Raul on 13/06/2017.
 */

public class NamelessSkin extends Skin {
    /**
     * Game instance. I need access to the AssetManager.
     */
    private final MainGame game;

    /**
     * Build a new Rectball skin. This will set up all the required styles
     * so that they can be later retrieving using the methods from Skin class.
     *
     * @param game the game this skin is attached to.
     */
    public NamelessSkin(MainGame game) {
        this.game = game;
        addPixmapStyles();
        addLabelStyles();
        addTextButtonStyles();
        addTextureRegionStyles();
        addImageButtonStyles();
        addWindowStyles();
        addNinePatchesStyles();
        addCheckboxStyles();
        addScrollPaneStyles();
        addTextFieldStyle();
        addButtonStyle();
        addTouchpadStyle();
        addSliderStyle();
    }

    private void addSliderStyle(){
        {
            Drawable background = newDrawable("pixel", 1, 1, 1, 1);
            background.setMinWidth(20);

            add("knob", game.manager.get("ui/knob.png"));
            Drawable knob = getDrawable("knob");

            Slider.SliderStyle style = new Slider.SliderStyle(background, knob);
            add("default-horizontal", style);
        }
        {
            Drawable background = newDrawable("pixel", 1, 1, 1, 1);
//            background.setMinWidth(20);
            background.setMinHeight(20);

            add("knob", game.manager.get("ui/knob.png"));
            Drawable knob = getDrawable("knob");

            Slider.SliderStyle style = new Slider.SliderStyle(background, knob);
            add("default-vertical", style);
        }
    }

    private void addScrollPaneStyles() {
        ScrollPane.ScrollPaneStyle style = new ScrollPane.ScrollPaneStyle();
        Drawable scroll = newDrawable("pixel", 1, 1, 1, 0.20f);
        Drawable knob = newDrawable("pixel", 1, 1, 1, 0.40f);
        scroll.setMinWidth(10);
        knob.setMinWidth(10);
        style.vScroll = style.hScroll = scroll;
        style.vScrollKnob = style.hScrollKnob = knob;
        add("default", style);
    }

    private void addButtonStyle(){
        NinePatchDrawable upButton = generateButton(Color.GRAY, Color.DARK_GRAY);
        NinePatchDrawable downButton = generateButton(Color.DARK_GRAY, Color.GRAY);
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle(upButton, downButton, upButton);
        this.add("default", buttonStyle);
    }

    private void addCheckboxStyles() {
        Texture sheet = game.manager.get("ui/switch.png");
        int width = sheet.getWidth();
        int height = sheet.getHeight() / 3;
        TextureRegion broken = new TextureRegion(sheet, 0, 0, width, height);
        TextureRegion on = new TextureRegion(sheet, 0, height, width, height);
        TextureRegion off = new TextureRegion(sheet, 0, 2 * height, width, height);

        CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle();
        style.checkboxOn = new TextureRegionDrawable(on);
        style.checkboxOff = new TextureRegionDrawable(off);
        style.checkboxOnDisabled = style.checkboxOffDisabled = new TextureRegionDrawable(broken);
        style.font = game.manager.get("fonts/normal.fnt");
        add("default", style);
    }

    /**
     * Create the default label style and add it to this skin file. This is
     * the label style that will be used for every label using this skin.
     */
    private void addLabelStyles() {
        // Build the label style for normal font.
        BitmapFont normalFont = game.manager.get("fonts/normal.fnt");
        Label.LabelStyle normalStyle = new Label.LabelStyle(normalFont, Color.WHITE);
        this.add("default", normalStyle);

        // Build the label style for bold font.
        BitmapFont boldFont = game.manager.get("fonts/bold.fnt");
        Label.LabelStyle boldStyle = new Label.LabelStyle(boldFont, Color.WHITE);
        this.add("bold", boldStyle);

        // Build the label style for bold font.
        BitmapFont smallFont = game.manager.get("fonts/small.fnt");
        Label.LabelStyle smallStyle = new Label.LabelStyle(smallFont, Color.WHITE);
        this.add("small", smallStyle);

        BitmapFont bigFont = game.manager.get("fonts/normal.fnt");
        Label.LabelStyle bigStyle = new Label.LabelStyle(bigFont, Color.WHITE);
        this.add("big", bigStyle);

        // Build the monospace style
        BitmapFont monospaceFont = game.manager.get("fonts/monospaceOutline.fnt");
        Label.LabelStyle monospaceStyle = new Label.LabelStyle(monospaceFont, Color.WHITE);
        this.add("monospace", monospaceStyle);

        BitmapFont monospaceFont2 = game.manager.get("fonts/monospace.fnt");
        Label.LabelStyle monospaceStyle2 = new Label.LabelStyle(monospaceFont2, Color.WHITE);
        this.add("monospace2", monospaceStyle2);

        BitmapFont titleFont = game.manager.get("fonts/title.fnt");
        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Constants.color);
        this.add("title", titleStyle);
    }

    private NinePatchDrawable generateButton(Color color, Color down) {
        Pixmap pixmap = new Pixmap(9, 9, Pixmap.Format.RGBA8888);
        pixmap.setColor(down);
        pixmap.fill();
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, 9, 5);

        Texture texture = new Texture(pixmap);
        NinePatch ninePatch = new NinePatch(texture, 4, 4, 4, 4);
        return new NinePatchDrawable(ninePatch);
    }

    private void addTextButtonStyles() {
        {
            NinePatchDrawable upButton = generateButton(Color.GRAY, Color.DARK_GRAY);
            NinePatchDrawable downButton = generateButton(Color.DARK_GRAY, Color.GRAY);
            BitmapFont font = game.manager.get("fonts/normal.fnt");
            TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(upButton, downButton, upButton, font);
            buttonStyle.disabled = downButton;
            this.add("default", buttonStyle);
        }

        {
            NinePatchDrawable upButton = generateButton(Color.valueOf("37c837"), Color.valueOf("37c837").lerp(Color.BLACK, 0.25f));
            NinePatchDrawable downButton = generateButton(Color.valueOf("37c837").lerp(Color.BLACK, 0.25f), Color.valueOf("37c837"));
            BitmapFont font = game.manager.get("fonts/normal.fnt");
            TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(upButton, downButton, upButton, font);
            this.add("green", buttonStyle);
        }

        {
            NinePatchDrawable upButton = generateButton(Color.valueOf("0066cc"), Color.valueOf("0066cc").lerp(Color.BLACK, 0.25f));
            NinePatchDrawable downButton = generateButton(Color.valueOf("0066cc").lerp(Color.BLACK, 0.25f), Color.valueOf("0066cc"));
            BitmapFont font = game.manager.get("fonts/normal.fnt");
            TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(upButton, downButton, upButton, font);
            this.add("blue", buttonStyle);
        }
        {
            NinePatchDrawable upButton = generateButton(Color.LIGHT_GRAY, Color.GRAY);
            NinePatchDrawable downButton = generateButton(Color.GRAY, Color.LIGHT_GRAY);
            BitmapFont font = game.manager.get("fonts/normal.fnt");
            TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(upButton, downButton, upButton, font);
            this.add("white", buttonStyle);
        }

        {
            NinePatchDrawable upButton = generateButton(new Color(0,0,0,0), new Color(0,0,0,0));
            NinePatchDrawable downButton = generateButton(new Color(0,0,0,0), new Color(0,0,0,0));
            BitmapFont font = game.manager.get("fonts/normal.fnt");
            TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(upButton, downButton, upButton, font);
            this.add("weapons", buttonStyle);
        }

        {
            Color color2 = new Color(Constants.color);
            color2.add(-0.3f,-0.3f,-0.3f,1);
            NinePatchDrawable upButton = generateButton(Constants.color, color2);
            NinePatchDrawable downButton = generateButton( color2, Constants.color);
            BitmapFont font = game.manager.get("fonts/normal.fnt");
            TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(upButton, downButton, upButton, font);
            this.add("main", buttonStyle);
        }
        {
            NinePatch ninepatch = new NinePatch(game.manager.get("ui/new_blue.png", Texture.class), 8, 9, 9, 8);
            NinePatchDrawable upButton = new NinePatchDrawable(ninepatch);
            NinePatch downButton = new NinePatch(game.manager.get("ui/new_blue.png", Texture.class), 8 ,9 ,9 ,8);
            Color color = new Color(downButton.getColor().r-0.3f, downButton.getColor().g-0.3f, downButton.getColor().b-0.3f, downButton.getColor().a);
            downButton.setColor(color);
            BitmapFont font = game.manager.get("fonts/normal.fnt");
            TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(upButton, new NinePatchDrawable(downButton), upButton, font );
            this.add("new_blue", style);
        }
        {
            NinePatch ninepatch = new NinePatch(game.manager.get("ui/new_grey.png", Texture.class), 8, 9, 9, 8);
            NinePatchDrawable upButton = new NinePatchDrawable(ninepatch);
            NinePatch downButton = new NinePatch(game.manager.get("ui/new_grey.png", Texture.class), 8 ,9 ,9 ,8);
            Color color = new Color(downButton.getColor().r-0.3f, downButton.getColor().g-0.3f, downButton.getColor().b-0.3f, downButton.getColor().a);
            downButton.setColor(color);
            BitmapFont font = game.manager.get("fonts/normal.fnt");
            TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(upButton, new NinePatchDrawable(downButton), upButton, font );
            style.disabled = new NinePatchDrawable(downButton);
            this.add("new_grey", style);
        }
        {
            NinePatch ninepatch = new NinePatch(game.manager.get("ui/new_yellow.png", Texture.class), 8, 9, 9, 8);
            NinePatchDrawable upButton = new NinePatchDrawable(ninepatch);
            NinePatch downButton = new NinePatch(game.manager.get("ui/new_yellow.png", Texture.class), 8 ,9 ,9 ,8);
            Color color = new Color(downButton.getColor().r-0.3f, downButton.getColor().g-0.3f, downButton.getColor().b-0.3f, downButton.getColor().a);
            downButton.setColor(color);
            BitmapFont font = game.manager.get("fonts/normal.fnt");
            TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(upButton, new NinePatchDrawable(downButton), upButton, font );
            this.add("new_yellow", style);
        }
        {
            NinePatch ninepatch = new NinePatch(game.manager.get("ui/new_purple.png", Texture.class), 8, 9, 9, 8);
            NinePatchDrawable upButton = new NinePatchDrawable(ninepatch);
            NinePatch downButton = new NinePatch(game.manager.get("ui/new_purple.png", Texture.class), 8 ,9 ,9 ,8);
            Color color = new Color(downButton.getColor().r-0.3f, downButton.getColor().g-0.3f, downButton.getColor().b-0.3f, downButton.getColor().a);
            downButton.setColor(color);
            BitmapFont font = game.manager.get("fonts/normal.fnt");
            TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(upButton, new NinePatchDrawable(downButton), upButton, font );
            this.add("new_purple", style);
        }
        {
            NinePatch ninepatch = new NinePatch(game.manager.get("ui/new_red.png", Texture.class), 8, 9, 9, 8);
            NinePatchDrawable upButton = new NinePatchDrawable(ninepatch);
            NinePatch downButton = new NinePatch(game.manager.get("ui/new_red.png", Texture.class), 8 ,9 ,9 ,8);
            Color color = new Color(downButton.getColor().r-0.3f, downButton.getColor().g-0.3f, downButton.getColor().b-0.3f, downButton.getColor().a);
            downButton.setColor(color);
            BitmapFont font = game.manager.get("fonts/normal.fnt");
            TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(upButton, new NinePatchDrawable(downButton), upButton, font );
            this.add("new_red", style);
        }
        {
            NinePatch ninepatch = new NinePatch(game.manager.get("ui/new_green.png", Texture.class), 8, 9, 9, 8);
            NinePatchDrawable upButton = new NinePatchDrawable(ninepatch);
            NinePatch downButton = new NinePatch(game.manager.get("ui/new_green.png", Texture.class), 8 ,9 ,9 ,8);
            Color color = new Color(downButton.getColor().r-0.3f, downButton.getColor().g-0.3f, downButton.getColor().b-0.3f, downButton.getColor().a);
            downButton.setColor(color);
            BitmapFont font = game.manager.get("fonts/normal.fnt");
            TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(upButton, new NinePatchDrawable(downButton), upButton, font );
            this.add("new_green", style);
        }
    }

    private ImageButton.ImageButtonStyle buildImageButton(TextButton.TextButtonStyle source, String region) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(source);
        style.imageUp = new TextureRegionDrawable(getRegion(region));
        return style;
    }

    private void addImageButtonStyles() {
        add("share", buildImageButton(get(TextButton.TextButtonStyle.class), "iconShare"));
        add("repeat", buildImageButton(get(TextButton.TextButtonStyle.class), "iconRepeat"));
        add("house", buildImageButton(get(TextButton.TextButtonStyle.class), "iconHouse"));
        add("help", buildImageButton(get(TextButton.TextButtonStyle.class), "iconQuestion"));
        add("cross", buildImageButton(get(TextButton.TextButtonStyle.class), "iconCross"));

        add("blueHelp", buildImageButton(get("blue", TextButton.TextButtonStyle.class), "iconQuestion"));
        add("blueCross", buildImageButton(get("blue", TextButton.TextButtonStyle.class), "iconCross"));
        add("greenPlay", buildImageButton(get("green", TextButton.TextButtonStyle.class), "iconPlay"));
        add("whitePlay", buildImageButton(get("white", TextButton.TextButtonStyle.class), "iconPlay"));
        add("mainPlay", buildImageButton(get("main", TextButton.TextButtonStyle.class), "iconPlay"));

        add("newPlay", buildImageButton(get("new_grey", TextButton.TextButtonStyle.class), "iconPlay"));

        add("play", buildImageButton(get(TextButton.TextButtonStyle.class), "iconPlay"));
        add("settings", buildImageButton(get("new_grey",TextButton.TextButtonStyle.class), "iconSettings"));
        add("info", buildImageButton(get("new_grey",TextButton.TextButtonStyle.class), "iconInfo"));
        add("charts", buildImageButton(get("new_grey",TextButton.TextButtonStyle.class), "iconCharts"));
        add("star", buildImageButton(get("new_grey",TextButton.TextButtonStyle.class), "iconStar"));
        add("skin", buildImageButton(get("new_grey",TextButton.TextButtonStyle.class), "iconSkin"));
        add("online", buildImageButton(get("new_grey",TextButton.TextButtonStyle.class), "iconMultiplayer"));

        add("pistol", buildImageButton(get("weapons",TextButton.TextButtonStyle.class), "iconPistol"));
        add("uzi", buildImageButton(get("weapons",TextButton.TextButtonStyle.class), "iconUzi"));
        add("shotgun", buildImageButton(get("weapons",TextButton.TextButtonStyle.class), "iconShotgun"));
        add("grenade", buildImageButton(get("weapons",TextButton.TextButtonStyle.class), "iconGrenade"));
        add("rocket", buildImageButton(get("weapons",TextButton.TextButtonStyle.class), "iconRocket"));

        {
            Drawable redCross = newDrawable("iconCross", 0.9f, 0.1f, 0.1f, 1f);
            ImageButton.ImageButtonStyle crossStyle = new ImageButton.ImageButtonStyle(null, null, null, redCross, null, null);
            add("quit", crossStyle);
        }
    }

    private void addWindowStyles() {
        final Color backgroundColor = new Color(0.2f, 0.2f, 0.2f, 1f);
        final Color borderColor = new Color(backgroundColor).lerp(Color.WHITE, 0.2f);
        final int borderBorder = 4;
        final int borderWidth = 4;

        final int pixmapSize = 2 * (borderBorder + borderWidth) + 1;

        Pixmap windowBackground = new Pixmap(pixmapSize, pixmapSize, Pixmap.Format.RGBA8888);

        windowBackground.setColor(backgroundColor);
        windowBackground.fill();

        windowBackground.setColor(borderColor);
        windowBackground.fillRectangle(borderBorder, borderBorder, pixmapSize - 2 * borderBorder, pixmapSize - 2 * borderBorder);

        windowBackground.setColor(backgroundColor);
        windowBackground.fillRectangle(borderBorder + borderWidth, borderBorder + borderWidth, pixmapSize - 2 * (borderBorder + borderWidth), pixmapSize - 2 * (borderBorder + borderWidth));

        Texture backgroundWindow = new Texture(windowBackground);
        NinePatch backgroundPatch = new NinePatch(backgroundWindow, borderBorder + borderWidth, borderBorder + borderWidth, borderBorder + borderWidth, borderBorder + borderWidth);
        Drawable background = new NinePatchDrawable(backgroundPatch);
        BitmapFont font = game.manager.get("fonts/normal.fnt");
        Window.WindowStyle window = new Window.WindowStyle(font, Color.WHITE, background);
        add("default", window);

    }

    private void addNinePatchesStyles() {
        // Load the yellow nine patches.
        Texture yellowTexture = game.manager.get("ui/yellow_patch.png");
        NinePatch yellowPatch = new NinePatch(yellowTexture, 10, 10, 10, 10);
        this.add("yellowPatch", yellowPatch);
    }

    private void addTextureRegionStyles() {
        // Texture region for the icons.
        {
            Texture icons = game.manager.get("ui/icons_aux.png");
            TextureRegion[][] iconRegions = TextureRegion.split(icons, 100, 100);
            add("iconCharts", iconRegions[0][0]);
            add("iconScore", iconRegions[0][1]);
            add("iconStar", iconRegions[0][2]);
            add("iconSettings", iconRegions[0][3]);
            add("iconCross", iconRegions[0][4]);
            add("iconShare", iconRegions[1][0]);
            add("iconQuestion", iconRegions[1][1]);
            add("iconRepeat", iconRegions[1][2]);
            add("iconPlay", iconRegions[1][3]);
            add("iconClock", iconRegions[2][0]);
            add("iconCrown", iconRegions[2][1]);
            add("iconHouse", iconRegions[2][2]);
            add("iconInfo", iconRegions[2][3]);
            add("iconSkin", iconRegions[1][4]);
            add("iconMultiplayer", iconRegions[2][4]);
        }

        // Texture region for the progress.
        {
            Texture progress = game.manager.get("ui/progress.png");
            add("progress", progress);
        }

        // Texture region for the weapons
        {
            Texture pistol = game.manager.get("weapons/pistol.png");
            add("iconPistol", pistol);
            Texture uzi = game.manager.get("weapons/uzi.png");
            add("iconUzi", uzi);
            Texture shotgun = game.manager.get("weapons/shotgun.png");
            add("iconShotgun", shotgun);
            Texture grenade = game.manager.get("weapons/grenade.png");
            add("iconGrenade", grenade);
            Texture rocket = game.manager.get("weapons/rocket.png");
            add("iconRocket", rocket);
        }
    }

    private void addPixmapStyles() {
        Pixmap pixel = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixel.setColor(Color.WHITE);
        pixel.fill();
        this.add("pixel", new Texture(pixel));
    }

    private void addTextFieldStyle(){
        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = game.manager.get("fonts/normal.fnt");
        style.fontColor = Color.WHITE;

        final Color backgroundColor = new Color(0.5f, 0.5f, 0.5f, 5f);
        final Color borderColor = new Color(backgroundColor).lerp(Color.WHITE, 0.2f);
        final int borderBorder = 4;
        final int borderWidth = 4;

        final int pixmapSize = 2 * (borderBorder + borderWidth) + 1;

        Pixmap windowBackground = new Pixmap(pixmapSize, pixmapSize, Pixmap.Format.RGBA8888);

        windowBackground.setColor(backgroundColor);
        windowBackground.fill();

        windowBackground.setColor(borderColor);
        windowBackground.fillRectangle(borderBorder, borderBorder, pixmapSize - 2 * borderBorder, pixmapSize - 2 * borderBorder);

        windowBackground.setColor(backgroundColor);
        windowBackground.fillRectangle(borderBorder + borderWidth, borderBorder + borderWidth, pixmapSize - 2 * (borderBorder + borderWidth), pixmapSize - 2 * (borderBorder + borderWidth));

        Texture backgroundWindow = new Texture(windowBackground);
        NinePatch backgroundPatch = new NinePatch(backgroundWindow, borderBorder + borderWidth, borderBorder + borderWidth, borderBorder + borderWidth, borderBorder + borderWidth);
        Drawable background = new NinePatchDrawable(backgroundPatch);

        style.background = background;

        add("default", style);
    }

    private void addTouchpadStyle(){
        add("touchKnob", game.manager.get("ui/touchKnob.png"));

        //Create TouchPad Style
        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        //Drawable touchBackground = getDrawable("touchBackground");
        Drawable touchKnob = getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        //touchpadStyle.background = touchBackground;

        touchpadStyle.knob = touchKnob;

        add("default", touchpadStyle);
    }
}
