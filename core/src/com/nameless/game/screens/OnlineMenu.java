package com.nameless.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.nameless.game.MainGame;
import com.nameless.game.multiplayer.ClientProgram;
import com.nameless.game.multiplayer.Network;
import com.nameless.game.multiplayer.ServerProgram;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by Raul on 18/06/2017.
 */

public class OnlineMenu extends BasicScreen {

    TextButton serverButton, clientButton;

    public OnlineMenu(MainGame game) {
        super(game);
    }

    @Override
    public void setUpInterface(Table table) {
        serverButton = new TextButton("Server", game.getSkin());
        serverButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ServerProgram server;
                try {
                    server = new ServerProgram();
                    server.server.addListener(new Listener(){

                        @Override
                        public void connected(Connection connection) {
                            super.connected(connection);
                            System.out.println("Connected called server side");
                        }

                        @Override
                        public void disconnected(Connection connection) {
                            super.disconnected(connection);
                            System.out.println("Disconnected called server side");
                        }

                        @Override
                        public void received(Connection connection, Object object) {
                            super.received(connection, object);
                            System.out.println("Received called server side");
                            if(object instanceof Network.Login)  {
                                Network.Login login = (Network.Login) object;
                                System.out.println("The message is " + login.name);
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });
        clientButton = new TextButton("Client", game.getSkin());
        clientButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final ClientProgram client = new ClientProgram();
                client.client.addListener(new Listener(){

                    @Override
                    public void connected(Connection connection) {
                        super.connected(connection);
                        System.out.println("Connected called client side");
                        Network.Login login = new Network.Login();
                        login.name = "Petao";
                        client.client.sendTCP(login);
                    }

                    @Override
                    public void disconnected(Connection connection) {
                        super.disconnected(connection);
                        System.out.println("Disconnected called client side");
                    }

                    @Override
                    public void received(Connection connection, Object object) {
                        super.received(connection, object);
                        System.out.println("Received called client side");
                    }
                });
            }
        });

        table.add(serverButton).pad(30).row();
        table.add(clientButton).pad(30);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        super.show();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(getStage());
        Gdx.input.setInputProcessor(multiplexer);


    }
}
