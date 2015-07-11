package graphic;

import javax.swing.*;

/**
 * Created by yashardabiran on 7/11/15.
 */
public class InformationPanel extends JPanel{
    public InformationPanel() {
        ImageIcon reza = new ImageIcon("img/Reza.png");
        ImageIcon hasin = new ImageIcon("img/Hasin.png");
        ImageIcon jafar = new ImageIcon("img/jafar.png");
        ImageIcon saman = new ImageIcon("img/Saman.png");

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel rezaLabel = new JLabel("Reza", reza, SwingConstants.LEFT);
        JLabel hasinLabel = new JLabel("Hasin", hasin, SwingConstants.LEFT);
        JLabel jafarLabel = new JLabel("Jafar", jafar, SwingConstants.LEFT);
        JLabel samanLabel = new JLabel("Saman", saman, SwingConstants.LEFT);

        this.add(rezaLabel);
        this.add(hasinLabel);
        this.add(jafarLabel);
        this.add(samanLabel);
    }
}
