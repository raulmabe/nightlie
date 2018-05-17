package com.nameless.game.multiplayer;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.nameless.game.actors.player.Player;

import java.io.IOException;
import java.util.HashSet;

public class ServerProgram {

    public Server server;
    public HashSet<Player> loggedIn = new HashSet<Player>();


    public ServerProgram() throws IOException {
        server = new Server() {
            protected Connection newConnection () {
                // By providing our own connection implementation, we can store per
                // connection state without a connection ID to state look up.
                return new PlayerConnection();
            }
        };
        Network.register(server);
        server.bind(Network.port);
        server.start();
        System.out.println("Server started");

    }

    void loggedIn (PlayerConnection c, Player player) {
        c.player = player;

        // Add existing characters to new logged in connection.
        for (Player other : loggedIn) {
            Network.AddPlayer addPlayer = new Network.AddPlayer();
            addPlayer.player = other;
            c.sendTCP(addPlayer);
        }

        loggedIn.add(player);

        // Add logged in character to all connections.
        Network.AddPlayer addPlayer = new Network.AddPlayer();
        addPlayer.player = player;
        server.sendToAllTCP(addPlayer);
    }

    static class PlayerConnection extends Connection {
        public Player player;
    }

    public static void main (String[] args) throws IOException {
        new ServerProgram();
    }
}
