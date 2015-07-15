package AI;

import Common.GameObjectID;
import Common.exceptions.BozorgExceptionBase;
import Common.exceptions.CantGetGiftException;
import Common.exceptions.CantMoveException;
import Judge.Judge;
import Judge.JudgeAbstract;
import gameEngine.GameEngine;
import gameEngine.Model.Generator.Gen;
import gameEngine.Model.Player;
import gamePanel.GamePanel;

import java.util.ArrayList;
import java.util.Random;


public class AI {
    GameEngine engine;
    Player player;
    boolean JJCellFound;
    Integer JJCell, current, width, height;
    ArrayList<Integer> seen = new ArrayList<Integer>();

    public AI(GameEngine engine, Player player) {
        this.player = player;
        this.engine = engine;
        JJCellFound = false;
        JJCell = null;
        width = Gen.WIDTH;
        height = Gen.HEIGHT;
    }

    public void setSeen(ArrayList<String> visited) {
        for (String s : visited) {
            Integer x = convert(s);
            if (!seen.contains(x))
                seen.add(x);
        }
    }

    private int findCol(String s) {
        return Integer.parseInt(s.substring(0, s.indexOf(",")));
    }

    private int findRow(String s) {
        return Integer.parseInt(s.substring(s.indexOf(",") + 1));
    }

    private int findCol(Integer x) {
        return x % width;
    }

    private int findRow(Integer x) {
        return x / width;
    }

    private int convert(String s) {
        return findRow(s) * width + findCol(s);
    }

    private int getCellType(Integer s) {
        Integer col = findCol(s), row = findRow(s);
        return engine.getMapCellType(col, row);

    }

    private boolean findJJCell() {
        if (JJCellFound)
            return true;
        for (Integer x : seen) {
            if (getCellType(x) == JudgeAbstract.JJ_CELL) {
                JJCellFound = true;
                JJCell = x;
                return true;
            }
        }
        return false;
    }

    public void run() {
        setSeen(engine.getVision(player));
        int direction;
        findCurrent();
        if (findJJCell()) {
            direction = findDirection(JJCell, current);
        } else {
            if (getCellType(current) == JudgeAbstract.BONUS_CELL) {
                try {
                    engine.getGift(player);
                } catch (CantGetGiftException exp) {
                    AIError();
                }
            }
            Random random = new Random();
            direction = random.nextInt(4);
        }
        try {
            engine.movePlayer(player, direction);
        } catch (CantMoveException exp) {
            AIError();
        }
    }

    private void AIError() {
    }

    void findCurrent() {
        current = engine.getInfo(player).get(JudgeAbstract.ROW) * engine.getInfo(player).get(JudgeAbstract.COL);
    }

    boolean mark[] = new boolean[50 * 50];
    int dir[] = new int[50 * 50];
    int parent[] = new int[50 * 50];

    private int findDirection(Integer destiny, Integer source) {
        for (boolean b : mark) {
            b = false;
        }
        dfs(source);
        return reverse(destiny, source);
    }

    private int reverse(Integer x, Integer source) {
        if (parent[x] == source)
            return (dir[x] + 2) % 4;
        return reverse(parent[x], source);
    }


    private void dfs(int x) {
        mark[x] = true;
        for (Integer i : seen) {
            if (near(i, x)) {
                parent[i] = x;
                dir[i] = getDirection(i, x);
                dfs(i);
            }
        }

    }

    private boolean near(int x, int y) {
        if (Math.abs(x - y) == 1 || Math.abs(x - y) == width) {
            if ((x % width == 0 && y == current - 1) || (y % width == 0 && x == current - 1))
                return false;
            if ((x % width == width - 1 && y == current + 1) || (x % width == width - 1 && y == current + 1))
                return false;
            return noWall(x, y);
        }
        return false;
    }

    // Up = 0 , right =1 ,down= 2 , left = 3
    private int getDirection(int x, int y) {
        if (x > y) {
            if (x - y == 1)
                return 3;
            if (x - y == width)
                return 2;
        } else {
            if (y - x == 1)
                return 1;
            else
                return 0;
        }
        return -1;
    }

    private boolean noWall(int x, int y) {
        int row = x / width, col = x % width;
        int wall = engine.getMapWallType(row, col);
        switch (getDirection(x, y)) {
            case 0:
                return !((1 & wall) == 1);
            case 1:
                return !((2 & wall) == 2);
            case 2:
                return !((4 & wall) == 4);
            case 3:
                return !((8 & wall) == 8);
        }
        return true;
    }

}
