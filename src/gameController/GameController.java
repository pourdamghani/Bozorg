package gameController;

import Common.exceptions.*;
import gameEngine.GameEngine;
import Judge.JudgeAbstract;
import gameEngine.Model.Player;
import gamePanel.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class GameController implements KeyListener{

    private static final int FPS = 20;

    GamePanel panel;

    GameEngine engine;

    Thread gameloop;

    boolean running = false;
    boolean ctrl;
    boolean shift;

    public void init(GamePanel panel, GameEngine engine) {
        this.panel = panel;
        this.engine = engine;
    }

    public void start() {
        gameloop = new Thread (new Runnable() {
            @Override
            public void run() {
                running = true;
                while (running) {
                     {
                        gameUpdate ();
                        gameRender ();
                    }

                    try {
                        Thread.sleep(1000 / FPS);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        gameloop.start();
    }

    private void gameUpdate() {
        try {
            engine.next50milies();
        } catch (GameEndException E) {
            int confirm = panel.showGameOverMessage();
            if(confirm == 0)
                engine.setup();
            else
                System.exit(0);
        }

    }

    private void gameRender() {
        panel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ArrayList<Player> players = engine.getAllPlayers();
        Player firstPlayer = players.get(0);
        Player secondPlayer = players.get(1);

        if (panel.whichPlayer() != null) {
            try {
                switch (e.getKeyCode()) {

                    //First Player Keys
                    case KeyEvent.VK_LEFT:
                        if (ctrl) {
                            engine.attack(firstPlayer, JudgeAbstract.LEFT);
                            panel.paintAttack();
                        } else {
                            engine.movePlayer(firstPlayer, JudgeAbstract.LEFT);
                            panel.paintMove();

                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (ctrl) {
                            engine.attack(firstPlayer, JudgeAbstract.RIGHT);
                            panel.paintAttack();
                        } else {
                            engine.movePlayer(firstPlayer, JudgeAbstract.RIGHT);
                            panel.paintMove();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (ctrl) {
                            engine.attack(firstPlayer, JudgeAbstract.DOWN);
                            panel.paintAttack();
                        } else {
                            engine.movePlayer(firstPlayer, JudgeAbstract.DOWN);
                            panel.paintMove();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (ctrl) {
                            engine.attack(firstPlayer, JudgeAbstract.UP);
                            panel.paintAttack();
                        } else {
                            engine.movePlayer(firstPlayer, JudgeAbstract.UP);
                            panel.paintMove();
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        panel.showGift(engine.getGift(firstPlayer));
                        break;
                    case KeyEvent.VK_SPACE:
                        engine.throwFan(firstPlayer);
                        break;
                    case KeyEvent.VK_CONTROL:
                        ctrl = true;
                        break;


                    //Second Player Keys
                    case KeyEvent.VK_A:
                        if (shift) {
                            engine.attack(secondPlayer, JudgeAbstract.LEFT);
                            panel.paintAttack();
                        } else {
                            engine.movePlayer(secondPlayer, JudgeAbstract.LEFT);
                            panel.paintMove();

                        }
                        break;
                    case KeyEvent.VK_D:
                        if (shift) {
                            engine.attack(secondPlayer, JudgeAbstract.RIGHT);
                            panel.paintAttack();
                        } else {
                            engine.movePlayer(secondPlayer, JudgeAbstract.RIGHT);
                            panel.paintMove();
                        }
                        break;
                    case KeyEvent.VK_S:
                        if (shift) {
                            engine.attack(secondPlayer, JudgeAbstract.DOWN);
                            panel.paintAttack();
                        } else {
                            engine.movePlayer(secondPlayer, JudgeAbstract.DOWN);
                            panel.paintMove();
                        }
                        break;
                    case KeyEvent.VK_W:
                        if (shift) {
                            engine.attack(secondPlayer, JudgeAbstract.UP);
                            panel.paintAttack();
                        } else {
                            engine.movePlayer(secondPlayer, JudgeAbstract.UP);
                            panel.paintMove();
                        }
                        break;
                    case KeyEvent.VK_F:
                        panel.showGift(engine.getGift(secondPlayer));
                        break;
                    case KeyEvent.VK_ALT:
                        engine.throwFan(secondPlayer);
                        break;
                    case KeyEvent.VK_SHIFT:
                        shift = true;
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
                ctrl = false;
                shift = false;
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_CONTROL:
                ctrl = false;
                break;

            case KeyEvent.VK_SHIFT:
                shift = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
