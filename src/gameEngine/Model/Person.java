package gameEngine.Model;

import java.awt.*;
import java.util.HashMap;

public abstract class Person {
    private Image image;

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
