package Model;

import Judge.JudgeAbstract;

import java.util.HashMap;

public class Fan {
    private HashMap<String, Integer> information = new HashMap<String, Integer>();

    public Fan(Integer row, Integer col, Integer owner, Integer IS_Alive) {
        information.put(JudgeAbstract.COL, col);
        information.put(JudgeAbstract.ROW, row);
        information.put(JudgeAbstract.OWNER, owner);
        information.put(JudgeAbstract.IS_ALIVE, IS_Alive);
    }

    public HashMap<String, Integer> getInfo() {
        return information;
    }

    public void upadeInfo(String infoKey, Integer infoValue) {
        information.put(infoKey, infoValue);
    }

    public int getInfo(String infoKey) {
        return information.get(infoKey);
    }
}
