import Controller.GameEngine;
import Model.Generator.Gen;
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
        JFrame frame = new JFrame("Bozorg");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GamePanel panel = new GamePanel ();
        GameEngine engine = new GameEngine ();
        GameController controller = new GameController();

        Gen mapGen = new Gen();


        panel.init (controller, engine);
        controller.init (panel, engine);
        controller.start();

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
