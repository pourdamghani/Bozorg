package Judge;

import Common.GameObjectID;
import Common.exceptions.BozorgExceptionBase;
import Common.exceptions.GameEndException;
import Controller.GameEngine;
import Model.Fan;
import Model.Person;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Judge extends JudgeAbstract {
    private HashMap<GameObjectID, Person> IDtoPerson = new HashMap<GameObjectID, Person>();
    private GameEngine cu = new GameEngine();

    private boolean run;

    public static int SPEEDUP_TIME = 10;
    public static int RADAR_TIME = 10;
    public static int STONE_TIME = 6;
    public static int JUMP_TIME = 8;
    public static int HEALTH_INCREASE_VALUE = 20;
    public static int FAN_INCREASE_VALUE = 3;
    public static int MAX_Health = 100;
    public static int TIME_INTERVAL = 20;
    public static int Reza_Vision = 6;
    public static int Other_Player_Vision = 3;
    public static int Jafar_Speed = 5;
    public static int Other_Player_Speed = 2;
    public static int Hasin_Power = 5;
    public static int Saman_Power = 5;
    public static int Jafar_Power = 1;
    public static int Reza_Power = 4;
    public static int Hasin_Fans = 10;
    public static int Saman_Fans = 100;
    public static int Jafar_Fans = 5;
    public static int Reza_Fans = 0;
    public static int MAXSIZE = 500;
    public static final Color Reza_COLOR = Color.red;
    public static final Color Hasin_COLOR = Color.green;
    public static final Color Jafar_COLOR = Color.blue;
    public static final Color Saman_COLOR = Color.yellow;
    public static final Image REZA_IMAGE = new ImageIcon("src/Image/Reza.png").getImage();
    public static final Image HASIN_IMAGE = new ImageIcon("src/Image/Hasin.png").getImage();
    public static final Image JAFAR_IMAGE = new ImageIcon("src/Image/jafar.png").getImage();
    public static final Image SAMAN_IMAGE = new ImageIcon("src/Image/Saman.png").getImage();
    public static final Image REZA_FAN_IMAGE = new ImageIcon("src/Image/RezaFan.png").getImage();
    public static final Image HASIN_FAN_IMAGE = new ImageIcon("src/Image/HasinFan.png").getImage();
    public static final Image JAFAR_FAN_IMAGE = new ImageIcon("src/Image/JafarFan.png").getImage();
    public static final Image SAMAN_FAN_IMAGE = new ImageIcon("src/Image/SamanFan.png").getImage();


    public ArrayList<GameObjectID> loadMap(int[][] cellsType, int[][] wallsType, int[] players) {
        ArrayList<Player> player = cu.loadMap(cellsType, wallsType, players);
        ArrayList<GameObjectID> ids = new ArrayList<GameObjectID>();
        for (Player i : player) {
            GameObjectID ID = GameObjectID.create(Player.class);
            IDtoPerson.put(ID, i);
            ids.add(ID);
        }
        return ids;
    }
    public int getMapWidth(){
        return cu.getWidth();
    }
    public int getMapHeight(){
        return cu.getHeight();
    }
    public int getMapCellType(int col, int row){
        return cu.getMapCellType(row, col);
    }
    public int getMapCellType(int col, int row, GameObjectID player){
        return cu.getMapCellType(row, col, (Player) IDtoPerson.get(player));
    }
    public int getMapWallType(int col, int row){
        return cu.getMapWallType(row, col);
    }
    public int getMapWallType(int col, int row, GameObjectID player){
        return cu.getMapWallType(row, col, (Player) IDtoPerson.get(player));
    }
    public void setup(){
        cu.setup();
    }
    public void movePlayer(GameObjectID player, int direction) throws BozorgExceptionBase {
        cu.movePlayer((Player) IDtoPerson.get(player), direction);
    }
    public void attack(GameObjectID attacker, int direction) throws BozorgExceptionBase{
        cu.attack((Player) IDtoPerson.get(attacker), direction);
    }
    public GameObjectID throwFan(GameObjectID player) throws BozorgExceptionBase{
        Fan fan = cu.throwFan((Player) IDtoPerson.get(player));
        GameObjectID ID = GameObjectID.create(Fan.class);
        IDtoPerson.put(ID, fan);
        return ID;
    }
    public void getGift(GameObjectID player) throws BozorgExceptionBase{
        cu.getGift((Player) IDtoPerson.get(player));
    }

    public void AIByStudents(GameObjectID player) throws BozorgExceptionBase {
        Random random = new Random();
        movePlayer(player, random.nextInt(4));
    }
    public void AIByTAs(GameObjectID player){
        //TODO by TA
    }
    public ArrayList<GameObjectID> getEveryThing() {
        ArrayList<GameObjectID> IDs = new ArrayList<GameObjectID>();
        for (HashMap.Entry<GameObjectID, Person> entry : IDtoPerson.entrySet()) {
            IDs.add(entry.getKey());
        }

        return IDs;
    }

    public ArrayList<String> getVision(GameObjectID player) throws BozorgExceptionBase{
        return cu.getVision((Player) IDtoPerson.get(player));
    }
    public ArrayList<GameObjectID> getPlayersInVision ( GameObjectID player){
        ArrayList<Player> players = cu.getPlayersInVision((Player) IDtoPerson.get(player));
        ArrayList<GameObjectID> IDs = new ArrayList<GameObjectID>();
        for(Player i: players)
            IDs.add(personToID(i));
        return IDs;
    }
    public ArrayList<GameObjectID> getFans(GameObjectID player) throws BozorgExceptionBase{
        ArrayList<Fan> fans = cu.getFans((Player) IDtoPerson.get(player));
        ArrayList<GameObjectID> IDs = new ArrayList<GameObjectID>();
        for(Fan i: fans)
            IDs.add(personToID(i));
        return IDs;
    }
    public HashMap<String, Integer> getInfo(GameObjectID id) throws BozorgExceptionBase{
        return cu.getInfo(IDtoPerson.get(id));
    }
    public void next50milis(){
        try {
            cu.next50milies();
        }
        catch (GameEndException E){

        }
    }

    public void startTimer(){
        run = true;
        while (run)
            next50milis();
    }
    public  void pauseTimer(){
        run = false;
    }
    public float getTime(){
        return cu.getTime() / (float)TIME_INTERVAL;
    }
    public void updateInfo(GameObjectID id, String infoKey,Integer infoValue) throws BozorgExceptionBase{
        cu.UpdateInfo(IDtoPerson.get(id), infoKey, infoValue);
    }



    private GameObjectID personToID(Person person) {
        for (HashMap.Entry<GameObjectID, Person> entry : IDtoPerson.entrySet()) {
            if (entry.getValue() == person)
                return entry.getKey();
        }

        return null;
    }
}
