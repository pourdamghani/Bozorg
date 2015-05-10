import Judge.JudgeAbstract;
import Model.Cell;
import Model.Map;
import Model.Player;

import java.util.ArrayList;

public class GameLogic {
	//if player cant move or attack in spacial direction beacuse of wall return false else return true
	private boolean isNoWall(Player player,int direction, Map map){
		Cell cell = map.getCell(player.getInfo(JudgeAbstract.ROW),player.getInfo(JudgeAbstract.COL));
		switch (direction){
			case JudgeAbstract.UP:
				if(cell.isUpWall())
					return false;
				break;
			case JudgeAbstract.DOWN:
				if(cell.isDownWall())
					return false;
				break;
			case JudgeAbstract.RIGHT:
				if(cell.isRightWall())
					return false;
				break;

			case JudgeAbstract.LEFT:
				if(cell.isLeftWall())
					return false;
				break;
		}
		return true;
	}
	private int numberofPlayer(int X, int Y, ArrayList<Player> players){
		int count = 0;
		for (Player player : players)
			if(player.getInfo(JudgeAbstract.ROW) == X && player.getInfo(JudgeAbstract.COL) == Y)
				count++;
	}
	public boolean canMove(Player player, int direction, Map map){
		if(player.getInfo(JudgeAbstract.IS_ALIVE) != JudgeAbstract.ALIVE)
			return false;
		if(player.getPendingAction() != 0)//havnt got any action
			return false;
		if(!isNoWall(player, direction, map))
			return false;
		return true;
	}
	//check shavad :D
	public boolean canAttack (Player player, int direction, Map map, ArrayList<Player> players){
		if(player.getInfo(JudgeAbstract.IS_ALIVE) != JudgeAbstract.ALIVE)
			return false;
		if(player.getPendingAction() != 0)//havnt got any action
			return false;
		if(!isNoWall(player, direction, map))
			return false;
		int X = player.getInfo(JudgeAbstract.ROW), Y = player.getInfo(JudgeAbstract.COL);
		switch (direction){
			case JudgeAbstract.UP:
				if(numberofPlayer(X, Y + 1, players) == 0)
					return false;
		}
		return true;
	}
}
