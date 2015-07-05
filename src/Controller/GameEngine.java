package Controller;

import Common.exceptions.BozorgExceptionBase;
import Judge.Judge;
import Judge.JudgeAbstract;
import Model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class GameEngine {
    private Map map;
    private ArrayList<Player> players;
    private Integer time = 0;
    private GameLogic logic = new GameLogic();


    public int getWidth() {
        return map.getWidth();
    }

    public int getHeight() {
        return map.getHeight();
    }

    public Integer getTime() {
        return time;
    }

    public HashMap<String, Integer> getInfo(Person person) {
        return person.getInfo();
    }

    public int getMapCellType(int row, int col) {
        return map.getCellType(row, col);
    }

    public int getMapCellType(int row, int col, Player player) {
        if (!logic.canSee(player, map, row, col))
            return JudgeAbstract.DARK_CELL;
        if (getMapCellType(row, col) > JudgeAbstract.START_CELL)
            return JudgeAbstract.BONUS_CELL;
        if (getMapCellType(row, col) == JudgeAbstract.JJ_CELL && !canSeeJJ())
            return JudgeAbstract.NONE_CELL;
        return getMapCellType(row, col);
    }

    public int getMapWallType(int row, int col) {
        return map.getWallType(row, col);
    }

    public int getMapWallType(int row, int col, Player player) {
        if (!logic.canSee(player, map, row, col))
            return JudgeAbstract.XXXX_WALL;
        return getMapWallType(row, col);
    }



    public ArrayList<Player> loadMap(int[][] cellsType, int[][] wallsType, int[] players) {

        int width = cellsType.length;
        int height = cellsType[0].length;

        //Initializing map
        map = new Map(width, height);
        map.loadMap(cellsType, wallsType);

        //Placing players in the map
        int iter = 0;
        this.players = new ArrayList<Player>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map.getCellType(i, j) == JudgeAbstract.START_CELL) {
                    this.players.add(new Player());
                    Player player = this.players.get(iter);
                    player.updateInfo(JudgeAbstract.ROW, i); //Setting Row
                    player.updateInfo(JudgeAbstract.COL, i); //Setting Col
                    player.updateInfo(JudgeAbstract.NAME, players[iter]);
                    iter++;
                }
            }
        }

        return this.players;
    }



    /**
     * Setting up players default abilities
     */
    private void setupPlayer(Player player) {
        player.updateInfo(JudgeAbstract.IS_WINNER, JudgeAbstract.NOT_FINISHED);
        player.updateInfo(JudgeAbstract.IS_ALIVE, JudgeAbstract.ALIVE);
        player.updateInfo(JudgeAbstract.HEALTH, Judge.MAX_Health);
        player.setDeadTime(0);
        switch (player.getInfo(JudgeAbstract.NAME)) {
            case JudgeAbstract.SAMAN:
                setupSaman(player);
                break;

            case JudgeAbstract.JAFAR:
                setupJafar(player);
                break;

            case JudgeAbstract.HASIN:
                setupHasin(player);
                break;

            case JudgeAbstract.REZA:
                setupReza(player);
                break;
        }
    }
    public void setup() {

        for (Player player : players) {
            setupPlayer(player);
        }
    }





    public void movePlayer(Player player, int direction) throws BozorgExceptionBase {
        if (logic.canMove(player, direction, map)) {
            player.move(direction);
            player.setPendingAction(Judge.TIME_INTERVAL / player.getInfo(JudgeAbstract.SPEED));
            int row = player.getInfo(JudgeAbstract.ROW), col = player.getInfo(JudgeAbstract.COL);

            if (map.getCell(row, col).getFan() != null) {
                Fan fan = map.getCell(row, col).getFan();

                for (Player owner : players) {
                    if (owner.getInfo(JudgeAbstract.NAME).equals(fan.getInfo(JudgeAbstract.OWNER)) ) {
                        owner.removeFan(fan);
                        break;
                    }
                }

                map.getCell(row, col).removeFan();


            }
        } else
            throw new BozorgExceptionBase();
    }


    /**
     *
     * @return which player exists in cell (X,Y)
     * it is presumed that there isn't more than one player in each cell
     */
    private Player WhichPlayer(int X, int Y) {
        for (Player player : players)
            if (player.getInfo(JudgeAbstract.ROW) == X && player.getInfo(JudgeAbstract.COL) == Y)
                return player;
        return null;
    }

    /**
     * @return which player exists in the direction of specific player
     */
    private Player WhichPlayer(int direction, Player player) {
        int X = player.getInfo(JudgeAbstract.ROW), Y = player.getInfo(JudgeAbstract.COL);
        switch (direction) {
            case JudgeAbstract.UP:
                return WhichPlayer(X, Y + 1);
            case JudgeAbstract.DOWN:
                return WhichPlayer(X, Y - 1);
            case JudgeAbstract.LEFT:
                return WhichPlayer(X - 1, Y);
            case JudgeAbstract.RIGHT:
                return WhichPlayer(X + 1, Y);
            case JudgeAbstract.NONE:
                return WhichPlayer(X, Y);
        }
        return null;
    }

    public void attack(Player player, int direction) throws BozorgExceptionBase{
        if(logic.canAttack(player, direction, map, players)){
            Player attackedPlayer = WhichPlayer(direction, player);
            attackedPlayer.getAttacked(player.getInfo(JudgeAbstract.POWER));
        }
        else
            throw new BozorgExceptionBase();

    }




    public Fan throwFan(Player player) {
        Fan fan = player.throwFan();
        int row = player.getInfo(JudgeAbstract.ROW), col = player.getInfo(JudgeAbstract.COL);
        map.getCell(row,col).setFan(fan);
        return fan;
    }

    public ArrayList<Fan> getFans(Player player) {
        return player.getFans();
    }

    public ArrayList<Fan> getFans() {
        ArrayList<Fan> fans = new ArrayList<Fan>();
        for (Player player : players) {
            fans.addAll(player.getFans());
        }
        return fans;
    }




    public void getGift(Player player) throws BozorgExceptionBase {
        if(logic.canGetGift(player,map,players)){
            int row = player.getInfo(JudgeAbstract.ROW) , col = player.getInfo(JudgeAbstract.COL);
            int giftType = map.getCellType(row,col);
            player.getGift(giftType);
            map.getCell(row,col).setCellType(JudgeAbstract.NONE_CELL);
        }
        else
            throw new BozorgExceptionBase();

    }


    private boolean canSeeJJ() {
        //todo
        return ((time / 100) % 2) == 0;
    }
    public ArrayList<String> getVision(Player player) {
        ArrayList<String> canSee = player.getVision(map.getWidth(), map.getHeight());
        if (canSeeJJ() && !canSee.contains(map.getJJCell())) {
            canSee.add(map.getJJCell());
        }
        return canSee;
    }

    public ArrayList<Player> getPlayersInVision(Player player) {
        ArrayList<Player> canSee = new ArrayList<Player>();
        for(Player i:players)
            if (logic.canSee(player, map, i.getInfo(JudgeAbstract.ROW), i.getInfo(JudgeAbstract.COL)) && i.getInfo(JudgeAbstract.IS_ALIVE) == 0)
                    canSee.add(i);
        return canSee;
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> alivePlayers = new ArrayList<Player>();
        for (Player player : players) {
            if (player.getInfo(JudgeAbstract.IS_ALIVE) == JudgeAbstract.ALIVE)
                alivePlayers.add(player);
        }
        return alivePlayers;
    }

    public Player stringToPlayer(String s){
        for(Player i: players){
            if(s.equals("SAMAN"))
                if(i.getInfo(JudgeAbstract.NAME) == JudgeAbstract.SAMAN)
                    return i;
            if(s.equals("JAFAR"))
                if(i.getInfo(JudgeAbstract.NAME) == JudgeAbstract.JAFAR)
                    return i;
            if(s.equals("HASIN"))
                if(i.getInfo(JudgeAbstract.NAME) == JudgeAbstract.HASIN)
                    return i;
            else
                if(i.getInfo(JudgeAbstract.NAME) == JudgeAbstract.REZA)
                    return i;

        }
        return null;
    }


    public void next50milies() {
        for (Player i : players) {
            i.next50milis();
            if (i.getDeadTime() == 0)
                setupPlayer(i);
        }
        time += 1;
    }

    public void UpdateInfo(Person person, String infoKey, Integer infoValue) throws BozorgExceptionBase {
        person.updateInfo(infoKey, infoValue);
    }

    //Power Setups
    private void setupSaman(Player player) {
        player.setColor(Judge.Saman_COLOR);
        player.updateInfo(JudgeAbstract.NAME, JudgeAbstract.SAMAN);
        player.updateInfo(JudgeAbstract.SPEED, Judge.Other_Player_Speed);
        player.updateInfo(JudgeAbstract.FANS, Judge.Saman_Fans);
        player.updateInfo(JudgeAbstract.VISION, Judge.Other_Player_Vision);
        player.updateInfo(JudgeAbstract.POWER, Judge.Saman_Power);
    }

    private void setupJafar(Player player) {
        player.setColor(Judge.Jafar_COLOR);
        player.updateInfo(JudgeAbstract.NAME, JudgeAbstract.JAFAR);
        player.updateInfo(JudgeAbstract.SPEED, Judge.Jafar_Speed);
        player.updateInfo(JudgeAbstract.FANS, Judge.Jafar_Fans);
        player.updateInfo(JudgeAbstract.VISION, Judge.Other_Player_Vision);
        player.updateInfo(JudgeAbstract.POWER, Judge.Jafar_Power);
    }

    private void setupReza(Player player) {
        player.setColor(Judge.Reza_COLOR);
        player.updateInfo(JudgeAbstract.NAME, JudgeAbstract.SAMAN);
        player.updateInfo(JudgeAbstract.SPEED, Judge.Other_Player_Speed);
        player.updateInfo(JudgeAbstract.FANS, Judge.Reza_Fans);
        player.updateInfo(JudgeAbstract.VISION, Judge.Reza_Vision);
        player.updateInfo(JudgeAbstract.POWER, Judge.Reza_Power);
    }

    private void setupHasin(Player player) {
        player.setColor(Judge.Hasin_COLOR);
        player.updateInfo(JudgeAbstract.NAME, JudgeAbstract.SAMAN);
        player.updateInfo(JudgeAbstract.SPEED, Judge.Other_Player_Speed);
        player.updateInfo(JudgeAbstract.FANS, Judge.Hasin_Fans);
        player.updateInfo(JudgeAbstract.VISION, Judge.Other_Player_Vision);
        player.updateInfo(JudgeAbstract.POWER, Judge.Hasin_Power);
    }
}
