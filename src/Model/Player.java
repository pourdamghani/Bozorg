package Model;

import Judge.JudgeAbstract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Player {
	private HashMap<String,Integer> information = new HashMap<String,Integer>();
    private ArrayList<Fan> aliveFans = new ArrayList<Fan>();

	private float pendingAction, pendingPrize;
    private int prizeType;

	public void setInfo(String key, Integer value) {
		information.put(key, value);
	}
	public void move(int direction){
        Integer row = information.get(JudgeAbstract.ROW);
        Integer col = information.get(JudgeAbstract.COL);
        switch (direction){
            case JudgeAbstract.UP:
                row--;
                break;
            case JudgeAbstract.DOWN:
                row++;
                break;
            case JudgeAbstract.LEFT:
                col--;
                break;
            case JudgeAbstract.RIGHT:
                col++;
                break;
        }
        information.put(JudgeAbstract.ROW,row);
        information.put(JudgeAbstract.COL,col);

	}
	public void attack(int direction){
		
	}
	public Fan throwFan() {
		return null;
	}

    public void removeFan(Fan fan) {
        aliveFans.remove(fan);
    }

	public void getGift(){
		
	}

    public float getPendingAction() {
        return pendingAction;
    }

    public void setPendingAction(float pendingAction) {
        this.pendingAction = pendingAction;
    }

    public float getPendingPrize() {
        return pendingPrize;
    }

    public void setPendingPrize(float pendingPrize) {
        this.pendingPrize = pendingPrize;
    }

    public int getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(int prizeType) {
        this.prizeType = prizeType;
    }



    public ArrayList<String> getVision(){
		return null;
	}
	public ArrayList<Player> getPlayersInVision(){
		return null;
	}
	public ArrayList<Fan> getFans(){
		return null;
	}
	public HashMap<String,Integer> getInfo(){
		return null;
	}
	public void updateInfo(String infoKey,Integer infoValue){
		
	}
    public Integer getInfo(String infoKey){
        return information.get(infoKey);
    }
/*	public int getX(){
		return X;
	}
	public int getY(){
		return Y;
	}*/
}
