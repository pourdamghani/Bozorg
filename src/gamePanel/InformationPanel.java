package gamePanel;

import gameEngine.GameEngine;
import Judge.JudgeAbstract;
import gameEngine.Model.Player;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class InformationPanel extends JPanel{
    ImageIcon reza = new ImageIcon("src/Image/Reza.png");
    ImageIcon hasin = new ImageIcon("src/Image/Hasin.png");
    ImageIcon jafar = new ImageIcon("src/Image/Jafar.png");
    ImageIcon saman = new ImageIcon("src/Image/Saman.png");

    GameEngine engine;
    JLabel firstHealthLabel, secondHealthLabel,firstFanLabel,secondFanLabel;
    ArrayList<Player> players;

    public InformationPanel(GameEngine engine) {
        this.engine = engine;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel rezaLabel = new JLabel("Reza", reza, SwingConstants.LEFT);
        JLabel hasinLabel = new JLabel("Hasin", hasin, SwingConstants.LEFT);
        JLabel jafarLabel = new JLabel("Jafar", jafar, SwingConstants.LEFT);
        JLabel samanLabel = new JLabel("Saman", saman, SwingConstants.LEFT);

        players = engine.getAllPlayers();
        switch (players.get(0).getInfo(JudgeAbstract.NAME)) {
            case JudgeAbstract.SAMAN:
                this.add(samanLabel);
                break;
            case JudgeAbstract.JAFAR:
                this.add(jafarLabel);
                break;
            case JudgeAbstract.REZA:
                this.add(rezaLabel);
                break;
            case JudgeAbstract.HASIN:
                this.add(hasinLabel);
                break;
        }

        firstHealthLabel = new JLabel("Health : " + players.get(0).getInfo(JudgeAbstract.HEALTH));
        this.add(firstHealthLabel);
        firstFanLabel = new JLabel("Fans : " + players.get(0).getInfo(JudgeAbstract.FANS));
        this.add(firstFanLabel);
        switch (players.get(1).getInfo(JudgeAbstract.NAME)) {
            case JudgeAbstract.SAMAN:
                this.add(samanLabel);
                break;
            case JudgeAbstract.JAFAR:
                this.add(jafarLabel);
                break;
            case JudgeAbstract.REZA:
                this.add(rezaLabel);
                break;
            case JudgeAbstract.HASIN:
                this.add(hasinLabel);
                break;
        }

        secondHealthLabel = new JLabel("Health : " + players.get(1).getInfo(JudgeAbstract.HEALTH));
        this.add(secondHealthLabel);
        secondFanLabel = new JLabel("Fans :" + players.get(1).getInfo(JudgeAbstract.FANS));
        this.add(secondFanLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        firstHealthLabel.setText("Health : " + players.get(0).getInfo(JudgeAbstract.HEALTH));
        secondHealthLabel.setText("Health : " + players.get(1).getInfo(JudgeAbstract.HEALTH));
        firstFanLabel.setText("Fans :" + players.get(0).getInfo(JudgeAbstract.FANS));
        secondFanLabel.setText("Fans :" + players.get(1).getInfo(JudgeAbstract.FANS));
    }
}
