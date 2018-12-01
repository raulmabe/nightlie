package com.nameless.game.flowfield;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Queue;
import com.nameless.game.Constants;
import com.nameless.game.MathStatic;
import com.nameless.game.maps.LevelManager;
import com.nameless.game.pathfinding.Node;

import java.util.ArrayList;

public class FlowFieldManager {

    public static int nodeGoalX;
    public static int nodeGoalY;

    private static void calcFlowForEveryNode(){
        for (int x = getMinX(); x < getMaxX() ; x++) {
            for (int y = getMinY(); y < getMaxY(); y++) {
                if(LevelManager.graph.getNodeByXYTiles(x,y).type == Node.Type.REGULAR) LevelManager.graph.getNodeByXYTiles(x,y).calcFlow();
            }
        }
    }

    public static void calcDistanceForEveryNode(float goalX, float goalY){
        FlowFieldManager.nodeGoalX = LevelManager.graph.getNodeByXYFloat(goalX, goalY).getX();
        FlowFieldManager.nodeGoalY = LevelManager.graph.getNodeByXYFloat(goalX, goalY).getY();
        setGoal(LevelManager.graph.getNodeByXYTiles( nodeGoalX, nodeGoalY));
    }

    private static void setGoal(Node n){
        Queue<Node> queue = new Queue<Node>();
        queue.addLast(n);

        ArrayList<Boolean> visited = new ArrayList<Boolean>();
        for(int i = 0; i < LevelManager.graph.getNodeCount(); ++i)
            visited.add( false);

        visited.set(n.index, true);

        n.setDistance(0);

        while(queue.size != 0){
            n = queue.first();
            queue.removeFirst();

            for (Connection<Node> connection: n.connections){
                Node auxN = connection.getToNode();
                if(MathStatic.isBetween(auxN.getX(), getMinX(), getMaxX())
                        && MathStatic.isBetween(auxN.getY(), getMinY(), getMaxY())
                        && !visited.get(auxN.index)){
                    auxN.setDistance(n.getDistance()+1);
                    visited.set(auxN.index, true);
                    queue.addLast(auxN);
                }
            }

        }

        calcFlowForEveryNode();
    }

    public static int getMinX(){
        return (int) (nodeGoalX - Constants.RENDER_WIDTH/2);
    }

    public static int getMinY(){
        return (int) (nodeGoalY - Constants.RENDER_WIDTH/2);
    }

    public static int getMaxX(){
        return (int) (nodeGoalX + Constants.RENDER_WIDTH/2);
    }

    public static int getMaxY(){
        return (int) (nodeGoalY + Constants.RENDER_WIDTH/2);
    }

}
