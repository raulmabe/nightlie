package com.nameless.game.flowfield;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.nameless.game.maps.LevelManager;
import com.nameless.game.pathfinding.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class FlowFieldManager {

    static boolean firstTime = true;


    public static void calcDistanceForEveryNode(float goalX, float goalY){
        setGoal(LevelManager.graph.getNodeByXY((int) goalX, (int) goalY));
    }

    private static void setGoal(Node n){
        Queue<Node> queue = new Queue<Node>();
        queue.addLast(n);

        ArrayList<Boolean> visited = new ArrayList<Boolean>();
        for(int i = 0; i < LevelManager.graph.getNodeCount(); ++i)
            visited.add( false);

        visited.set(n.index, true);

        int distance = 0;

        while(queue.size != 0){
            n = queue.first();
            queue.removeFirst();

            n.setDistance(distance);
            distance ++;

            for (Connection<Node> connection: n.connections){
                Node auxN = connection.getToNode();
                if(!visited.get(auxN.index)){
                    auxN.setDistance(distance);
                    visited.set(auxN.index, true);
                    queue.addLast(auxN);
                }
            }

        }
    }

}
