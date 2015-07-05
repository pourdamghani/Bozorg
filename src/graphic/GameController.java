package graphic;

import Controller.GameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Iman on 7/5/2015.
 */
public class GameController implements KeyListener{

    private static final int FPS = 30;

    GamePanel panel;

    GameEngine engine;

    Thread gameloop;

    boolean running = false;

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
        //TODO
    }

    private void gameRender() {
        panel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                break;
            case KeyEvent.VK_RIGHT:
                break;
            case KeyEvent.VK_DOWN:
                break;
            case KeyEvent.VK_UP:
                break;
            case KeyEvent.VK_ENTER:
                break;


        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
