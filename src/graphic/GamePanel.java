package graphic;

import Controller.GameEngine;
import Judge.JudgeAbstract;
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
    private BozorgPanel bozorgPanel;

    private JComboBox comboBox;

    public void init(final GameController controller, GameEngine engine) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ArrayList<Player> players = engine.getPlayers();
        String s[] = new String[players.size() + 1];
        for(int i = 0; i < players.size(); i++) {
            int name = players.get(i).getInfo(JudgeAbstract.NAME);
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
        s[players.size()] = "ALL";
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
    }

    public void showGameOverMessage() {
        JOptionPane.showMessageDialog(this, "Game is over!");
    }

    public void showCantMove(){
        JOptionPane.showMessageDialog(this, "cant move!");
    }

    public String whichPlayer(){
        if (comboBox.getSelectedItem().equals("ALL"))
            return null;
        return (String)comboBox.getSelectedItem();
    }

}

class BozorgPanel extends JPanel{


    /**
     *
     */

    public static final int CellSize = 20;

    public int WIDTH;

    public int HEIGHT;

    String player;

    boolean allMapSeen = true;

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
     //   paintFans(g2d);
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
                paintCell(g2d, j * CellSize , i * CellSize,color);
                paintWall(g2d, i, j);
            }
    }
    private void paintWall(Graphics2D g2d, int i, int j){
        int wall = engine.getMapWallType(i, j);
        g2d.setColor(Color.BLACK);
        if (wall % 4 == 2 || wall % 4 == 3)
            g2d.fillRect((j + 1) * CellSize , i * (CellSize), 3, CellSize);
        if (wall % 8 > 3 && wall % 8 < 8)
            g2d.fillRect((j) * CellSize, (i + 1) * (CellSize), CellSize, 3);
    }
    private void paintCell(Graphics2D g2d,int x, int y, Color color){
       // System.out.println(x+" "+y);
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
        g2d.fillOval(player.getInfo(JudgeAbstract.COL) * CellSize, player.getInfo(JudgeAbstract.ROW) * CellSize , CellSize, CellSize);
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
