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

    public static int activeSector;

    public static void calcDistanceForEveryNode(float goalX, float goalY){
        FlowFieldManager.nodeGoalX = LevelManager.graph.getNodeByXYFloat(goalX, goalY).getX();
        FlowFieldManager.nodeGoalY = LevelManager.graph.getNodeByXYFloat(goalX, goalY).getY();

        setNodeGoal(LevelManager.graph.getNodeByXYTiles( nodeGoalX, nodeGoalY));
        setSectorGoal(LevelManager.graph.getNodeByXYTiles( nodeGoalX, nodeGoalY).sector);
    }

    public static void calcDistanceForMySector(float goalX, float goalY){
        FlowFieldManager.nodeGoalX = LevelManager.graph.getNodeByXYFloat(goalX, goalY).getX();
        FlowFieldManager.nodeGoalY = LevelManager.graph.getNodeByXYFloat(goalX, goalY).getY();

        setNodeGoal(LevelManager.graph.getNodeByXYTiles( nodeGoalX, nodeGoalY));
    }


    /**
     * Set node as goal for my sector
     * @param n
     */
    private static void setNodeGoal(Node n){
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
                        && !visited.get(auxN.index)
                        && auxN.sector == n.sector){
                    auxN.setDistance(n.getDistance()+1);
                    visited.set(auxN.index, true);
                    queue.addLast(auxN);
                }
            }
        }

        calcSectorFlow();
    }

    private static void calcSectorFlow(){
        for (int x = 0; x < LevelManager.WIDTH_IN_TILES ; x++) {
            for (int y = 0; y < LevelManager.HEIGHT_IN_TILES; y++) {

                if(LevelManager.graph.getNodeByXYTiles(x,y).sector != activeSector){
                    y += 19;
                    continue;
                }
                if(LevelManager.graph.getNodeByXYTiles(x,y).type == Node.Type.REGULAR)
                    LevelManager.graph.getNodeByXYTiles(x,y).calcSectorFlow();
            }
        }
    }

    /**
     * Calcula el flow de todos los sectores hacia el sector indicado
     * @param sector
     */
    private static void setSectorGoal(int sector){
        FlowFieldManager.activeSector = sector;

        Queue<Node> queue = new Queue<Node>();

        ArrayList<Boolean> visited = new ArrayList<Boolean>();

        for (Node node : LevelManager.graph.nodes) {
            visited.add( false);
            if(node.sector == sector){
                queue.addLast(node);
                visited.set(node.index, true);
                node.setDistance(0);
            }
        }

        Node n;

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

        calcFlow();
    }

    private static void calcFlow(){
        for (int x = 0; x < LevelManager.WIDTH_IN_TILES ; x++) {
            for (int y = 0; y < LevelManager.HEIGHT_IN_TILES; y++) {
                if(LevelManager.graph.getNodeByXYTiles(x,y).type == Node.Type.REGULAR) LevelManager.graph.getNodeByXYTiles(x,y).calcFlow();
            }
        }
    }

    public static int getMinX(){
        return 0; //(int) (nodeGoalX - Constants.RENDER_WIDTH/3);
    }

    public static int getMinY(){
        return 0; //(int) (nodeGoalY - Constants.RENDER_WIDTH/3);
    }

    public static int getMaxX(){
        return LevelManager.WIDTH_IN_TILES; //(int) (nodeGoalX + Constants.RENDER_WIDTH/3);
    }

    public static int getMaxY(){
        return LevelManager.HEIGHT_IN_TILES; // (nodeGoalY + Constants.RENDER_WIDTH/3);
    }

}
