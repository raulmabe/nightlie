package com.nameless.game.multiplayer;


import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class ClientProgram {

    public Client client;

    public ClientProgram() {
        client = new Client();
        client.start();

        Network.register(client);

        String host = "localhost";
        try {
            client.connect(5000, host, Network.port);
            // Server communication after connection can go here, or in Listener#connected().
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main (String[] args) {
        new ClientProgram();
    }
}