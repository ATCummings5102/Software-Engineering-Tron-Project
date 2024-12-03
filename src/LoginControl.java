import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginControl implements ActionListener {
    // Private data fields for the container and chat client.
    private JPanel container;
    private ChatClient client;

    // Constructor for the login controller.
    public LoginControl(JPanel container, ChatClient client) {
        this.container = container;
        this.client = client;
    }

    // Handle button clicks.
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the name of the button clicked.
        String command = e.getActionCommand();

        // Initialize the login panel to ensure the error text is refreshed.
        LoginPanel loginPanel = (LoginPanel) container.getComponent(1);
        loginPanel.setError("");


        if (command.equals("Cancel")) {
            // Clear user-entered data and take the user back to the initial panel.
            loginPanel.setUsername("");
            loginPanel.setPasswordEntry("");

            CardLayout cardLayout = (CardLayout) container.getLayout();
            cardLayout.show(container, "1");
        } else if (command.equals("Submit")) {
            // Get the username and password the user entered.
            LoginData data = new LoginData(loginPanel.getUsername(), loginPanel.getPassword());

            // Check the validity of the information locally first.
            if (data.getUsername().isEmpty() || data.getPassword().isEmpty()) {
                displayError("You must enter a username and password.");
                return;
            }

            // Submit the login information to the server.
            try {
                client.sendToServer(data);
            } catch (IOException ex) {
                displayError("Error connecting to the server.");
            }
        }
    }
    public void handleLoginResponse(Object response) 
    {
        String message = (String) response;

        System.out.println("Message: " + message);

        // Check if login is successful
        if (response != null && message.contains("Player joined"))
        {
            // If login is successful, call the loginSuccess() method
            loginSuccess();

        } else if (response instanceof Error)
        {
            // Handle error case (invalid login)
            displayError("Invalid username or password.");
        }
    }

    // After the login is successful, set the User object and return to the initial panel with enabled buttons.
    public void loginSuccess() {
        // Switch back to the initial panel.
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "4");

        // Enable the Host Game and Join Game buttons.
        InitialPanel initialPanel = (InitialPanel) container.getComponent(0);
        initialPanel.enableGameButtons();
    }

    // Method that displays a message in the error label.
    public void displayError(String error) {
        LoginPanel loginPanel = (LoginPanel) container.getComponent(1);
        loginPanel.setError(error);
    }
}
