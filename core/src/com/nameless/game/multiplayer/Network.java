package com.nameless.game.multiplayer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.nameless.game.actors.player.Player;

/**
 * Created by Raul on 18/06/2017.
 */

public class Network {
    static public final int port = 54355;

    // This registers objects that are going to be sent over the network.
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Login.class);
        kryo.register(RegistrationRequired.class);
        kryo.register(Register.class);
        kryo.register(AddPlayer.class);
        kryo.register(UpdateCharacter.class);
        kryo.register(RemoveCharacter.class);
        kryo.register(Character.class);
        kryo.register(MoveCharacter.class);
    }

    static public class Login {
        public String name;
    }

    static public class RegistrationRequired {
    }

    static public class Register {
        public String name;
        public String otherStuff;
    }

    static public class UpdateCharacter {
        public int id, x, y;
    }

    static public class AddPlayer {
        public Player player;
    }

    static public class RemoveCharacter {
        public int id;
    }

    static public class MoveCharacter {
        public int x, y;
    }
}