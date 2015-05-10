import Model.Fan;
import Model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IMAN on 5/10/2015.
 */
public class ControlUnit {
    public <ArrayList>loadMap(int[][] cellsType,int[][] wallsType, int[]players){
        return null;
        //TODO
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
