package com.nameless.game.actors.enemies;

import com.badlogic.gdx.ai.pfa.PathFinderRequest;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.nameless.game.MathStatic;
import com.nameless.game.actors.Character;
import com.nameless.game.actors.states.IState;
import com.nameless.game.maps.LevelManager;
import com.nameless.game.pathfinding.*;

public class AStarState implements IState, Pather<Node>{

    private Zombie zombie;

    private IndexedAStarPathFinder<Node> pathFinder;
    public GraphPathImp resultPath = null;

    private boolean isRequested;

    private Vector2 waypoint = null;

    private float lastRequestTime = 0;
    private float targetX = 0;
    private float targetY = 0;


    private float timeToChangePathfinding = 2000000000; // 2 seconds
    private float timeEntered;


    @Override
    public void Enter(Character parent) {
        zombie = (Zombie) parent;
        if(((Zombie) parent).target == null) parent.ChangeState(null);

        pathFinder = new IndexedAStarPathFinder<Node>(LevelManager.graph, false);
        RequestPath();
        isRequested = false;

        timeEntered = TimeUtils.nanoTime();
    }

    @Override
    public void Update(float dt) {
        if(TimeUtils.nanoTime() - timeEntered > timeToChangePathfinding*2){
            zombie.ChangeState(new FlowFieldState());
        }

        if(zombie.target != null){

            if (!isRequested && targetHasMoved() && TimeUtils.nanoTime() - lastRequestTime > 1000000000/2) {
                RequestPath();
            }

            if(resultPath != null){
                //PathfindingDebugger.drawPath(resultPath);
                FollowRequestedPath();
            } else GoToTarget();
        }

        if(zombie.direction.angle() != 0) zombie.setRotation(zombie.direction.angle());
        zombie.body.setTransform(zombie.body.getPosition().x, zombie.body.getPosition().y, (float) Math.toRadians(zombie.getRotation()));
        zombie.body.setLinearVelocity(zombie.direction.x *zombie.SPEED * dt , zombie.direction.y * zombie.SPEED *dt);
        zombie.setPosition(zombie.body.getPosition().x - zombie.getWidth()/2, zombie.body.getPosition().y - zombie.getHeight()/2);

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
        if(targetX != MathUtils.round(zombie.target.getX() + zombie.target.getWidth()/2) ||
                targetY != MathUtils.round(zombie.target.getY() + zombie.target.getHeight()/2))
            return true;
        return false;
    }

    private void RequestPath(){
//        Gdx.app.log(this.toString(), "Requesting a path");
        isRequested = true;
        int startX = MathUtils.round(zombie.body.getPosition().x);
        int startY = MathUtils.round(zombie.body.getPosition().y);
        int endX = MathUtils.round(zombie.target.getX() + zombie.target.getWidth()/2);
        int endY = MathUtils.round(zombie.target.getY() + zombie.target.getHeight()/2);
        targetX = endX;
        targetY = endY;
        Node startNode = LevelManager.graph.getNodeByXY(startX, startY);
        Node endNode = LevelManager.graph.getNodeByXY(endX, endY);

//        try{
            PathfindingManager.getInstance().requestPathfinding(this, pathFinder, startNode, endNode);
//        } catch (Exception e){
//            Gdx.app.log("Pathfinding","" + e);
//            isRequested = false;
//        }
        lastRequestTime = TimeUtils.nanoTime();
    }

    private void FollowRequestedPath(){
        zombie.direction = getDirection().nor();
        if(MathStatic.getDistancePointToPoint(zombie.body.getPosition(),new Vector2(zombie.target.getX() + zombie.target.getWidth()/2,
                zombie.target.getY() + zombie.target.getHeight()/2)) <= zombie.RANGE){
            resultPath = null;
            waypoint = null;
            zombie.direction = Vector2.Zero;
            zombie.ChangeState(new ZombieAttackState());
        }
        else if(getDistanceFromWaypoint() < .5f) {
//            Gdx.app.log("Pathfinding", "Next waypoint");
            if (resultPath.getCount() <= 1) {
                resultPath = null;
                waypoint = null;
                zombie.direction = Vector2.Zero;
//                zombie.ChangeState(new ZombieAttackState());
            } else {
                // Set next waypoint
                int waypointIndex = resultPath.get(1).getIndex();
                waypoint = new Vector2(waypointIndex % LevelManager.WIDTH_IN_TILES * LevelManager.TILE_WIDTH + .5f,
                        waypointIndex / LevelManager.WIDTH_IN_TILES * LevelManager.TILE_HEIGHT + .5f);
                resultPath.removeIndex(0);
            }
        }
    }

    private Vector2 getDirection(){
        if(waypoint == null) return Vector2.Zero;
        float x = waypoint.x - zombie.getX();
        float y = waypoint.y - zombie.getY();
        return new Vector2(x,y);
    }

    private float getDistanceFromWaypoint(){
        if(waypoint == null) return 0f;

        float x1 = zombie.getX();
        float y1 = zombie.getY();
        float x2 = waypoint.x;
        float y2 = waypoint.y;

        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public void Exit() {
        pathFinder = null;
        resultPath = null;
    }

    @Override
    public void acceptPath(PathFinderRequest<Node> request) {
//        Gdx.app.log(this.toString(), "Got a path back");
        if (request != null && request.pathFound) {
            resultPath = (GraphPathImp) request.resultPath;
            zombie.distance = resultPath.getCount();
//            Gdx.app.log(this.toString(), "" + resultPath.getCount());
        }
        isRequested = false;
    }
//
//    public void reset() {
//        Gdx.app.log("Zombie " + zombie, "Restarting");
//        waypoint = null;
//        pathFinder = null;
//        resultPath = null;
//        isRequested = false;
//        count = 0;
//    }
}
