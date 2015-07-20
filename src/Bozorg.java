import gameEngine.GameEngine;
import gameEngine.Model.Generator.Gen;
import gameController.GameController;
import gamePanel.GamePanel;
import AI.AI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


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

        AI ai = null;
        boolean isAI = false;
        String[] players = {"SAMAN", "REZA", "JAFAR", "HASIN"};
        Random random = new Random();
        int rand = random.nextInt(4);
        if (!chooseMode(frame).equals("AI")) {
            choosePlayers(mapGen, frame, null);
        } else {
            choosePlayers(mapGen, frame, players[rand]);
            isAI = true;
        }


        engine.loadMap(mapGen.getMap(), mapGen.getWalls(), mapGen.getPlayers());
        engine.setup();
        if (isAI)
            ai = new AI(engine, engine.stringToPlayer(players[rand]));


        panel.init (controller, engine);
        controller.init(panel, engine, ai);
        controller.start();

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static String chooseMode(JFrame frame) {
        String[] modes = {"Manual", "AI"};
        String mode = (String) JOptionPane.showInputDialog(frame, "Choose mode", "Mode", JOptionPane.QUESTION_MESSAGE, null, modes, modes[0]);
        if (mode == null)
            System.exit(0);
        return mode;
    }

    private static void choosePlayers(Gen mapGen, JFrame frame, String AIPlayer) {
        ArrayList<String> p = new ArrayList<String>();
        p.add("SAMAN");
        p.add("JAFAR");
        p.add("REZA");
        p.add("HASIN");
        String[] players = new String[4];
        p.toArray(players);
        String firstPlayer;
        if (AIPlayer == null) {
            firstPlayer = (String) JOptionPane.showInputDialog(frame,
                    "Choose your player :",
                    "First Player",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    players,
                    players[0]);
            if (firstPlayer == null)
                System.exit(0);
        } else {
            firstPlayer = AIPlayer;
        }
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
