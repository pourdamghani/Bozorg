import Judge.JudgeAbstract;
import Model.Fan;
import Model.Map;
import Model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IMAN on 5/10/2015.
 */
public class ControlUnit {
    private Map map;
    private ArrayList<Player> players;

    public ArrayList<Player> loadMap(int[][] cellsType,int[][] wallsType, int[] players){

        int width = cellsType.length;
        int height = cellsType[0].length;

        //Initializing map
        map = new Map(width, height);
        map.loadMap(cellsType, wallsType);

        //Placing players in the map
        int iter = 0;
        this.players = new ArrayList<Player>();
        for (int i = 0; i <width; i++) {
            for (int j = 0; j < height; j++) {
                if (map.getCellType(i, j) == JudgeAbstract.START_CELL) {
                    this.players.add(new Player() );
                    Player player = this.players.get(iter);
                    player.updateInfo(JudgeAbstract.ROW, i); //Setting Row
                    player.updateInfo(JudgeAbstract.COL, i); //Setting Col
                    iter++;
                }
            }
        }

        return this.players;
    }
    public int getMapWidth(){
        return 0;
        //TODO
    }
    public int getMapHeight(){
        return 0;
        //TODO
    }
    public int getMapCellType(int row, int col){
        return 0;
        //TODO
    }
    public int getMapCellType(int row, int col, Player player){
        return 0;
        //TODO
    }
    public int getMapWallType(int row, int col){
        return 0;
        //TODO
    }
    public int getMapWallType(int row, int col,Player player){
        return 0;
        //TODO
    }
    public void setup(){

    }
    public void movePlayer(Player player, int direction){
        //TODO
    }
    public void attack(Player player, int direction){
        //TODO
    }
    public Fan throwfan(Player player){
        return null;
        //TODO
    }
    public void getGift(Player player){
        //TODO
    }
    public ArrayList<String> getVision(Player player){
        return null;
        //TODO
    }
    public ArrayList<Player> getPlayersInVision (Player player){
        return null;
        //TODO
    }
    public ArrayList<Fan> getFans(Player player){
        return null;
        //TODO
    }
    public HashMap<String, Integer> getInfo(Player player){
        return null;
        //TODO
    }
    public HashMap<String, Integer> getInfo(Fan fan){
        return null;
        //TODO
    }
    public void next50milies(){
        //TODO
    }
    public float getTime(){
        return 0;
        //TODO
    }
}
