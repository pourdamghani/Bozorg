package graphic;

import Common.exceptions.BozorgExceptionBase;
import Controller.GameEngine;
import Judge.JudgeAbstract;
import Model.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Iman on 7/5/2015.
 */
public class GameController implements KeyListener{

    private static final int FPS = 20;

    GamePanel panel;

    GameEngine engine;

    Thread gameloop;

    boolean running = false;
    boolean ctrl;

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
        engine.next50milies();
    }

    private void gameRender() {
        panel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (panel.whichPlayer() != null) {
            Player player = engine.stringToPlayer(panel.whichPlayer());
            try {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (ctrl) {
                            engine.attack(player, JudgeAbstract.LEFT);
                            panel.paintAttack();
                        } else {
                            engine.movePlayer(player, JudgeAbstract.LEFT);
                            panel.paintMove();

                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (ctrl) {
                            engine.attack(player, JudgeAbstract.RIGHT);
                            panel.paintAttack();
                        } else {
                            engine.movePlayer(player, JudgeAbstract.RIGHT);
                            panel.paintMove();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (ctrl) {
                            engine.attack(player, JudgeAbstract.DOWN);
                            panel.paintAttack();
                        } else {
                            engine.movePlayer(player, JudgeAbstract.DOWN);
                            panel.paintMove();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (ctrl) {
                            engine.attack(player, JudgeAbstract.UP);
                            panel.paintAttack();
                        } else {
                            engine.movePlayer(player, JudgeAbstract.UP);
                            panel.paintMove();
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        engine.getGift(player);
                        break;
                    case KeyEvent.VK_SPACE:
                        engine.throwFan(player);
                        break;
                    case KeyEvent.VK_CONTROL:
                        ctrl = true;
                        break;
                }
            } catch (BozorgExceptionBase E) {
                panel.showCantMove();
                ctrl = false;
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

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
