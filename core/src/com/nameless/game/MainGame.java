package com.nameless.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.nameless.game.scene2d.NamelessSkin;
import com.nameless.game.screens.Loading;
import com.nameless.game.screens.Menu;


public class MainGame extends Game {
	SpriteBatch batch;

	private NamelessSkin skin;

	public static AssetManager manager;

	public Preferences prefs;

	public MainGame() {
	}

	@Override
	public void create () {
		batch = new SpriteBatch();

		// Load the resources.
		manager = createManager();

		// Create Preferences
		prefs = Gdx.app.getPreferences("nameless");
		Constants.character = prefs.getString("skin","manBlue");
		DayNightCycleManager.dayTime = prefs.getFloat("dayTime", 1f);

		// Load first screen
		setScreen(new Loading(this));
	}

	private AssetManager createManager() {
		AssetManager manager = new AssetManager();

		// Set up the parameters for loading linear textures. Linear textures
		// use a linear filter to not have artifacts when they are scaled.
		TextureLoader.TextureParameter texParameters = new TextureLoader.TextureParameter();
		BitmapFontLoader.BitmapFontParameter fntParameters = new BitmapFontLoader.BitmapFontParameter();
		texParameters.minFilter = texParameters.magFilter = Texture.TextureFilter.Linear;
		fntParameters.minFilter = texParameters.magFilter = Texture.TextureFilter.Linear;

		manager.load("fonts/bold.fnt", BitmapFont.class, fntParameters);
		manager.load("fonts/monospace.fnt", BitmapFont.class);
		manager.load("fonts/monospaceOutline.fnt", BitmapFont.class);
		manager.load("fonts/normal.fnt", BitmapFont.class, fntParameters);
		manager.load("fonts/small.fnt", BitmapFont.class, fntParameters);
		manager.load("fonts/title.fnt", BitmapFont.class, fntParameters);

		// Load UI resources.
		manager.load("ui/progress.png", Texture.class, texParameters);
		manager.load("ui/icons_aux.png", Texture.class, texParameters);
		manager.load("ui/yellow_patch.png", Texture.class);
		manager.load("ui/switch.png", Texture.class, texParameters);
		manager.load("ui/touchBackground.png", Texture.class, texParameters);
		manager.load("ui/touchKnob.png", Texture.class, texParameters);
		manager.load("ui/knob.png", Texture.class, texParameters);
		manager.load("ui/new_blue.png", Texture.class, texParameters);
		manager.load("ui/new_yellow.png", Texture.class, texParameters);
		manager.load("ui/new_grey.png", Texture.class, texParameters);
		manager.load("ui/new_purple.png", Texture.class, texParameters);
		manager.load("ui/new_red.png", Texture.class, texParameters);
		manager.load("ui/new_green.png", Texture.class, texParameters);

		// Load characters
		manager.load("players/sprites.png", Texture.class, texParameters);
		manager.load("players/sprites.atlas", TextureAtlas.class);
		manager.load("players/characters.png", Texture.class, texParameters);
		manager.load("players/characters.atlas", TextureAtlas.class);

		manager.load("players/anim/zombie1_attack.png", Texture.class, texParameters);
		manager.load("players/anim/zombie2_attack.png", Texture.class, texParameters);

		// Weapons
		manager.load("players/anim/explosion.png", Texture.class, texParameters);
		manager.load("weapons/pistol.png", Texture.class, texParameters);
		manager.load("weapons/uzi.png", Texture.class, texParameters);
		manager.load("weapons/shotgun.png", Texture.class, texParameters);
		manager.load("weapons/grenade.png", Texture.class, texParameters);
		manager.load("weapons/rocket.png", Texture.class, texParameters);


		// Load Maps
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load("tmx/town_map.tmx", TiledMap.class);


		// Load sounds
		// ...

		return manager;
	}

	public void finishLoading() {
		// Load the remaining data.
		skin = new NamelessSkin(this);

		// Enter main menu.
		setScreen(new Menu(this));
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		manager.dispose();
		skin.dispose();
		prefs.putString("skin",Constants.character);
		prefs.putFloat("dayTime", DayNightCycleManager.dayTime);
		prefs.flush();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public NamelessSkin getSkin() {
		return skin;
	}
}
