package com.nameless.game.actors.player;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nameless.game.*;
import com.nameless.game.actors.Blinker;
import com.nameless.game.actors.Character;
import com.nameless.game.actors.items.Flashlight;
import com.nameless.game.scene2d.ui.HealthBar;
import com.nameless.game.scene2d.ui.WeaponInfo;
import com.nameless.game.screens.Menu;
import com.nameless.game.screens.Play;

import static com.nameless.game.Constants.ENEMY_BIT;
import static com.nameless.game.Constants.ENEMY_OBSTACLES_BIT;
import static com.nameless.game.Constants.PixelsPerMeter;


public class Player extends Character {

    public float MAX_HEALTH;

    public Play play;

    public TextureAtlas atlas;
    public TextureRegion region;

    private Flashlight flashlight = null;

    public int[] weapons;

    public Vector2 MuzzlePos;

    private HealthBar healthbar;
    private WeaponInfo weaponInfo;

    public Player(Play play, RayHandler rayHandler, World world, float x, float y) {
        super(world, 300 * (43/Constants.PixelsPerMeter), 999999999);
        this.play = play;
        MAX_HEALTH = HEALTH;

        weapons = new int[Weapons.NUMB_WEAPONS];
        weapons[Weapons.NONE] = 1;
        weapons[Weapons.PISTOL] = Weapons.PISTOL_CAPACITY;
        weapons[Weapons.UZI] = Weapons.UZI_CAPACITY;
        weapons[Weapons.SHOTGUN] = Weapons.SHOTGUN_CAPACITY;
        weapons[Weapons.GRENADE] = Weapons.GRENADE_CAPACITY;
        weapons[Weapons.ROCKET] = Weapons.ROCKET_CAPACITY;

        atlas = play.game.manager.get("players/sprites.atlas");
        switch (VirtualController.ACTUAL_WEAPON){
            case Weapons.ROCKET:
                region = atlas.findRegion(Constants.character + "_rocket");
                break;
            case Weapons.GRENADE:
                region = atlas.findRegion(Constants.character + "_grenade");
                break;
            case Weapons.SHOTGUN:
                region = atlas.findRegion(Constants.character + "_shotgun");
                break;
            case Weapons.UZI:
                region = atlas.findRegion(Constants.character + "_machine");
                break;
            case Weapons.PISTOL:
                region = atlas.findRegion(Constants.character + "_gun");
                break;
            case Weapons.NONE:
            default:
                region = atlas.findRegion(Constants.character + "_stand");
        }
        setPosition(x/PixelsPerMeter, y/PixelsPerMeter);
        setSize(atlas.findRegion(Constants.character + "_stand").getRegionWidth()/PixelsPerMeter,
                atlas.findRegion(Constants.character + "_stand").getRegionWidth()/PixelsPerMeter);
        setOrigin(getWidth()/2,getHeight()/2);
        MuzzlePos = new Vector2(getX() + getWidth()/2, getY() + getHeight()/2)
                .add(getWidth()*1.5f, -getHeight()/6);


        setBox2d();

        if(rayHandler != null) flashlight = new Flashlight(rayHandler, getRotation(), getHeight(), body);

        currentState = new DefaultPlayerState();
        currentState.Enter(this);

        healthbar = new HealthBar(this);
        weaponInfo = new WeaponInfo(this);
        play.mapHud.addActor(healthbar);
        play.mapHud.addActor(weaponInfo);

    }

    private void setBox2d() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setUserData(this);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius((getWidth()/2));

        fdef.shape = shape;
        fdef.filter.categoryBits = Constants.PLAYER_BIT;
        fdef.filter.maskBits = Constants.everyOthersBit(ENEMY_OBSTACLES_BIT);
        body.createFixture(fdef);
        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(blinker.shouldBlink(Gdx.graphics.getDeltaTime())) return;

        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, getColor().a);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), region.getRegionWidth()/PixelsPerMeter,
                region.getRegionHeight()/PixelsPerMeter, getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        flashlight.update(DayNightCycleManager.lightsOpen);
        MuzzlePos.set(getCenterX(), getCenterY())
                .add(getWidth()*1f, -getHeight()/6);

        switch (VirtualController.ACTUAL_WEAPON){
            case Weapons.ROCKET:
                region = atlas.findRegion(Constants.character + "_rocket");
                break;
            case Weapons.GRENADE:
                region = atlas.findRegion(Constants.character + "_grenade");
                break;
            case Weapons.SHOTGUN:
                region = atlas.findRegion(Constants.character + "_shotgun");
                break;
            case Weapons.UZI:
                region = atlas.findRegion(Constants.character + "_machine");
                break;
            case Weapons.PISTOL:
                region = atlas.findRegion(Constants.character + "_gun");
                break;
            case Weapons.NONE:
            default:
                region = atlas.findRegion(Constants.character + "_stand");
        }
    }

    public boolean remove(){
        play.clearScene();
        world.destroyBody(body);
        currentState.Exit();
        currentState = null;
        return super.remove();
    }

    public void LootCollected(){
        switch (MathUtils.random(0,5)){
            case 4:
                if(weapons[Weapons.ROCKET] < Weapons.ROCKET_CAPACITY){
                    weapons[Weapons.ROCKET] = MathUtils.round(Weapons.ROCKET_CAPACITY);
                    break;
                }
            case 3:
                if(weapons[Weapons.GRENADE] < Weapons.GRENADE_CAPACITY){
                    weapons[Weapons.GRENADE] = MathUtils.round(Weapons.GRENADE_CAPACITY);
                    break;
                }
            case 2:
                if(weapons[Weapons.SHOTGUN] < Weapons.SHOTGUN_CAPACITY){
                    weapons[Weapons.SHOTGUN] = MathUtils.round(Weapons.GRENADE_CAPACITY);
                    break;
                }
            case 1:
                if(weapons[Weapons.UZI] < Weapons.UZI_CAPACITY){
                    weapons[Weapons.UZI] = MathUtils.round(Weapons.UZI_CAPACITY);
                    break;
                }
            case 0:
                if(weapons[Weapons.PISTOL] < Weapons.PISTOL_CAPACITY){
                    weapons[Weapons.PISTOL] = MathUtils.round(Weapons.PISTOL_CAPACITY);
                    break;
                }
            case 5:
            default:
                if(HEALTH < MAX_HEALTH){
                    HEALTH = MAX_HEALTH;
                }
        }
    }
}
