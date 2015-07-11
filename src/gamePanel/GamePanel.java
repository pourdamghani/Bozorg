package gamePanel;

import gameController.GameController;
import gameEngine.GameEngine;
import Judge.JudgeAbstract;
import gameEngine.Model.Player;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    private BozorgPanel bozorgPanel;

    private JComboBox comboBox;


    public void init(final GameController controller, GameEngine engine) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ArrayList<Player> players = engine.getPlayers();
        String s[] = new String[players.size() + 2];
        s[0] = "ALL";
        for(int i = 1; i <= players.size(); i++) {
            int name = players.get(i - 1).getInfo(JudgeAbstract.NAME);
            switch (name) {
                case JudgeAbstract.SAMAN:
                    s[i] = "SAMAN";
                    break;
                case JudgeAbstract.JAFAR:
                    s[i] = "JAFAR";
                    break;
                case JudgeAbstract.HASIN:
                    s[i] = "HASIN";
                    break;
                case JudgeAbstract.REZA:
                    s[i] = "REZA";
                    break;
            }
        }
        s[players.size() + 1] = "BOTH";
        comboBox = new JComboBox(s);

        add(comboBox, BorderLayout.PAGE_START);

        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                bozorgPanel.setPlayer((String) comboBox.getSelectedItem());
            }
        });


        bozorgPanel = new BozorgPanel();
        bozorgPanel.init(controller, engine);
        add(bozorgPanel, BorderLayout.CENTER);

        InformationPanel informationPanel = new InformationPanel(engine);
        add(informationPanel, BorderLayout.LINE_START);
    }

    public int showGameOverMessage() {
        try {
            InputStream in = new FileInputStream("src/Sound/victory.wav");
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int n = JOptionPane.showConfirmDialog(this, "Victory! \n Would you like to start again?");
        if (n > 0)
            System.exit(0);

        return n;
    }

    public void showCant(String s) {
        JOptionPane.showMessageDialog(this, "You cant " + s + "!", "Watch Out", JOptionPane.ERROR_MESSAGE);
    }

    public String whichPlayer(){
        if (comboBox.getSelectedItem().equals("ALL"))
            return null;
        return (String)comboBox.getSelectedItem();
    }

    public void attackSound() {
        try {
            InputStream in = new FileInputStream("src/Sound/fight.wav");
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveSound() {
        try {
            InputStream in = new FileInputStream("src/Sound/walk.wav");
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showGift(int gift) {
        String Message = "Nothing here";
        if (gift == JudgeAbstract.SPEEDUP_CELL)
            Message = "You have Nitro for 5 sec, Speed Up!";
        if (gift == JudgeAbstract.HOSPITAL_CELL)
            Message = "First Aid Kit! Health increases";
        if (gift == JudgeAbstract.FAN_CELL)
            Message = "Every body loves You! 3 extra fans";
        if (gift == JudgeAbstract.JUMP_CELL)
            Message = "Magical shoes! You Can Jump over the walls for 2 sec";
        if (gift == JudgeAbstract.STONE_CELL)
            Message = "You're Stoned! Can't play for 3 sec";
        if (gift == JudgeAbstract.RADAR_CELL)
            Message = "Sun rises! You can See EveryWhere for 3 sec";
        JOptionPane.showMessageDialog(this, Message, "Gift", JOptionPane.INFORMATION_MESSAGE);
    }
}

