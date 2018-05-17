package com.nameless.game.maps;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.nameless.game.Constants;
import com.nameless.game.MainGame;

/**
 * Created by Raul on 27/06/2017.
 */
public class TownMap extends BasicMap {

    public TownMap(MainGame game, float unitScale) {
        super(game, "tmx/town_map.tmx", unitScale);

        /*
        Must have layers:
        - TreesCollisions
        - ActorsCollisions
        - AllCollisions
         */

        createBodies();
    }

    private void createBodies() {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        CircleShape roundShape = new CircleShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Layer 9 - TreesCollisions
        for(MapObject object : tiledmap.getLayers().get("TreesCollisions").getObjects().getByType(EllipseMapObject.class)){
            Ellipse ellipse;
            ellipse = ((EllipseMapObject) object).getEllipse();
            Circle circle = new Circle(ellipse.x + ellipse.height/2 , ellipse.y + ellipse.height/2, ellipse.height/2);

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(circle.x *unitScale, circle.y *unitScale);

            body = world.createBody(bdef);

            roundShape.setRadius(circle.radius *unitScale);
            fdef.shape = roundShape;
            fdef.filter.categoryBits = Constants.NEUTRAL_BIT;
            fdef.filter.maskBits = Constants.everyOthersBit(Constants.NEUTRAL_BIT);
            body.createFixture(fdef);
        }

        // Layer 10 - Actors/Characters collisions
        for(MapObject object : tiledmap.getLayers().get("ActorCollisions").getObjects().getByType(EllipseMapObject.class)){
            Ellipse ellipse;
            ellipse = ((EllipseMapObject) object).getEllipse();
            Circle circle = new Circle(ellipse.x + ellipse.height/2 , ellipse.y + ellipse.height/2, ellipse.height/2);

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(circle.x*unitScale , circle.y *unitScale);

            body = world.createBody(bdef);

            roundShape.setRadius(circle.radius*unitScale);
            fdef.shape = roundShape;
            fdef.filter.categoryBits = Constants.LOW_FURNITURES_BIT;
            fdef.filter.maskBits = Constants.PLAYER_BIT | Constants.ENEMY_BIT;
            body.createFixture(fdef);

            if(object.getProperties().get("roundLight", false, boolean.class)){
                createRoundLight(body, ellipse.x, ellipse.y);
            }
        }
        for(MapObject object : tiledmap.getLayers().get("ActorCollisions").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)*unitScale, (rect.getY() + rect.getHeight() / 2)*unitScale );

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2 )*unitScale, (rect.getHeight() / 2 )*unitScale);
            fdef.shape = shape;
            fdef.filter.categoryBits = Constants.LOW_FURNITURES_BIT;
            fdef.filter.maskBits = Constants.PLAYER_BIT | Constants.ENEMY_BIT;
            body.createFixture(fdef);

        }
        for(MapObject object : tiledmap.getLayers().get("PlayerCollisions").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)*unitScale, (rect.getY() + rect.getHeight() / 2)*unitScale );

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2 )*unitScale, (rect.getHeight() / 2 )*unitScale);
            fdef.shape = shape;
            fdef.filter.categoryBits = Constants.LOW_FURNITURES_BIT;
            fdef.filter.maskBits = Constants.PLAYER_BIT;
            body.createFixture(fdef);

        }

        // Layer 11 - Everyone collisions
        for(MapObject object : tiledmap.getLayers().get("AllCollisions").getObjects().getByType(EllipseMapObject.class)){
            Ellipse ellipse;
            ellipse = ((EllipseMapObject) object).getEllipse();
            Circle circle = new Circle(ellipse.x + ellipse.height/2 , ellipse.y + ellipse.height/2, ellipse.height/2);

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(circle.x*unitScale , circle.y *unitScale);

            body = world.createBody(bdef);

            roundShape.setRadius(circle.radius*unitScale);
            fdef.shape = roundShape;
            fdef.filter.categoryBits = Constants.NEUTRAL_BIT;
            fdef.filter.maskBits = Constants.everyOthersBit(Constants.NEUTRAL_BIT);
            body.createFixture(fdef);
        }
        for(MapObject object : tiledmap.getLayers().get("AllCollisions").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) *unitScale, (rect.getY() + rect.getHeight() / 2)*unitScale );

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2)*unitScale , (rect.getHeight() / 2 )*unitScale);
            fdef.shape = shape;
            fdef.filter.categoryBits = Constants.NEUTRAL_BIT;
            fdef.filter.maskBits = Constants.everyOthersBit(Constants.NEUTRAL_BIT);
            body.createFixture(fdef);
        }
        roundShape.dispose();
        shape.dispose();
    }

}
