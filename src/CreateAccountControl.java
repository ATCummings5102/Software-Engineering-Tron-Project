import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreateAccountControl implements ActionListener {
    private JPanel container;
    private ChatClient client;

    public CreateAccountControl(JPanel container, ChatClient client){
        this.container = container;
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
        createAccountPanel.setError("");  // Reset the error label when a new action is performed

        if (command.equals("Cancel")){
            createAccountPanel.setUserEntry("");
            createAccountPanel.setPasswordEntry("");
            createAccountPanel.setVerifyPasswordEntry("");

            CardLayout cardLayout = (CardLayout)container.getLayout();
            cardLayout.show(container, "1");
        }
        else if (command.equals("Submit")){
            // Validate the input fields
            if (createAccountPanel.getPassword().length() < 6){
                displayError("Your password must be longer than 6 characters", Color.RED);
                return;
            }
            else if (!createAccountPanel.getPassword().equals(createAccountPanel.getVerifiedPassword())){
                displayError("Your passwords must match", Color.RED);
                return;
            }
            else if (createAccountPanel.getUsername().isEmpty() || createAccountPanel.getPassword().isEmpty() ||
                    createAccountPanel.getVerifiedPassword().isEmpty()){
                displayError("You must enter something to create an account", Color.RED);
                return;
            }

            // Create the data object to be sent to the server
            CreateAccountData data = new CreateAccountData(createAccountPanel.getUsername(), createAccountPanel.getPassword());

            try {
                // Send the data to the server
                client.sendToServer(data);
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }

    // Method to display the error message and set color on the panel
    public void displayError(String error, Color color){
        CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
        createAccountPanel.setError(error);
        createAccountPanel.errorLabel.setForeground(color);  // Set the error label color
    }

    // This method is called to handle server responses
    public void handleServerResponse(Object response) {
        CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);

        if (response instanceof String) {
            String result = (String) response;

            if (result.equals("CreateAccountSuccessful")) {
                displayError("Account created successfully!", Color.GREEN);  // Display success in green
            } else {
                displayError("Error: " + result, Color.RED);  // Show error message (e.g., "Username already exists")
            }
        }

        // If we received an Error object, display the message
        else if (response instanceof Error) {
            Error error = (Error) response;
            displayError(error.getMessage(), Color.RED);  // Display error in red
        }
    }
}
