import Common.exceptions.BozorgExceptionBase;
import Judge.JudgeAbstract;
import Model.Fan;
import Model.Map;
import Model.Player;

import java.util.ArrayList;
import java.util.HashMap;


public class ControlUnit {
    private Map map;
    private ArrayList<Player> players;
    private int time = 0;
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
        return 0;
        //TODO
    }

    public int getMapWallType(int row, int col) {
        return map.getWallType(row, col);
    }

    public int getMapWallType(int row, int col, Player player) {
        return 0;
        //TODO
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

    public void movePlayer(Player player, int direction) throws BozorgExceptionBase{
        if(logic.canMove(player, direction, map)){
            player.move(direction);
            player.setPendingAction(1000/50/ player.getInfo(JudgeAbstract.SPEED));//check it please :D
        }
        else
            throw BozorgExceptionBase;
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
        //TODO
        return null;
    }

    public ArrayList<Player> getPlayersInVision(Player player) {
        return null;
        //TODO
    }

    public ArrayList<Fan> getFans(Player player) {
        return null;
        //TODO
    }

    public HashMap<String, Integer> getInfo(Player player) {
        return null;
        //TODO
    }

    public HashMap<String, Integer> getInfo(Fan fan) {
        return null;
        //TODO
    }

    public void next50milies() {
        //TODO
    }

    public float getTime() {
        return (float) time / 1000;
    }

    //Power Setups
    private void setupSaman(Player player) {
        player.updateInfo(JudgeAbstract.NAME, JudgeAbstract.SAMAN);
        player.updateInfo(JudgeAbstract.SPEED, 2);
        player.updateInfo(JudgeAbstract.HEALTH, 100);
        player.updateInfo(JudgeAbstract.IS_ALIVE, JudgeAbstract.ALIVE);
        player.updateInfo(JudgeAbstract.FANS, 100);
        player.updateInfo(JudgeAbstract.VISION, 3);
        player.updateInfo(JudgeAbstract.POWER, 5);
        player.updateInfo(JudgeAbstract.IS_WINNER, 2);
    }

    private void setupJafar(Player player) {
        player.updateInfo(JudgeAbstract.NAME, JudgeAbstract.JAFAR);
        player.updateInfo(JudgeAbstract.SPEED, 5);
        player.updateInfo(JudgeAbstract.HEALTH, 100);
        player.updateInfo(JudgeAbstract.IS_ALIVE, JudgeAbstract.ALIVE);
        player.updateInfo(JudgeAbstract.FANS, 5);
        player.updateInfo(JudgeAbstract.VISION, 3);
        player.updateInfo(JudgeAbstract.POWER, 1);
        player.updateInfo(JudgeAbstract.IS_WINNER, 2);
    }

    private void setupReza(Player player) {
        player.updateInfo(JudgeAbstract.NAME, JudgeAbstract.SAMAN);
        player.updateInfo(JudgeAbstract.SPEED, 2);
        player.updateInfo(JudgeAbstract.HEALTH, 100);
        player.updateInfo(JudgeAbstract.IS_ALIVE, JudgeAbstract.ALIVE);
        player.updateInfo(JudgeAbstract.FANS, 0);
        player.updateInfo(JudgeAbstract.VISION, 6);
        player.updateInfo(JudgeAbstract.POWER, 4);
        player.updateInfo(JudgeAbstract.IS_WINNER, 2);
    }

    private void setupHasin(Player player) {
        player.updateInfo(JudgeAbstract.NAME, JudgeAbstract.SAMAN);
        player.updateInfo(JudgeAbstract.SPEED, 2);
        player.updateInfo(JudgeAbstract.HEALTH, 100);
        player.updateInfo(JudgeAbstract.IS_ALIVE, JudgeAbstract.ALIVE);
        player.updateInfo(JudgeAbstract.FANS, 10);
        player.updateInfo(JudgeAbstract.VISION, 3);
        player.updateInfo(JudgeAbstract.POWER, 5);
        player.updateInfo(JudgeAbstract.IS_WINNER, 2);
    }
}
