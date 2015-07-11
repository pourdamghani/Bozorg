import gameEngine.GameEngine;
import gameEngine.Model.Generator.Gen;
import gameController.GameController;
import gamePanel.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        Image iconImage = new ImageIcon("src/Image/icon.png").getImage();
        frame.setIconImage(iconImage);

        GamePanel panel = new GamePanel ();
        GameEngine engine = new GameEngine ();
        GameController controller = new GameController();

        Gen mapGen = new Gen();
        choosePlayers(mapGen, frame);

        engine.loadMap(mapGen.getMap(), mapGen.getWalls(), mapGen.getPlayers());
        engine.setup();

        panel.init (controller, engine);
        controller.init (panel, engine);
        controller.start();

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static void choosePlayers(Gen mapGen, JFrame frame) {
        ArrayList<String> p = new ArrayList<String>();
        p.add("SAMAN");
        p.add("JAFAR");
        p.add("REZA");
        p.add("HASIN");
        String[] players = new String[4];
        p.toArray(players);

        String firstPlayer = (String) JOptionPane.showInputDialog(frame,
                "Choose your player :",
                "First Player",
                JOptionPane.QUESTION_MESSAGE,
                null,
                players,
                players[0]);
        if (firstPlayer == null)
            System.exit(0);

        mapGen.setPlayers(whichPlayer(firstPlayer), 0);

        p.remove(firstPlayer);
        players = new String[3];
        p.toArray(players);

        String secondPlayer = (String) JOptionPane.showInputDialog(frame,
                "Choose your player :",
                "Second Player",
                JOptionPane.QUESTION_MESSAGE,
                null,
                players,
                players[0]);
        if (secondPlayer == null)
            System.exit(0);

        mapGen.setPlayers(whichPlayer(secondPlayer), 1);
    }

    private static int whichPlayer(String player) {

        if (player.equals("SAMAN") )
            return 0;
        else if (player.equals("JAFAR") )
            return 1;
        else if (player.equals("REZA") )
            return 2;
        else return 3;
    }
}
