package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	private HashMap<String,Integer> information = new HashMap<String,Integer>();
    private ArrayList<Fan> aliveFans = new ArrayList<Fan>();

	private float pendingAction, pendingPrize;
    private int prizeType;

	public void setInfo(String key, Integer value) {
		information.put(key, value);
	}
	public void move(int direction){
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
	public void upadeInfo(String infoKey,Integer infoValue){
		
	}
/*	public int getX(){
		return X;
	}
	public int getY(){
		return Y;
	}*/
}