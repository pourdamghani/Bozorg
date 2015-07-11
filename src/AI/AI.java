package AI;

import Common.GameObjectID;
import Common.exceptions.BozorgExceptionBase;
import Judge.Judge;
import Judge.JudgeAbstract;
import gameEngine.Model.Generator.Gen;

import java.util.ArrayList;
import java.util.Random;


public class AI {
    Judge judge;
    GameObjectID objectID;
    boolean JJCellFound;
    Integer JJCell, current, width, height;
    ArrayList<Integer> seen = new ArrayList<Integer>();

    public AI(Judge judge, GameObjectID objectID) {
        this.objectID = objectID;
        this.judge = judge;
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

    private int convert(String s) {
        return findRow(s) * width + findCol(s);
    }

    private int getCellType(Integer s) {
        Integer col = s % width, row = s / width;
        return judge.getMapCellType(col, row);

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

    public void move() {
        try {
            setSeen(judge.getVision(objectID));
        } catch (BozorgExceptionBase exceptionBase) {
            exceptionBase.printStackTrace();
        }
        int direction;
        findCurrent();
        if (findJJCell()) {
            direction = findDirection(JJCell, current);
        } else {
            Random random = new Random();
            direction = random.nextInt(4);
        }
        try {
            judge.movePlayer(objectID, direction);
        } catch (BozorgExceptionBase exceptionBase) {
            exceptionBase.printStackTrace();
        }
    }

    void findCurrent() {
        try {
            current = judge.getInfo(objectID).get(JudgeAbstract.ROW) * judge.getInfo(objectID).get(JudgeAbstract.COL);
        } catch (BozorgExceptionBase exceptionBase) {
            exceptionBase.printStackTrace();
        }
    }

    boolean mark[] = new boolean[50 * 50];
    int dir[] = new int[50 * 50];

    private int findDirection(Integer destiny, Integer source) {
        for (boolean b : mark) {
            b = false;
        }
        dfs(source);
        return reverse(dir[destiny]);
    }

    private int reverse(int x) {
        return (x + 2) % 4;
    }


    private void dfs(int x) {
        mark[x] = true;
        for (Integer i : seen) {
            if (near(i, x)) {
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
        int wall = judge.getMapWallType(row, col);
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
