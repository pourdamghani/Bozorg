import Controller.GameEngine;
import graphic.GameController;
import graphic.GamePanel;

import javax.swing.*;

/**
 * Created by PARDAZESH on 7/5/2015.
 */
public class Bozorg {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel panel = new GamePanel ();
        frame.getContentPane().add(panel);

        GameEngine engine = new GameEngine ();

        GameController controller = new GameController();

        panel.init (controller, engine);
        //controller.init (panel, engine);
        //controller.start ();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
