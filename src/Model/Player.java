package Model;

import Judge.Judge;
import Judge.JudgeAbstract;

import java.awt.*;
import java.util.ArrayList;


public class Player extends Person {

    private ArrayList<Fan> aliveFans = new ArrayList<Fan>();

    private Integer pendingAction, pendingPrize, deadTime;
    private int prizeType;


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
            //todo
            deadTime = 30;
        }
    }





    public Fan throwFan() {
        Fan fan = new Fan(information.get(JudgeAbstract.ROW), information.get(JudgeAbstract.COL), information.get(JudgeAbstract.NAME), JudgeAbstract.ALIVE);
        aliveFans.add(fan);
        fan.setColor(this.getColor());
        information.put(JudgeAbstract.FANS, information.get(JudgeAbstract.FANS));
        return fan;
        }

    public void removeFan(Fan fan) {
        aliveFans.remove(fan);
    }

    public ArrayList<Fan> getFans() {
        return aliveFans;
    }







    public void getGift(int giftType) {
        if (prizeType != giftType)
            pendingPrize = 0;
        prizeType = giftType;
        switch (giftType) {
            case JudgeAbstract.SPEEDUP_CELL:
                information.put(JudgeAbstract.SPEED, information.get(JudgeAbstract.SPEED) * 2);
                pendingPrize += Judge.SPEEDUP_TIME * Judge.TIME_INTERVAL;
                break;
            case JudgeAbstract.RADAR_CELL:
                pendingPrize += Judge.RADAR_TIME * Judge.TIME_INTERVAL;
                break;
            case JudgeAbstract.STONE_CELL:
                pendingPrize += Judge.STONE_TIME * Judge.TIME_INTERVAL;
                break;
            case JudgeAbstract.JUMP_CELL:
                pendingPrize += Judge.JUMP_TIME * Judge.TIME_INTERVAL;
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


    public void next50milis() {
        if (pendingPrize > 0)
            pendingPrize--;
        if (pendingPrize == 0) {
            if (prizeType == JudgeAbstract.SPEEDUP_CELL)
                information.put(JudgeAbstract.SPEED, information.get(JudgeAbstract.SPEED) / 2);
            prizeType = JudgeAbstract.NONE_CELL;
        }
        if (pendingAction > 0)
            pendingAction--;
        if (deadTime > 0)
            deadTime--;
    }

    public Integer getPendingAction() {
        return pendingAction;
    }

    public void setPendingAction(Integer pendingAction) {
        this.pendingAction = pendingAction;
    }

    public Integer getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(Integer deadTime) {
        this.deadTime = deadTime;
    }

    public Integer getPrizeType() {
        return prizeType;
    }

}
