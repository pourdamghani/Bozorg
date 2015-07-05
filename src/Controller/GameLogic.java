package Controller;

import Judge.JudgeAbstract;
import Model.Cell;
import Model.Fan;
import Model.Map;
import Model.Player;

import java.util.ArrayList;

public class GameLogic {

    /**
     * Checking if there is a wall in specific direction
     * @param player player that wants to move
     * @param map the map player is playing on
     * @return true if there isn't a wall and false otherwise
     */
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

    /**
     *
     * @return returns true if specific player can move in specific direction, false otherwise
     */
    public boolean canMove(Player player, int direction, Map map) {
        if (player.getInfo(JudgeAbstract.IS_ALIVE) != JudgeAbstract.ALIVE)
            return false;
        if (player.getPendingAction() != 0) //Checks if any move is in queue
            return false;

        int x = player.getInfo(JudgeAbstract.ROW);
        int y = player.getInfo(JudgeAbstract.COL);
        switch (direction) { //Checks if move is not going out of the board
            case JudgeAbstract.UP:
                if (x - 1 < 0)
                    return false;
                break;
            case JudgeAbstract.RIGHT:
                if (y + 1 >= map.getHeight())
                    return false;
                break;
            case JudgeAbstract.DOWN:
                if (x + 1 >= map.getWidth())
                    return false;
                break;
            case JudgeAbstract.LEFT:
                if (y - 1 < 0)
                    return false;
                break;
        }

        if (player.getPrizeType() == JudgeAbstract.STONE_CELL)
            return false;
        if (player.getPrizeType() == JudgeAbstract.JUMP_CELL)
            return true;
        if (!isNoWall(player, direction, map))
            return false;
        return true;
    }

    /**
     *
     * @param player player that wants to attack
     * @param direction direction the player wants to attack to
     * @param map map of the game
     * @param players all the players in the map
     * @return
     */
    public boolean canAttack(Player player, int direction, Map map, ArrayList<Player> players) {
        if (player.getPrizeType() == JudgeAbstract.STONE_CELL)
            return false;
        if (player.getInfo(JudgeAbstract.IS_ALIVE) != JudgeAbstract.ALIVE)
            return false;
        if (player.getPendingAction() != 0)//havnt got any action
            return false;
        if (!isNoWall(player, direction, map))
            return false;
        if (!playerExists(direction, player, players, map))
            return false;
        return true;
    }


    /**
     *
     * @param width of the map
     * @param height height of the map
     * @return number of the players existing in cell (X, Y)
     */
    private int numberOfPlayers(int X, int Y, ArrayList<Player> players, int width, int height) {
        int counter = 0;
        if (X >= width || Y >= height || X < 0 || Y < 0) //Checking if X and Y are valid
            return counter;

        for (Player player : players)
            if (player.getInfo(JudgeAbstract.IS_ALIVE) == JudgeAbstract.ALIVE
                    && player.getInfo(JudgeAbstract.ROW) == X
                    && player.getInfo(JudgeAbstract.COL) == Y)
                counter++;
        return counter;
    }

    /**
     *
     * @return true if there's a player at this player's direction, false otherwise
     */
    private boolean playerExists(int direction, Player player, ArrayList<Player> players, Map map) {
        int X = player.getInfo(JudgeAbstract.ROW), Y = player.getInfo(JudgeAbstract.COL);
        int width = map.getWidth();
        int height = map.getHeight();
        switch (direction) {
            case JudgeAbstract.UP:
                if (numberOfPlayers(X, Y + 1, players, width, height) == 0)
                    return false;
                break;
            case JudgeAbstract.DOWN:
                if (numberOfPlayers(X, Y - 1, players, width, height) == 0)
                    return false;
                break;
            case JudgeAbstract.LEFT:
                if (numberOfPlayers(X - 1, Y, players, width, height) == 0)
                    return false;
                break;
            case JudgeAbstract.RIGHT:
                if (numberOfPlayers(X + 1, Y, players, width, height) == 0)
                    return false;
                break;
            case JudgeAbstract.NONE:
                if (numberOfPlayers(X, Y, players, width, height) < 2)
                    return false;
                break;
        }
        return true;
    }


    /**
     *
     * @return returns true if player can see cell (X, Y)
     */
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


    public boolean canGetGift(Player player, Map map, ArrayList<Player> players){
        int X = player.getInfo(JudgeAbstract.ROW), Y = player.getInfo(JudgeAbstract.COL);
        if(map.getCellType(X,Y) < 4)
            return false;
        if(player.getPrizeType() != JudgeAbstract.NONE_CELL && player.getPrizeType() != map.getCellType(X,Y))
            return false;
        if (numberOfPlayers(X, Y, players, map.getWidth(), map.getHeight()) > 1)
            return false;
        return true;
    }
}
