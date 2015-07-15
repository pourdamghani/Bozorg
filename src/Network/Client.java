package Network;

import Common.exceptions.*;
import gameController.NetController;
import gameEngine.GameEngine;
import gamePanel.GamePanel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private GameEngine engine;
    private NetController controller;
    private GamePanel panel;

    public void init(GameEngine engine, NetController controller, GamePanel panel) {
        this.engine = engine;
        this.controller = controller;
        this.panel = panel;
    }

    public void start() throws Exception{
        Socket socket = new Socket("127.0.0.1", 9999);

        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }

    public void send(String string) throws IOException {
        out.writeUTF(string);
        out.flush();
    }

    private void recieve() throws IOException {
        while (!engine.isGameOver()) {
            String input = in.readUTF();

            if (input.charAt(0) == 'S') {
                self(input.substring(1));
            } else if (input.charAt(0) == 'O'){
                opponent(input.substring(1));
            } else {
                engine.setGameOver(true);
            }
        }
    }

    private void self(String s) {
        try {
            switch (s.charAt(0)) {
                case 'M':
                    controller.moveSelf(Integer.parseInt(s.substring(1)));
                    break;
                case 'A':
                    controller.attackSelf(Integer.parseInt(s.substring(1)));
                    break;
                case 'G':
                    controller.giftSelf();
                    break;
                case 'F':
                    controller.fanSelf();
                    break;
            }
        } catch (BozorgExceptionBase E) {
            if (E.getClass().equals(CantMoveException.class))
                panel.showCant("Move");
            else if (E.getClass().equals(CantGetGiftException.class))
                panel.showCant("GetGift");
            else if (E.getClass().equals(CantAttackException.class))
                panel.showCant("Attack");
            else if (E.getClass().equals(CantThrowFanException.class))
                panel.showCant("Throw Fan");
        }
    }

    private void opponent(String s) {
        try {
            switch (s.charAt(0)) {
                case 'M':
                    controller.moveOpponent(Integer.parseInt(s.substring(1)));
                    break;
                case 'A':
                    controller.attackOpponent(Integer.parseInt(s.substring(1)));
                    break;
                case 'G':
                    controller.giftOpponent();
                    break;
                case 'F':
                    controller.fanOpponent();
                    break;
            }
        } catch (BozorgExceptionBase E) {
            if (E.getClass().equals(CantMoveException.class))
                panel.showCant("Move");
            else if (E.getClass().equals(CantGetGiftException.class))
                panel.showCant("GetGift");
            else if (E.getClass().equals(CantAttackException.class))
                panel.showCant("Attack");
            else if (E.getClass().equals(CantThrowFanException.class))
                panel.showCant("Throw Fan");
        }
    }
}
