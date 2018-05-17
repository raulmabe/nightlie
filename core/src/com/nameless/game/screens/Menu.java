package com.nameless.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nameless.game.Constants;
import com.nameless.game.MainGame;
import com.nameless.game.maps.BasicMap;
import com.nameless.game.maps.TownMap;
import com.nameless.game.screens.stages.*;

/**
 * Created by Raul on 13/06/2017.
 */

public class Menu extends BasicScreen {

    private final static int MENU = 1;
    private final static int ONLINE = 2;
    private final static int SKINS = 3;
    private final static int INFO = 4;
    private final static int SETTINGS = 5;
    private final static int STATISTICS = 6;
    private int state = 1;

    private BasicMap map = null;
    private OrthographicCamera camMap = null;
    private int x, y;

    private BasicMenuStage skinStage, infoStage, onlineStage, settingsStage, statisticsStage;

    private Label title = null;

    private ImageButton play = null, settings = null, statistics = null, about = null, quit = null, online = null, skin = null;

    private Table extraButtons = null;

    public Menu(MainGame game) {
        super(game);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        title = null;
        play = settings = statistics = about = quit = online = skin = null;
        extraButtons = null;
        map.dispose();
    }

    @Override
    public void setUpInterface(Table table) {
        buildMainMenu(table);

        ChangeListener listener, nightListener;

        listener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                state = MENU;
                Gdx.input.setInputProcessor(stage);
            }
        };
        nightListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(map instanceof TownMap) {
                }
                else {
                }
            }
        };

        skinStage = new SkinsStage(game, viewport, listener);
        infoStage = new InfoStage(game, viewport, listener);
        onlineStage = new OnlineStage(game, viewport, listener);
        settingsStage = new SettingsStage(game, viewport, listener, nightListener);
        statisticsStage = new StatisticsStage(game, viewport, listener);
    }


    private void buildMainMenu(Table table){
        // Build the actors.
        if (title == null) {
            title = new Label(Constants.TITLE, game.getSkin(), "title");
            title.setFontScale(0.5f);
        }

        if (play == null) {
            play = new ImageButton(game.getSkin(), "newPlay");
            play.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new Play(game));
                        }
                    })));
                }
            });
        }
        if (settings == null) {
            settings = new ImageButton(game.getSkin(), "settings");
            settings.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    state = SETTINGS;
                    Gdx.input.setInputProcessor(settingsStage);
                }
            });
        }
        if (statistics == null) {
            statistics = new ImageButton(game.getSkin(), "charts");
            statistics.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    state = STATISTICS;
                    Gdx.input.setInputProcessor(statisticsStage);
                }
            });
        }
        if (about == null) {
            about = new ImageButton(game.getSkin(), "info");
            about.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    state = INFO;
                    Gdx.input.setInputProcessor(infoStage);
                }
            });
        }
        if (quit == null) {
            quit = new ImageButton(game.getSkin(), "quit");
            quit.setSize(50, 50);
            quit.setPosition(60, getStage().getViewport().getWorldHeight() - 60);
            quit.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.app.exit();
                }
            });
            getStage().addActor(quit);
        }
        if(online == null) {
            online = new ImageButton(game.getSkin(), "online");
            online.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    state = ONLINE;
                    Gdx.input.setInputProcessor(onlineStage);
                }
            });
        }

        if(skin == null) {
            skin = new ImageButton(game.getSkin(), "skin");
            skin.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    state = SKINS;
                    InputMultiplexer inputMulti = new InputMultiplexer();
                    inputMulti.addProcessor(skinStage);
                    inputMulti.addProcessor(new InputProcessor() {
                        private Vector2 lastTouch = new Vector2();
                        private Vector2 delta = new Vector2();

                        @Override
                        public boolean keyDown(int keycode) {
                            return false;
                        }

                        @Override
                        public boolean keyUp(int keycode) {
                            return false;
                        }

                        @Override
                        public boolean keyTyped(char character) {
                            return false;
                        }

                        @Override
                        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                            lastTouch.set(screenX, screenY);
                            return true;
                        }

                        @Override
                        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                            Vector2 newTouch = new Vector2(screenX, screenY);
                            delta = newTouch.cpy().sub(lastTouch);

                            if(delta.x > 0) ((SkinsStage) skinStage).changeImage(+1);
                            else if(delta.x < 0) ((SkinsStage) skinStage).changeImage(-1);

                            lastTouch = newTouch;
                            return true;
                        }

                        @Override
                        public boolean touchDragged(int screenX, int screenY, int pointer) {
                            return false;
                        }

                        @Override
                        public boolean mouseMoved(int screenX, int screenY) {
                            return false;
                        }

                        @Override
                        public boolean scrolled(int amount) {
                            return false;
                        }
                    });
                    Gdx.input.setInputProcessor(inputMulti);
                }
            });
        }

        if (extraButtons == null) {
            extraButtons = new Table();
            extraButtons.defaults().expandX().fillX();
            extraButtons.add(settings);
            extraButtons.add(statistics).pad(0, 20, 0, 20);
            extraButtons.add(about).pad(0,0,0,20);
            extraButtons.add(skin).row();
        }

        if(map == null) {
            map = new TownMap(game, 1f);
            camMap = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
            camMap.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
            camMap.position.x += 10*Constants.PixelsPerMeter;
            camMap.position.y += 10*Constants.PixelsPerMeter;
            x = 1;
            y = 1;
        }

        table.add(title).pad(40).padBottom(20).row();
        table.add(play).fillX().height(150).row();
        table.add(extraButtons).fillX().height(150).row();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(camMap.position.x > (map.getWidth()*64)- Constants.VIEWPORT_WIDTH || camMap.position.y > (map.getHeight()*64)- Constants.VIEWPORT_HEIGHT
                || camMap.position.x < Constants.VIEWPORT_WIDTH/2 || camMap.position.y < Constants.VIEWPORT_HEIGHT/2){
            if(MathUtils.randomBoolean()) {
                x *= 1;
                y *= -1;
            } else {
                x *= -1;
                y *= 1;
            }
        }
        camMap.translate(x,y,0);
        camMap.update();

        // Render Map
        map.render(camMap);

        // Render RayHandler
        map.renderRayHandler(camMap);

        // Render fore layers
        map.renderForeLayers();

        switch (state) {
            case SETTINGS:
                settingsStage.act(delta);
                settingsStage.draw();
                break;
            case ONLINE:
                onlineStage.act(delta);
                onlineStage.draw();
                break;
            case SKINS:
                skinStage.act(delta);
                skinStage.draw();
                break;
            case INFO:
                infoStage.act(delta);
                infoStage.draw();
                break;
            case STATISTICS:
                statisticsStage.act(delta);
                statisticsStage.draw();
                break;
            case MENU:
            default:
                stage.act(delta);
                stage.draw();
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new MainMenuInputProcessor());
        Gdx.input.setInputProcessor(multiplexer);


    }

    private class MainMenuInputProcessor extends InputAdapter {

        @Override
        public boolean keyDown(int keycode) {
            if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
                Gdx.app.exit();
                return true;
            }
            return false;
        }
    }
}
