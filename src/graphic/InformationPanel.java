package graphic;

import Controller.GameEngine;
import Judge.Judge;
import Judge.JudgeAbstract;
import Model.Player;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by yashardabiran on 7/11/15.
 */
public class InformationPanel extends JPanel{
    ImageIcon reza = new ImageIcon("src/Image/Reza.png");
    ImageIcon hasin = new ImageIcon("src/Image/Hasin.png");
    ImageIcon jafar = new ImageIcon("src/Image/jafar.png");
    ImageIcon saman = new ImageIcon("src/Image/Saman.png");

    public InformationPanel(GameEngine engine) {

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel rezaLabel = new JLabel("Reza", reza, SwingConstants.LEFT);
        JLabel hasinLabel = new JLabel("Hasin", hasin, SwingConstants.LEFT);
        JLabel jafarLabel = new JLabel("Jafar", jafar, SwingConstants.LEFT);
        JLabel samanLabel = new JLabel("Saman", saman, SwingConstants.LEFT);

        ArrayList<Player> players = engine.getAllPlayers();
        for (Player player : players) {
            switch (player.getInfo(JudgeAbstract.NAME)) {
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

            JLabel health = new JLabel("Health : " + player.getInfo(JudgeAbstract.HEALTH) );
            this.add(health);
        }

    }
}
