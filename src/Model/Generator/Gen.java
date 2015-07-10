package Model.Generator;

import Judge.JudgeAbstract;
import sun.java2d.jules.JulesPathBuf;

import java.util.Random;

/**
 * Created by Arash on 15/07/10.
 */
public class Gen {
    public static int NUM_OF_PLAYERS = 2;
    private static int POSSIBILITY = 4;
    public static int width = 10;
    public static int height = 10;

    private int[][] map;
    private int JJRow;
    private int JJCol;
    private int[] players;

    public Gen() {
        map = new int[width][height];
        players = new int[NUM_OF_PLAYERS];
    }

    public int[] getPlayers() {
        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            players[i] = -1;
        }

        Random rand = new Random();
        players[0] = rand.nextInt(4);

        for (int i = 1; i < NUM_OF_PLAYERS; i++) {
            players[i] = rand.nextInt(4);

            for (int j = i - 1; j >= 0; j--) {
                if (players[i] == players[j]) {
                    i--;
                    break;
                }
            }
        }

        return players;
    }

    public int[][] getMap() {
        return map;
    }

    private void buildMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = -1;
            }
        }
        Random rand = new Random();

        int col, row;

        JJRow = rand.nextInt(width);
        JJCol = rand.nextInt(height);
        map[JJRow][JJCol] = JudgeAbstract.JJ_CELL;

        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            row = rand.nextInt(width);
            col = rand.nextInt(height);
            if ( Distance(JJRow, JJCol, row, col) >= 6)
                map[row][col] = JudgeAbstract.START_CELL;
            else i--;
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] == -1) {
                    if (rand.nextInt(POSSIBILITY) == 0)
                        map[i][j] = rand.nextInt(7) + 4;
                    else map[i][j] = JudgeAbstract.NONE_CELL;
                }
            }
        }
    }

    private int Distance(int jjRow, int jjCol, int row, int col) {
        int dis = Math.abs(jjRow - row) + Math.abs(jjCol - col);

        return dis;
    }
}
