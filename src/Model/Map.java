package Model;


public class Map {
	private static int width,height;
	private Cell[][] map;

    public Map(int width, int height) {
        map = new Cell[width][height];
        Map.height = height;
        Map.width = width;
    }

    public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public void loadMap(int[][] cellsType,int[][] wallsType){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = new Cell(cellsType[i][j], wallsType[i][j]);
            }
        }
    }

	public int getCellType(int row,int col){
        return map[row][col].getCellType();
	}

	public int getWallType(int row,int col){
		return map[row][col].getWallType();
	}


	public Cell getCell(int row, int col){
		return map[row][col];
	}

}
