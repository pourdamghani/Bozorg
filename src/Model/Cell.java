package Model;


public class Cell {
    private int cellType;
    private boolean rightWall, leftWall, upWall, downWall;
    private Fan fan;
    private int wallType;

    public Cell(int cellType, int wallType) {
        this.cellType = cellType;
        this.wallType = wallType;
        setWalls(wallType);
    }

    public Fan getFan() {
        return fan;
    }

    public void setFan(Fan fan) {
        this.fan = fan;
    }

    public void removeFan(){
        this.fan = null;
    }

    public int getCellType() {
        return cellType;
    }

    public void setCellType(int cellType) {
        this.cellType = cellType;
    }

    public boolean isRightWall() {
        return rightWall;
    }

    public boolean isLeftWall() {
        return leftWall;
    }

    public boolean isUpWall() {
        return upWall;
    }

    public boolean isDownWall() {
        return downWall;
    }

    private void setWalls(int wallType) {
        upWall = wallType % 2 == 1;

        wallType /= 2;

        rightWall = wallType % 2 == 1;

        wallType /= 2;

        downWall = wallType % 2 == 1;

        wallType /= 2;

        leftWall = wallType % 2 == 1;
    }

    public int getWallType() {
        return wallType;
    }
}
