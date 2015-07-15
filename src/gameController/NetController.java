package gameController;

import Common.exceptions.*;
import Judge.JudgeAbstract;
import Network.Client;
import gameEngine.GameEngine;
import gameEngine.Model.Player;
import gamePanel.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class NetController implements KeyListener{

    GamePanel panel;
    GameEngine engine;
    Client client;

    boolean ctrl;

    public void init(GameEngine engine, GamePanel panel, Client client) {
        this.engine = engine;
        this.panel = panel;
        this.client = client;
    }


    public void start() {
        Thread gameLoop = new Thread (new Runnable() {
            @Override
            public void run() {
                while (true) {
                    {
                        gameUpdate();
                        gameRender();
                    }

                    try {
                        Thread.sleep(1000 / GameController.FPS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        gameLoop.start();
    }

    private void gameRender() {
        panel.repaint();
    }

    private void gameUpdate() {
        try {
            engine.next50milis();
        } catch (GameEndException e) {
            int confirm = panel.showGameOverMessage();
            if(confirm == 0)
                engine.setup();
            else
                System.exit(0);
        }
    }


    public void moveSelf(int direction) throws BozorgExceptionBase {
        Player player = engine.getAllPlayers().get(0);
        engine.movePlayer(player, direction);
        panel.moveSound();
    }

    public void attackSelf(int direction) throws BozorgExceptionBase {
        Player player = engine.getAllPlayers().get(0);
        engine.movePlayer(player, direction);
        panel.attackSound();
    }

    public void giftSelf() throws BozorgExceptionBase {
        Player player = engine.getAllPlayers().get(0);
        engine.getGift(player);
    }

    public void fanSelf() throws CantThrowFanException {
        Player player = engine.getAllPlayers().get(0);
        engine.throwFan(player);
    }

    public void moveOpponent(int direction) throws CantMoveException {
        Player player = engine.getAllPlayers().get(1);
        engine.movePlayer(player, direction);
    }

    public void attackOpponent(int direction) throws CantMoveException {
        Player player = engine.getAllPlayers().get(1);
        engine.movePlayer(player, direction);
    }

    public void giftOpponent() throws CantGetGiftException {
        Player player = engine.getAllPlayers().get(1);
        engine.getGift(player);
    }

    public void fanOpponent() throws CantThrowFanException {
        Player player = engine.getAllPlayers().get(1);
        engine.throwFan(player);
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (panel.whichPlayer() != null) {
            try {
                switch (e.getKeyCode()) {

                    //First Player Keys
                    case KeyEvent.VK_LEFT:
                        if (ctrl) {
                            client.send("A" + String.valueOf(JudgeAbstract.LEFT));
                        } else {
                            client.send("M" + String.valueOf(JudgeAbstract.LEFT));
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (ctrl) {
                            client.send("A" + String.valueOf(JudgeAbstract.RIGHT));
                        } else {
                            client.send("M" + String.valueOf(JudgeAbstract.RIGHT));
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (ctrl) {
                            client.send("A" + String.valueOf(JudgeAbstract.DOWN));
                        } else {
                            client.send("M" + String.valueOf(JudgeAbstract.DOWN));
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (ctrl) {
                            client.send("A" + String.valueOf(JudgeAbstract.UP));
                        } else {
                            client.send("M" + String.valueOf(JudgeAbstract.UP));
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        client.send("G");
                        break;
                    case KeyEvent.VK_SPACE:
                        client.send("F");
                        break;
                    case KeyEvent.VK_CONTROL:
                        ctrl = true;
                        break;
                }
            } catch (IOException E) {

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_CONTROL:
                ctrl = false;
                break;
        }
    }
}