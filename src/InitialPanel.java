import javax.swing.*;
import java.awt.*;

public class InitialPanel extends JPanel {
    InitialPanel(InitialControl initialControl) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 5, 5));
        JPanel labelPanel = new JPanel();
        JPanel firstButtonPanel = new JPanel();
        JPanel secondButtonPanel = new JPanel();
        JLabel accountInfo = new JLabel("Account Information");
        JButton login = new JButton("Login");
        JButton create = new JButton("Create");

        login.addActionListener(initialControl);
        create.addActionListener(initialControl);

        labelPanel.add(accountInfo);
        firstButtonPanel.add(login);
        secondButtonPanel.add(create);

        panel.add(labelPanel);
        panel.add(firstButtonPanel);
        panel.add(secondButtonPanel);

        add(panel);
    }
}
