package Model;

import Judge.Judge;
import Judge.JudgeAbstract;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Player extends Person{
    private ArrayList<Fan> aliveFans = new ArrayList<Fan>();

    private Integer pendingAction, pendingPrize;
    private Integer prizeType;

    //check pendingAction

    public void setInfo(String key, Integer value) {
        information.put(key, value);
    }
    public void move(int direction) {
        Integer row = information.get(JudgeAbstract.ROW);
        Integer col = information.get(JudgeAbstract.COL);
        switch (direction) {
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
        information.put(JudgeAbstract.ROW, row);
        information.put(JudgeAbstract.COL, col);

    }

    public void getAttacked(Integer power) {
        Integer newHealth = information.get(JudgeAbstract.HEALTH) - power;
        information.put(JudgeAbstract.HEALTH, newHealth);
        if (newHealth < 0) {
            information.put(JudgeAbstract.IS_ALIVE, JudgeAbstract.DEAD);
            information.put(JudgeAbstract.IS_WINNER, JudgeAbstract.LOST);
        }
    }

    public Fan throwFan() {
        Fan fan = new Fan(information.get(JudgeAbstract.ROW), information.get(JudgeAbstract.COL), information.get(JudgeAbstract.NAME), JudgeAbstract.ALIVE);
        aliveFans.add(fan);
        information.put(JudgeAbstract.FANS, information.get(JudgeAbstract.FANS));
        return fan;
    }

    public void removeFan(Fan fan) {
        aliveFans.remove(fan);
    }

    public void getGift(Integer giftType) {
        prizeType = giftType;
        switch (giftType) {
            case JudgeAbstract.SPEEDUP_CELL:
                information.put(JudgeAbstract.SPEED, information.get(JudgeAbstract.SPEED) * 2);
                pendingPrize = Judge.SPEEDUP_TIME * Judge.TIMEINTERVAL;
                break;
            case JudgeAbstract.RADAR_CELL:
                pendingPrize = Judge.RADAR_TIME * Judge.TIMEINTERVAL;
                break;
            case JudgeAbstract.STONE_CELL:
                pendingPrize = Judge.STONE_TIME * Judge.TIMEINTERVAL;
                break;
            case JudgeAbstract.JUMP_CELL:
                pendingPrize = Judge.JUMP_TIME * Judge.TIMEINTERVAL;
                break;
            case JudgeAbstract.HOSPITAL_CELL:
                information.put(JudgeAbstract.HEALTH, Math.min(Judge.MAX_Health, information.get(JudgeAbstract.HEALTH) + Judge.HEALTH_INCREASE_VALUE));
                break;
            case JudgeAbstract.FAN_CELL:
                information.put(JudgeAbstract.FANS, information.get(JudgeAbstract.FANS) + Judge.FAN_INCREASE_VALUE);
                break;
        }
    }

    public ArrayList<String> getVision(Integer width, Integer height) {
        ArrayList<String> canSee = new ArrayList<String>();
        if (prizeType != JudgeAbstract.RADAR_CELL) {
            Integer vision = information.get(JudgeAbstract.VISION);
            Integer x = information.get(JudgeAbstract.ROW);
            Integer y = information.get(JudgeAbstract.COL);
            for (int i = x - vision; i <= x + vision; i++)
                for (int j = y - vision; j <= y + vision; j++)
                    if (i >= 0 && j >= 0 && i < height && j < width)
                        canSee.add(i + "," + j);
        } else {
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++)
                    canSee.add(i + "," + j);
        }
        return canSee;
    }

    public ArrayList<Fan> getFans() {
        return aliveFans;
    }

    public void reduce() {
        //todo

    }

    public Integer getPendingAction() {
        return pendingAction;
    }

    public Integer getPendingPrize() {
        return pendingPrize;
    }

    public Integer getPrizeType() {
        return prizeType;
    }

    public void setPendingAction(Integer pendingAction) {
        this.pendingAction = pendingAction;
    }

    public void setPendingPrize(Integer pendingPrize) {
        this.pendingPrize = pendingPrize;
    }

    public void setPrizeType(Integer prizeType) {
        this.prizeType = prizeType;
    }
}
