package gameEngine.Model.Generator;

import Judge.JudgeAbstract;

import java.util.Random;

/**
*   generate a random map
*
 */
public class Gen {
    public final static int NUM_OF_PLAYERS = 2;
    private static int POSSIBILITY = 4;
    public final static int WIDTH = 10;
    public final static int HEIGHT = 10;

    private int[][] map;
    private int JJRow;
    private int JJCol;
    private int[] players;
    private int[][] walls;

    public Gen() {
        map = new int[WIDTH][HEIGHT];
        players = new int[NUM_OF_PLAYERS];
        WallGen wallGen = new WallGen();
        walls = wallGen.getWalls();
        buildMap();
    }

    public int[] getPlayers() {
        return players;
    }

    public void setPlayers(int player, int index) {
        players[index] = player;
    }

    public int[][] getMap() {
        return map;
    }

    public int[][] getWalls() {
        return walls;
    }

    private void buildMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                map[i][j] = -1;
            }
        }
        Random rand = new Random();

        int col, row;

        //set the jjCel
        JJRow = rand.nextInt(WIDTH);
        JJCol = rand.nextInt(HEIGHT);
        map[JJRow][JJCol] = JudgeAbstract.JJ_CELL;

        //set the Start Cell
        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            row = rand.nextInt(WIDTH);
            col = rand.nextInt(HEIGHT);
            if ( Distance(JJRow, JJCol, row, col) >= 6)
                map[row][col] = JudgeAbstract.START_CELL;
            else i--;
        }

        //Set the kind of cell
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j] == -1) {
                    if (rand.nextInt(POSSIBILITY) == 0)
                        map[i][j] = rand.nextInt(7) + 4;
                    else map[i][j] = JudgeAbstract.NONE_CELL;
                }
            }
        }
    }

    private int Distance(int jjRow, int jjCol, int row, int col) {
        return Math.abs(jjRow - row) + Math.abs(jjCol - col);
    }
}
