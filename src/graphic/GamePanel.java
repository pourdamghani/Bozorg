package graphic;

import Controller.GameEngine;
import Judge.JudgeAbstract;
import Model.Cell;
import Model.Fan;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

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

        String s[] = {"SAMAN", "JAFAR", "REZA", "HASIN", "ALL"};
        s[0] = "SAMAN";s[1] =  "JAFAR";s[2] =  "REZA" ;s[3] = "HASIN";s[4] = "ALL";
        comboBox = new JComboBox(s);

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

    public void showCantMove(){
        JOptionPane.showMessageDialog(this, "cant move!");
    }

    public String whichPlayer(){
        return (String)comboBox.getSelectedItem();
    }

}

class BozorgPanel extends JPanel{

    /**
     *
     */

    public static final int CellSize = 10;

    public int WIDTH;

    public int HEIGHT;

    String player;

    boolean allMapSeen = false;

    GameEngine engine;

    GameController controller;

    public void init(GameController controller, GameEngine engine) {
        this.engine = engine;
        this.controller = controller;

        WIDTH = CellSize * engine.getWidth();
        HEIGHT = CellSize * engine.getHeight();

        addKeyListener(controller);
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
        paintPlayers(g2d);
        paintFans(g2d);
//        paintGifts(g2d);
    }

    private void paintMap(Graphics2D g2d){
        for(int i = 0; i < engine.getHeight(); i++)
            for(int j = 0; j < engine.getWidth(); j++) {
                int kind;
                if (allMapSeen)
                    kind = engine.getMapCellType(i,j);
                else
                    kind =engine.getMapCellType(i, j, engine.stringToPlayer(player));
                Color color;
                switch (kind){
                    case JudgeAbstract.DARK_CELL:
                        color = Color.black;
                        break;
                    case JudgeAbstract.BONUS_CELL:
                        color = Color.cyan;
                        break;
                    case JudgeAbstract.NONE_CELL:
                        color = Color.white;
                        break;
                    case JudgeAbstract.JJ_CELL:
                        color = Color.orange;
                        break;
                    default:
                        color = Color.magenta;
                }
                paintCell(g2d, j - CellSize / 2, i - CellSize / 2,color);
                paintWall(g2d,i,j);
            }
    }
    private void paintWall(Graphics2D g2d, int i, int j){
        int wall = engine.getMapWallType(i, j);
        g2d.setColor(Color.BLACK);
        if (wall % 4 == 2 || wall % 4 == 3)
            g2d.drawLine(j + CellSize / 2, i - CellSize / 2, j + CellSize / 2, i + CellSize / 2);
        if (wall % 8 > 3 && wall % 8 < 8)
            g2d.drawLine(j - CellSize / 2, i + CellSize / 2, j + CellSize / 2, i + CellSize / 2);
    }
    private void paintCell(Graphics2D g2d,int x, int y, Color color){
        g2d.setColor(color);
        g2d.fillRect(x,y,CellSize,CellSize);
    }
    private void paintPlayers(Graphics2D g2d) {
        if (allMapSeen)
            for (Player i : engine.getPlayers())
                paintPlayer(g2d, i);
        else
            for (Player i : engine.getPlayersInVision(engine.stringToPlayer(player)))
                paintPlayer(g2d, i);
    }

    private void paintPlayer(Graphics2D g2d, Player player){
        g2d.setColor(player.getColor());
        g2d.fillOval(player.getInfo(JudgeAbstract.COL), player.getInfo(JudgeAbstract.ROW), CellSize, CellSize);
    }

    private void paintFans(Graphics2D g2d){
        //TODO
    }

    private void paintFan(Graphics2D g2d, Fan fan){
        g2d.setColor(fan.getColor());
        g2d.fillRect(fan.getInfo(JudgeAbstract.COL) - CellSize / 4 , fan.getInfo(JudgeAbstract.ROW) - CellSize / 4, CellSize / 2, CellSize / 2);
    }

//    private void paintGifts(Graphics2D g2d){
//        for(int i = 0; i < engine.getHeight(); i++)
//            for(int j = 0; j < engine.getWidth(); j++)
//                if(allMapSeen || engine.getMapCellType(i, j, engine.stringToPlayer(player)) == JudgeAbstract.BONUS_CELL){
//                    g2d.setColor(Color.red);
//                    g2d.fillRect(j - CellSize / 2, i - CellSize / 2, CellSize, CellSize);
//                }
//    }

    public void setPlayer(String s){
        if(s.equals("ALL"))
            allMapSeen = true;
        else{
            player = s;
            allMapSeen = false;
        }
    }

}
