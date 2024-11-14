import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginControl implements ActionListener {
    private final JPanel container;
    private final ChatClient client;

    public LoginControl(JPanel container, ChatClient client) {
        this.container = container;
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Cancel")) {
            CardLayout cardLayout = (CardLayout) container.getLayout();
            cardLayout.show(container, "1");
        } else if (command.equals("Submit")) {
            LoginPanel loginPanel = (LoginPanel) container.getComponent(1);
            LoginData data = new LoginData(loginPanel.getUsername(), loginPanel.getPassword());

            if (data.getUsername().isEmpty() || data.getPassword().isEmpty()) {
                displayError("You must enter a username and password");
                return;
            }

            try {
                client.sendToServer(data);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void displayError(String error) {
        LoginPanel loginPanel = (LoginPanel) container.getComponent(1);
        loginPanel.setError(error);
    }
}
