package Judge;

import java.util.ArrayList;
import java.util.HashMap;

import Common.GameObjectID;
import Common.exceptions.BozorgExceptionBase;

public abstract class JudgeAbstract {
	// cells type
	public static final int NONE_CELL = 0;
	public static final int JJ_CELL = 1;
	public static final int DARK_CELL = 2;
	public static final int START_CELL = 3;
	public static final int SPEEDUP_CELL = 4;
	public static final int RADAR_CELL = 5;
	public static final int STONE_CELL = 6;
	public static final int JUMP_CELL = 7;
	public static final int FAN_CELL = 8;
	public static final int HOSPITAL_CELL = 9;
	public static final int BONUS_CELL = 10;

	public static final int SAMAN = 0;
	public static final int JAFAR = 1;
	public static final int REZA = 2;
	public static final int HASIN = 3;

	public static final int FFFF_WALL = 0; // No Walls Around
	public static final int FFFT_WALL = 1; // up wall
	public static final int FFTF_WALL = 2; // right wall
	public static final int FFTT_WALL = 3; // up and right wall
	public static final int FTFF_WALL = 4; // down wall
	public static final int FTFT_WALL = 5; // up and down wall
	public static final int FTTF_WALL = 6; // right and down wall
	public static final int FTTT_WALL = 7; // up and right and down wall
	public static final int TFFF_WALL = 8; // left wall
	public static final int TFFT_WALL = 9; // up and left wall
	public static final int TFTF_WALL = 10; // right and left wall
	public static final int TFTT_WALL = 11; // up and right and left wall
	public static final int TTFF_WALL = 12; // down and left wall
	public static final int TTFT_WALL = 13; // up and down and left wall
	public static final int TTTF_WALL = 14; // right and down and left wall
	public static final int TTTT_WALL = 15; // up and right and down and left
											// wall
	public static final int XXXX_WALL = 16;
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int NONE = 4;

	public static final int WINS = 0;
	public static final int LOST = 1;
	public static final int NOT_FINISHED = 2;

	public static final int ALIVE = 0;
	public static final int DEAD = 1;

	public static final String ROW = "row";
	public static final String COL = "col";
	public static final String SPEED = "speed";
	public static final String OWNER = "owner";
	public static final String NAME = "name";
	public static final String IS_WINNER = "winner";
	public static final String POWER = "power";
	public static final String VISION = "vision";
	public static final String FANS = "fans";
	public static final String IS_ALIVE = "alive";
	public static final String HEALTH = "hp";

	// map functions
	public abstract ArrayList<GameObjectID> loadMap(int[][] cellsType,
			int[][] wallsType, int[] players);

	public abstract int getMapWidth();

	public abstract int getMapHeight();

	public abstract int getMapCellType(int col, int row);

	public abstract int getMapCellType(int col, int row, GameObjectID player);

	public abstract int getMapWallType(int col, int row);
	
	public abstract int getMapWallType(int col, int row ,GameObjectID player);

	public abstract void setup();

	// logic functions
	public abstract void movePlayer(GameObjectID player, int direction)
			throws BozorgExceptionBase;

	public abstract void attack(GameObjectID attacker, int direction)
			throws BozorgExceptionBase;

	public abstract GameObjectID throwFan(GameObjectID player)
			throws BozorgExceptionBase;

	public abstract void getGift(GameObjectID player)
			throws BozorgExceptionBase;

	// AI functions. these functions will never be used in judge
    public abstract void AIByStudents(GameObjectID player) throws BozorgExceptionBase;

	public void AIByTAs(GameObjectID player) {

    };

	// get info
	public abstract ArrayList<GameObjectID> getEveryThing(); // will never be
																// used in judge
	public abstract ArrayList<String> getVision(GameObjectID player)
			throws BozorgExceptionBase;

	public abstract ArrayList<GameObjectID> getPlayersInVision(
			GameObjectID player);

	public abstract ArrayList<GameObjectID> getFans(GameObjectID player)
			throws BozorgExceptionBase;

	public abstract HashMap<String, Integer> getInfo(GameObjectID id)
			throws BozorgExceptionBase;

	// Controller functions
	public abstract void next50milis();

	public abstract void startTimer();// will never be used in judge

	public abstract void pauseTimer();

	public abstract float getTime();

	// Judge cheat functions
	public abstract void updateInfo(GameObjectID id, String infoKey,
			Integer infoValue) throws BozorgExceptionBase;

}
