import tron_game.CreateAccountControl;
import tron_game.GameStartControl;
import tron_game.GameStartPanel;

import javax.swing.*;
import java.awt.*;

public class TestMain extends JFrame {

    public TestMain(){
        setTitle("Tron Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        GameStartControl gsc = new GameStartControl(container);
        LoginControl lc = new LoginControl(container);
        CreateAccountControl ac = new CreateAccountControl(container);

        JPanel view1 = new GameStartPanel(gsc);
        JPanel view2 = new LoginPanel(lc);
        JPanel view3 = new CreateAccountPanel(ac);

        container.add(view1, "1");
        container.add(view2, "2");
        container.add(view3, "3");

        cardLayout.show(container, "1");

        add(container, BorderLayout.CENTER);
        setSize(550, 350);
        setVisible(true);
    }

    public static void main(String[] args){
        new TestMain();
    }
}
