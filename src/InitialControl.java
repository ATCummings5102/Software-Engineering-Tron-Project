import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialControl implements ActionListener {
    private final JPanel container;

    InitialControl(JPanel container) {
        this.container = container;
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("Login")) {
            CardLayout cardLayout = (CardLayout) container.getLayout();
            cardLayout.show(container, "2");
        } else if (action.equals("Create")) {
            CardLayout cardLayout = (CardLayout) container.getLayout();
            cardLayout.show(container, "3");
        }
    }
}
