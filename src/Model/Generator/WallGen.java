package Model.Generator;

import Controller.GameEngine;
import Judge.Judge;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Arash on 15/07/10.
 */
public class WallGen {
    GameEngine gameEngine = new GameEngine();
    int width, height;
    ArrayList<Integer> directions = new ArrayList<Integer>();
    ArrayList<Integer> available = new ArrayList<Integer>();
    int walls[] = new int[Judge.MAXSIZE * Judge.MAXSIZE];
    public WallGen() {
        width = Gen.WIDTH;
        height = Gen.HEIGHT;
        genDir();
        genAva();
        dfs(0);
    }


    boolean visited[] = new boolean[Judge.MAXSIZE * Judge.MAXSIZE];

    private void dfs(Integer currentCell) {
        available.remove(currentCell);
        visited[currentCell] = true;
        Random random = new Random();
        for (int i = random.nextInt(4), j = 0; j < 4; j++) {
            Integer direction = directions.get(i);
            Integer next = currentCell + direction;
            if (valid(next, currentCell) && !visited[next]) {
                removeWall(currentCell, i);
                removeWall(next, reverse(i));
                dfs(next);
            }
            i = (i + 1) % 4;
        }
    }

    private boolean valid(int next, int current) {
        if (!(next >= 0) || !(next < height * width))
            return false;
        if (current % width == 0 && next == current - 1)
            return false;
        if (current % width == width - 1 && next == current + 1)
            return false;
        return true;
    }

    private void genAva() {
        for (int i = 0; i < width * height; i++) {
            available.add(i);
            walls[i] = 15;
        }
    }

    private void genDir() {
        directions.add(-width);
        directions.add(1);
        directions.add(width);
        directions.add(-1);
    }

    private void removeWall(int current, int direction) {
        walls[current] = walls[current] & (15 - (int) Math.pow(2, direction));
    }

    private int reverse(int direction) {
        return (2 + direction) % 4;
    }

    public int[][] getWalls() {
        int[][] ret = new int[width][height];
        for (int i = 0; i < width * height; i++) {
            ret[i / width][i % width] = walls[i];
        }
        return ret;
    }
}
