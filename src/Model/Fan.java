package Model;

import Judge.JudgeAbstract;
public class Fan extends Person {

    public Fan(Integer row, Integer col, Integer owner, Integer IS_Alive) {
        information.put(JudgeAbstract.COL, col);
        information.put(JudgeAbstract.ROW, row);
        information.put(JudgeAbstract.OWNER, owner);
        information.put(JudgeAbstract.IS_ALIVE, IS_Alive);
    }
}
