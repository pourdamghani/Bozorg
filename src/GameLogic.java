import Judge.JudgeAbstract;
import Model.Cell;
import Model.Map;
import Model.Player;

import java.util.ArrayList;

public class GameLogic {
	public boolean canMove(Player player, int direction, Map map){

		Cell cell = map.getCell(player.getInfo(JudgeAbstract.ROW),player.getInfo(JudgeAbstract.COL));
		if()
			return true;
	}
	public boolean canAttack (Player player, int direction){
		//TODO
		return true;
	}
}
