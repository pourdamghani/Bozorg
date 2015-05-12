package Model;

import java.util.HashMap;

/**
 * Created by PARDAZESH on 5/12/2015.
 */
public abstract class Person {
    protected HashMap<String, Integer> information = new HashMap<String, Integer>();

    public HashMap<String, Integer> getInfo() {
        return information;
    }

    public void updateInfo(String infoKey, Integer infoValue) {
        information.put(infoKey, infoValue);
    }

    public Integer getInfo(String infoKey) {
        return information.get(infoKey);
    }

}
