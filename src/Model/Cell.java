package Model;

import java.util.ArrayList;

public class Cell {
	private int cellType;
	private boolean rightWall, leftWall, upWall, downWall;
//	private ArrayList<Player> players;
	private Fan fan;
    private int wallType;

    public Cell(int cellType, int wallType) {
        this.cellType = cellType;
        this.wallType = wallType;
        setWalls(wallType);
    }

    public Fan getFan(){
		return fan;
	}
/*	public ArrayList<Player> getPlayer(){
		return players;
	}*/

	public void setFan(Fan fan){
        this.fan = fan;
	}
/*	public void addPlayer(Player player){
        players.add(player);
	}*/
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
        if (wallType % 2 == 1)
            upWall = true;
        else upWall = false;

        wallType /= 2;

        if (wallType % 2 == 1)
            rightWall = true;
        else rightWall = false;

        wallType /= 2;

        if (wallType % 2 == 1)
            downWall = true;
        else downWall = false;

        wallType /= 2;

        if (wallType % 2 == 1)
            leftWall = true;
        else leftWall = false;
    }

    public int getWallType() {
        return wallType;
    }
}
