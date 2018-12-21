package com.nameless.game.tutorial;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.nameless.game.Constants;
import com.nameless.game.IObserver;
import com.nameless.game.ISubject;
import com.nameless.game.MainGame;
import com.nameless.game.actors.Loot;
import com.nameless.game.actors.enemies.Zombie;
import com.nameless.game.managers.WaveSpawnManager;
import com.nameless.game.maps.LevelManager;
import com.nameless.game.pathfinding.Node;
import com.nameless.game.screens.BasicPlay;
import static com.nameless.game.Constants.PLAYER_BIT;

public class TutorialPlay extends BasicPlay implements IObserver {

    public TutorialPlay(MainGame game) {
        super(game);
        player.transformTo(30,33);
        player.setRotation(90);

        setShield();
        addZombies(3);
    }

    private void addZombies(int size) {
        float ang;
        for(int i = 0; i < size; ++i){
            ang = i * 360/size;
            float x = player.getCenterX() + Constants.RENDER_WIDTH * MathUtils.sin(ang);
            float y = player.getCenterY()+ Constants.RENDER_WIDTH  * MathUtils.cos(ang);

            while(x < 0 || y < 0 || x > LevelManager.WIDTH_IN_PIXELS || y > LevelManager.HEIGHT_IN_PIXELS ||
                    LevelManager.graph.getNodeByXYFloat(player.getCenterX() + Constants.RENDER_WIDTH * MathUtils.sin(ang),
                            player.getCenterY()+ Constants.RENDER_WIDTH  * MathUtils.cos(ang)).type != Node.Type.REGULAR){
                ang = MathUtils.random() * 360;
                x = player.getCenterX() + Constants.RENDER_WIDTH * MathUtils.sin(ang);
                y = player.getCenterY()+ Constants.RENDER_WIDTH  * MathUtils.cos(ang);
            }

            Zombie zombie = new Zombie(this,map.world,player,x,y);
            zombie.setTypeLoot(Loot.Type.values()[i%3]);
            zombie.attach(this);
            fg.addActor(zombie);
        }
    }

    private void setShield() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(player.getX(), player.getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = map.world.createBody(bdef);
        body.setUserData(this);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4);

        fdef.shape = shape;
        fdef.filter.categoryBits = Constants.ENEMY_OBSTACLES_BIT;
        fdef.filter.maskBits = Constants.everyOthersBit(PLAYER_BIT);
        body.createFixture(fdef);
        shape.dispose();
    }


    @Override
    public void handleMessage(Object o, ISubject.type type) {
        if (type == ISubject.type.ZOMBIE_DEAD){

        }
    }
}
