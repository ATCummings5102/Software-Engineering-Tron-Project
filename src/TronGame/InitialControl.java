package TronGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.CardLayout;

public class InitialControl implements ActionListener {
    private JPanel container;

    // Constructor to accept the container panel (CardLayout)
    public InitialControl(JPanel container) {
        this.container = container;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // When the Login button is clicked, switch to the LoginPanel
        if ("Login".equals(command)) {
            CardLayout cardLayout = (CardLayout) container.getLayout();
            cardLayout.show(container, "2");  // "2" is the name of the LoginPanel in the container
        }

        // When the Create button is clicked, switch to the CreateAccountPanel
        else if ("Create".equals(command)) {
            CardLayout cardLayout = (CardLayout) container.getLayout();
            cardLayout.show(container, "3");  // "3" is the name of the CreateAccountPanel in the container
        }

        // When the Join Game button is clicked, switch to the JoinGamePanel
        else if ("Join".equals(command)) {
            CardLayout cardLayout = (CardLayout) container.getLayout();
            cardLayout.show(container, "4");  // "4" is the name of the JoinGamePanel in the container
        }

        // When the Host Game button is clicked, switch to the HostGamePanel
        else if ("Host".equals(command)) {
            CardLayout cardLayout = (CardLayout) container.getLayout();
            cardLayout.show(container, "5");  // "5" is the name of the HostGamePanel in the container
        }
    }
}
