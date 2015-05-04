package Model;

import java.util.ArrayList;

public class GameLogic {
	private int time;
	private Map map;
	public GameLogic(){
		time = 0;
		map = new Map();
	}
	public boolean canMove(Player player,int direct){
		int X = player.getX();
		int Y = player.getY();
		int Wall = map.getMapWallType(X, Y);
		//change the wall to binary then chek with direct
		return true;
	}
	public boolean canAttack(Player player, int direct){
		int X = player.getX();
		int Y = player.getY();
		switch(direct){
		case 0:
			Y++;
			break;
		case 1:
			X++;
			break;
		case 2:
			Y--;
			break;
		case 3:
			X--;
			break;
		}
		ArrayList<Player> players = map.getPlayer(X, Y);
		if(players.size() > 1 || (players.size() == 1 && !players.contains(player)))
			return true;
		return false;
	}
	public void next50milies(){
		time += 50;
	}
	public int getTime(){
		return time;
	}
//	public void setup(){
	//	
//	}
	public void startTimer(){
		
	}
	public void pauseTimer(){
		
	}
	public Map getMap(){
		return map;
	}
	
}
