package Model;


public class Map {
	private int width,height;
	private Cell[][] map;

    public Map(int width, int height) {
        map = new Cell[width][height];
        this.height = height;
        this.width = width;
    }

    public int getWidth() {
		return width;
	}

	public int getHeight() {
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
