package Controller;

import Common.exceptions.BozorgExceptionBase;
import Judge.Judge;
import Judge.JudgeAbstract;
import Model.Fan;
import Model.Map;
import Model.Person;
import Model.Player;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.HashMap;


public class ControlUnit {
    private Map map;
    private ArrayList<Player> players;
    private Integer time = 0;
    private GameLogic logic = new GameLogic();

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

    public int getWidth() {
        return map.getWidth();
    }

    public int getHeight() {
        return map.getHeight();
    }


    public int getMapCellType(int row, int col) {
        return map.getCellType(row, col);
    }

    public int getMapCellType(int row, int col, Player player) {
        if (!logic.canSee(player, map, row, col))
            return JudgeAbstract.DARK_CELL;
        if (getMapCellType(row, col) > JudgeAbstract.START_CELL)
            return JudgeAbstract.BONUS_CELL;
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

    /**
     * Setting up players default abilities
     */
    public void setup() {

        for (Player player : players) {
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
    }

    public void movePlayer(Player player, int direction) throws BozorgExceptionBase {
        if (logic.canMove(player, direction, map)) {
            player.move(direction);
            player.setPendingAction(Judge.TIMEINTERVAL / player.getInfo(JudgeAbstract.SPEED));//check it please :D
            int row = player.getInfo(JudgeAbstract.ROW), col = player.getInfo(JudgeAbstract.COL);
            if (map.getCell(row, col).getFan() != null)
                map.getCell(row, col).removeFan();
        } else
            throw new BozorgExceptionBase();
    }

    public void attack(Player player, int direction) {
        //TODO
    }

    public Fan throwfan(Player player) {
        Fan fan = player.throwFan();
        return fan;
    }

    public void getGift(Player player) {
        //TODO
    }

    public ArrayList<String> getVision(Player player) {
        return player.getVision(map.getWidth(), map.getHeight());
    }

    public ArrayList<Player> getPlayersInVision(Player player) {
        return null;
        //TODO
    }

    public ArrayList<Fan> getFans(Player player) {
        return player.getFans();
    }

    public HashMap<String, Integer> getInfo(Person person) {
        return person.getInfo();
    }

//    public HashMap<String, Integer> getInfo(Fan fan) {

//    }

    public void next50milies() {
        //TODO
    }

    public Integer getTime() {
        return time;
    }

    public void UpdateInfo(Person person, String infoKey, Integer infoValue) throws BozorgExceptionBase {
        person.updateInfo(infoKey, infoValue);
    }

    //Power Setups
    private void setupSaman(Player player) {
        player.updateInfo(JudgeAbstract.NAME, JudgeAbstract.SAMAN);
        player.updateInfo(JudgeAbstract.SPEED, Judge.Other_Player_Speed);
        player.updateInfo(JudgeAbstract.HEALTH, Judge.MAX_Health);
        player.updateInfo(JudgeAbstract.IS_ALIVE, JudgeAbstract.ALIVE);
        player.updateInfo(JudgeAbstract.FANS, Judge.Saman_Fans);
        player.updateInfo(JudgeAbstract.VISION, Judge.Other_Player_Vision);
        player.updateInfo(JudgeAbstract.POWER, Judge.Saman_Power);
        player.updateInfo(JudgeAbstract.IS_WINNER, JudgeAbstract.NOT_FINISHED);
    }

    private void setupJafar(Player player) {
        player.updateInfo(JudgeAbstract.NAME, JudgeAbstract.JAFAR);
        player.updateInfo(JudgeAbstract.SPEED, Judge.Jafar_Speed);
        player.updateInfo(JudgeAbstract.HEALTH, Judge.MAX_Health);
        player.updateInfo(JudgeAbstract.IS_ALIVE, JudgeAbstract.ALIVE);
        player.updateInfo(JudgeAbstract.FANS, Judge.Jafar_Fans);
        player.updateInfo(JudgeAbstract.VISION, Judge.Other_Player_Vision);
        player.updateInfo(JudgeAbstract.POWER, Judge.Jafar_Power);
        player.updateInfo(JudgeAbstract.IS_WINNER, JudgeAbstract.NOT_FINISHED);
    }

    private void setupReza(Player player) {
        player.updateInfo(JudgeAbstract.NAME, JudgeAbstract.SAMAN);
        player.updateInfo(JudgeAbstract.SPEED, Judge.Other_Player_Speed);
        player.updateInfo(JudgeAbstract.HEALTH, Judge.MAX_Health);
        player.updateInfo(JudgeAbstract.IS_ALIVE, JudgeAbstract.ALIVE);
        player.updateInfo(JudgeAbstract.FANS, Judge.Reza_Fans);
        player.updateInfo(JudgeAbstract.VISION, Judge.Reza_Vision);
        player.updateInfo(JudgeAbstract.POWER, Judge.Reza_Power);
        player.updateInfo(JudgeAbstract.IS_WINNER, JudgeAbstract.NOT_FINISHED);
    }

    private void setupHasin(Player player) {
        player.updateInfo(JudgeAbstract.NAME, JudgeAbstract.SAMAN);
        player.updateInfo(JudgeAbstract.SPEED, Judge.Other_Player_Speed);
        player.updateInfo(JudgeAbstract.HEALTH, Judge.MAX_Health);
        player.updateInfo(JudgeAbstract.IS_ALIVE, JudgeAbstract.ALIVE);
        player.updateInfo(JudgeAbstract.FANS, Judge.Hasin_Fans);
        player.updateInfo(JudgeAbstract.VISION, Judge.Other_Player_Vision);
        player.updateInfo(JudgeAbstract.POWER, Judge.Hasin_Power);
        player.updateInfo(JudgeAbstract.IS_WINNER, JudgeAbstract.NOT_FINISHED);
    }
}
