package com.nameless.game.actors.enemies;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.nameless.game.Constants;
import com.nameless.game.MathStatic;
import com.nameless.game.actors.Character;
import com.nameless.game.actors.states.IState;
import com.nameless.game.maps.LevelManager;

public class ZombieFollowState2 implements IState {

    private Zombie zombie;

    private Vector2 ahead;
    private Vector2 ahead2;

    private Vector2 position, velocity;

    private int MAX_SEE_AHEAD = 3;

    private static OrthographicCamera camera;
    private static ShapeRenderer shaper;

    private float targetX = 0;
    private float targetY = 0;

    public static void setCamera(OrthographicCamera camera) {
        ZombieFollowState2.camera = camera;
        shaper = new ShapeRenderer();
    }

    @Override
    public void Enter(Character parent) {
        this.zombie = (Zombie) parent;
        position = new Vector2(this.zombie.getX(), this.zombie.getY());
        velocity = new Vector2(this.zombie.SPEED, this.zombie.SPEED);
        ahead = new Vector2(position.add(velocity));
        ahead = MathStatic.V2xf(ahead, MAX_SEE_AHEAD);
        ahead2 = MathStatic.V2xf(ahead, 0.5f);
    }

    @Override
    public void Update(float dt) {
        position.x = this.zombie.getX() + zombie.getWidth()/2;
        position.y = this.zombie.getY() + zombie.getHeight()/2;
        calcAhead(ahead);
        calcAhead(ahead2);

        GoToTarget();

        if(zombie.direction.angle() != 0) zombie.setRotation(zombie.direction.angle());
        zombie.body.setTransform(zombie.body.getPosition().x, zombie.body.getPosition().y, (float) Math.toRadians(zombie.getRotation()));
        zombie.body.setLinearVelocity(zombie.direction.x *zombie.SPEED * dt , zombie.direction.y * zombie.SPEED *dt);
        zombie.setPosition(zombie.body.getPosition().x - zombie.getWidth()/2, zombie.body.getPosition().y - zombie.getHeight()/2);
        debug();
    }

    private void GoToTarget(){
        zombie.direction = MathStatic.getDirection(new Vector2(zombie.target.getX() + zombie.target.getWidth()/2,
                zombie.target.getY() + zombie.target.getHeight()/2), zombie.body.getPosition());
        if(MathStatic.getDistancePointToPoint(zombie.body.getPosition(),new Vector2(zombie.target.getX() + zombie.target.getWidth()/2,
                zombie.target.getY() + zombie.target.getHeight()/2)) <= zombie.RANGE){
            zombie.direction = Vector2.Zero;
            zombie.ChangeState(new ZombieAttackState());
        }
    }

    private boolean targetHasMoved(){
        if(targetX != com.badlogic.gdx.math.MathUtils.round(zombie.target.getX() + zombie.target.getWidth()/2) ||
                targetY != MathUtils.round(zombie.target.getY() + zombie.target.getHeight()/2))
            return true;
        return false;
    }

    private void debug(){
        shaper.setProjectionMatrix(camera.combined);
        shaper.begin(ShapeRenderer.ShapeType.Line);
        shaper.setColor(1, 0, 0, .3f);

        /*
        Vector2 aux = MathStatic.V2minusV2(position, zombie.direction);
        shaper.rectLine(LevelManager.TILE_WIDTH / 2 + ((zombie.getX() - zombie.getWidth()/2) % LevelManager.WIDTH_IN_TILES),
                LevelManager.TILE_HEIGHT / 2 + ((zombie.getY() - zombie.getHeight()/2) % LevelManager.WIDTH_IN_TILES),
                LevelManager.TILE_WIDTH / 2 + (aux.x % LevelManager.WIDTH_IN_TILES),
                LevelManager.TILE_HEIGHT / 2 + (aux.y % LevelManager.WIDTH_IN_TILES), .3f);
        //System.out.println("Position: " + position + "; Ahead: " + ahead);*/
        shaper.circle(ahead.x, ahead.y, .5f);
        shaper.line(position, ahead);
        shaper.end();
    }

    private void calcAhead(Vector2 vec){
        vec.x = (zombie.getX() + zombie.getWidth()/2) + zombie.direction.x*MAX_SEE_AHEAD;
        vec.y = (zombie.getY() + zombie.getHeight()/2) + zombie.direction.y*MAX_SEE_AHEAD;
    }

    @Override
    public void Exit() {

    }


}
