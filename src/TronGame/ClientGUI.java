package TronGame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame {

    public ClientGUI(){
        setTitle("Tron Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        ChatClient client = new ChatClient(container);

        try {
            client.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GameStartControl gsc = new GameStartControl(container);
        LoginControl lc = new LoginControl(container, client);
        CreateAccountControl ac = new CreateAccountControl(container, client);

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
        new ClientGUI();
    }
}
