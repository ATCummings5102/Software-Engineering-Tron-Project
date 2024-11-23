package TronGame;

import javax.swing.*;
import java.awt.*;

public class GameStartPanel extends JPanel{
    GameStartPanel(GameStartControl gameStartControl){
        Color MAIN_BACKGROUND = new Color(0, 0, 102);
        Color BOX_BACKGROUND = new Color(102, 153, 204);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 5, 5));
        JPanel labelPanel = new JPanel();
        JPanel firstButtonPanel = new JPanel();
        JPanel secondButtonPanel = new JPanel();
        JLabel accountInfo = new JLabel("Account Information");
        JButton login = new JButton("Login");
        JButton create = new JButton("Create");
        setBackground(MAIN_BACKGROUND); //change later
        labelPanel.setBackground(MAIN_BACKGROUND);
        accountInfo.setForeground(Color.white);
        firstButtonPanel.setBackground(MAIN_BACKGROUND);
        secondButtonPanel.setBackground(MAIN_BACKGROUND);
        panel.setBackground(MAIN_BACKGROUND);
        login.setBackground(BOX_BACKGROUND);
        create.setBackground(BOX_BACKGROUND);
        login.setForeground(Color.white);
        create.setForeground(Color.white);

        login.addActionListener(gameStartControl);
        create.addActionListener(gameStartControl);

        labelPanel.add(accountInfo);
        firstButtonPanel.add(login);
        secondButtonPanel.add(create);

        panel.add(labelPanel);
        panel.add(firstButtonPanel);
        panel.add(secondButtonPanel);

        add(panel);
    }
}
