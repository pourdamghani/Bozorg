package graphic;

import Controller.GameEngine;
import Judge.JudgeAbstract;
import Model.Cell;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Iman on 7/5/2015.
 */
public class GamePanel extends JPanel {
    /**
     *
     */
    private BozorgPanel bozorgPanle;

    private JComboBox comboBox;

    public void init(final GameController controller, GameEngine engine) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bozorgPanle = new BozorgPanel();
        bozorgPanle.init(controller, engine);
        add(bozorgPanle, BorderLayout.CENTER);

        String s[] = new String[5];
        s[0] = "SAMAN";s[1] =  "JAFAR";s[2] =  "REZA" ;s[3] = "HASIN";s[4] = "ALL";
        comboBox.addItem(s);

        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                bozorgPanle.setPlayer((String)comboBox.getSelectedItem());
            }
        });

        JPanel bottons = new JPanel();
        bottons.setLayout(new BoxLayout(bottons, BoxLayout.LINE_AXIS));
        bottons.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottons.add(Box.createHorizontalGlue());

        bottons.add(comboBox);

        add(bottons, BorderLayout.PAGE_START);
    }

    public void showGameOverMessage() {
        JOptionPane.showMessageDialog(this, "Game is over!");
    }

}

class BozorgPanel extends JPanel{

    /**
     *
     */

    public static final int CellSize = 10;

    public int WIDTH;

    public int HEIGHT;

    Player player;

    boolean allMapSeen = false;

    GameEngine engine;

    GameController controller;

    public void init(GameController controller, GameEngine engine) {
        this.engine = engine;
        this.controller = controller;

        WIDTH = CellSize * engine.getWidth();
        HEIGHT = CellSize * engine.getHeight();

   //     addKeyListener(controller);
        setFocusable(true);
        requestFocus();
        setLayout(null);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        paintMap(g2d);
    }

    private void paintMap(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        for(int i = 0; i < engine.getHeight(); i++)
            for(int j = 0; j < engine.getWidth(); j++){
                int wall = engine.getMapWallType(i,j);

                if(wall % 4 == 2 || wall % 4 == 3)
                    g2d.drawLine(j + CellSize / 2, i - CellSize / 2, j + CellSize / 2, i + CellSize / 2);

                if(wall % 8 > 3 && wall % 8 < 8)
                    g2d.drawLine(j - CellSize / 2, i + CellSize / 2, j + CellSize / 2, i + CellSize / 2);
        }

    }

    public void setPlayer(String s){
        if(s.equals("all"))
            allMapSeen = true;
        else{
            player = engine.stringToPlayer(s);
        }
    }

}
