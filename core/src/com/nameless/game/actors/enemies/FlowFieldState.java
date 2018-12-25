package com.nameless.game.actors.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.nameless.game.MathStatic;
import com.nameless.game.actors.Character;
import com.nameless.game.actors.states.IState;
import com.nameless.game.maps.LevelManager;

public class FlowFieldState implements IState {

    private Zombie zombie;

    private int actualNode;

    private float timeToChangePathfinding = 2000000000; // 2 seconds
    private float timeSinceLastChange;

    @Override
    public void Enter(Character parent) {
        this.zombie = (Zombie) parent;
        if(((Zombie) parent).target == null) parent.changeState(null);

        actualNode = LevelManager.graph.getIndexByXYEnemy( zombie.getCenterX(), zombie.getCenterY(), zombie.distance);

        timeSinceLastChange = TimeUtils.nanoTime();

    }

    @Override
    public void Update(float dt) {
        if(LevelManager.graph.getIndexByXYEnemy( zombie.getCenterX() - .5f * MathUtils.cos(zombie.direction.angleRad()),
                zombie.getCenterY() - .5f * MathUtils.sin(zombie.direction.angleRad()), zombie.distance) != actualNode) {
            actualNode = LevelManager.graph.getIndexByXYEnemy( zombie.getCenterX() - .5f * MathUtils.cos(zombie.direction.angleRad()),
                    zombie.getCenterY() - .5f * MathUtils.sin(zombie.direction.angleRad()), zombie.distance);
            timeSinceLastChange = TimeUtils.nanoTime();
        } else if(TimeUtils.nanoTime() - timeSinceLastChange > timeToChangePathfinding*2){
            zombie.changeState(new AStarState());
        }
        //PathfindingDebugger.drawPositionNode(LevelManager.graph.getNode(actualNode), Color.GOLD);

        if(!LevelManager.graph.getNode(actualNode).flow.nor().isZero()){
            zombie.direction = LevelManager.graph.getNode(actualNode).flow.nor();
        }

        zombie.setRotation(zombie.direction.angle());
        zombie.body.setTransform(zombie.body.getPosition().x, zombie.body.getPosition().y, (float) Math.toRadians(zombie.getRotation()));
        zombie.body.setLinearVelocity(zombie.direction.x *zombie.SPEED * dt , zombie.direction.y * zombie.SPEED *dt);
        zombie.setPosition(zombie.body.getPosition().x - zombie.getWidth()/2, zombie.body.getPosition().y - zombie.getHeight()/2);

        if(MathStatic.getDistancePointToPoint(zombie.body.getPosition(),new Vector2(zombie.target.getX() + zombie.target.getWidth()/2,
                zombie.target.getY() + zombie.target.getHeight()/2)) <= zombie.RANGE *2){
            GoToTarget();
        }
    }

    private void GoToTarget(){
        zombie.direction = MathStatic.getDirection(new Vector2(zombie.target.getX() + zombie.target.getWidth()/2,
                zombie.target.getY() + zombie.target.getHeight()/2), zombie.body.getPosition());
        if(MathStatic.getDistancePointToPoint(zombie.body.getPosition(),new Vector2(zombie.target.getX() + zombie.target.getWidth()/2,
                zombie.target.getY() + zombie.target.getHeight()/2)) <= zombie.RANGE){
            zombie.direction = Vector2.Zero;
            zombie.changeState(new ZombieAttackState());
        }
    }

    @Override
    public void Exit() {
    }


}
