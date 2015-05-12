import Judge.JudgeAbstract;
import Model.Cell;
import Model.Fan;
import Model.Map;
import Model.Player;

import java.util.ArrayList;

public class GameLogic {
    //if player cant move or attack in spacial direction beacuse of wall return false else return true
    private boolean isNoWall(Player player, int direction, Map map) {
        Cell cell = map.getCell(player.getInfo(JudgeAbstract.ROW), player.getInfo(JudgeAbstract.COL));
        switch (direction) {
            case JudgeAbstract.UP:
                if (cell.isUpWall())
                    return false;
                break;
            case JudgeAbstract.DOWN:
                if (cell.isDownWall())
                    return false;
                break;
            case JudgeAbstract.RIGHT:
                if (cell.isRightWall())
                    return false;
                break;

            case JudgeAbstract.LEFT:
                if (cell.isLeftWall())
                    return false;
                break;
        }
        return true;
    }

    //number of player in (x,y) cell
    private int numberofPlayer(int X, int Y, ArrayList<Player> players, int width, int height) {
        int counter = 0;
        if (X >= width || Y >= height || X < 0 || Y < 0)
            return counter;
        for (Player player : players)
            if (player.getInfo(JudgeAbstract.ROW) == X && player.getInfo(JudgeAbstract.COL) == Y)
                counter++;
        return counter;
    }

    //if there are on player to attack return true else return false
    private boolean playerexist(int direction,Player player, ArrayList<Player> players, Map map) {
        int X = player.getInfo(JudgeAbstract.ROW), Y = player.getInfo(JudgeAbstract.COL);
        int width = map.getWidth();
        int height = map.getHeight();
        switch (direction) {
            case JudgeAbstract.UP:
                if (numberofPlayer(X, Y + 1, players, width, height) == 0)
                    return false;
                break;
            case JudgeAbstract.DOWN:
                if (numberofPlayer(X, Y - 1, players, width, height) == 0)
                    return false;
                break;
            case JudgeAbstract.LEFT:
                if (numberofPlayer(X - 1, Y, players, width, height) == 0)
                    return false;
                break;
            case JudgeAbstract.RIGHT:
                if (numberofPlayer(X + 1, Y, players, width, height) == 0)
                    return false;
                break;
            case JudgeAbstract.NONE:
                if (numberofPlayer(X, Y, players, width, height) <= 1)
                    return false;
                break;
        }
        return true;
    }

    public boolean canMove(Player player, int direction, Map map) {
        if (player.getPrizeType() == JudgeAbstract.STONE_CELL)
            return false;
        if (player.getPrizeType() == JudgeAbstract.JUMP_CELL)
            return true;
        if (player.getInfo(JudgeAbstract.IS_ALIVE) != JudgeAbstract.ALIVE)
            return false;
        if (player.getPendingAction() != 0)//havnt got any action
            return false;
        if (!isNoWall(player, direction, map))
            return false;
        return true;
    }

    //check shavad :D
    public boolean canAttack(Player player, int direction, Map map, ArrayList<Player> players) {
        if (player.getPrizeType() == JudgeAbstract.STONE_CELL)
            return false;
        if (player.getInfo(JudgeAbstract.IS_ALIVE) != JudgeAbstract.ALIVE)
            return false;
        if (player.getPendingAction() != 0)//havnt got any action
            return false;
        if (!isNoWall(player, direction, map))
            return false;
        if (!playerexist(direction, player, players, map))
            return false;
        return true;
    }

    public boolean canSee(Player player,Map map,int X, int Y) {
        Fan fan = map.getCell(X,Y).getFan();
        if(fan != null)
            if(player.getFans().contains(fan))
                return true;
        int playerX = player.getInfo(JudgeAbstract.ROW), playerY = player.getInfo(JudgeAbstract.COL);
        if (X - playerX < player.getInfo(JudgeAbstract.VISION) && X - playerX > -1 * player.getInfo(JudgeAbstract.VISION))
            if (Y - playerY < player.getInfo(JudgeAbstract.VISION) && Y - playerY > -1 * player.getInfo(JudgeAbstract.VISION))
                return true;
        return false;
    }
}
