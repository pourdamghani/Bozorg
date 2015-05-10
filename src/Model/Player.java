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
	public void getAttack(Integer power){
		Integer newHealth = information.get(JudgeAbstract.HEALTH)-power;
        information.put(JudgeAbstract.HEALTH,newHealth);
        if(newHealth < 0){
            information.put(JudgeAbstract.IS_ALIVE, JudgeAbstract.DEAD);
            information.put(JudgeAbstract.IS_WINNER,JudgeAbstract.LOST);
        }
	}
	public Fan throwFan() {
		Fan fan = new Fan(information.get(JudgeAbstract.ROW),information.get(JudgeAbstract.COL),information.get(JudgeAbstract.NAME),JudgeAbstract.ALIVE);
        aliveFans.add(fan);
        information.put(JudgeAbstract.FANS, information.get(JudgeAbstract.FANS));
        return fan;
	}

    public void removeFan(Fan fan) {
        aliveFans.remove(fan);
    }

	public void getGift(Integer giftType){
        switch (giftType){
            //todo
        }
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
	public ArrayList<Fan> getFans(){
		return aliveFans;
	}
	public HashMap<String,Integer> getInfo(){
		return information;
	}
	public void updateInfo(String infoKey,Integer infoValue){
		information.put(infoKey,infoValue);
	}
    public Integer getInfo(String infoKey){
        return information.get(infoKey);
    }
}
