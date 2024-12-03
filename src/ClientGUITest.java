import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientGUITest {
    private ClientGUI clientGUI;

    @BeforeEach
    public void setUp() {
        // Create the GUI instance for testing
        SwingUtilities.invokeLater(() -> {
            clientGUI = new ClientGUI();  // Ensure ClientGUI extends JFrame
            clientGUI.setVisible(true);
        });
    }

    @AfterEach
    public void tearDown() {
        if (clientGUI != null) {
            clientGUI.dispose();
        }
    }

    @Test
    public void testLoginFunctionality() {
        SwingUtilities.invokeLater(() -> {
            // Simulate user input
            JTextField usernameEntry = (JTextField) findComponentByName(clientGUI, "usernameEntry");
            JPasswordField passwordEntry = (JPasswordField) findComponentByName(clientGUI, "passwordEntry");
            JButton submitButton = (JButton) findComponentByName(clientGUI, "submitButton");
            JLabel errorLabel = (JLabel) findComponentByName(clientGUI, "errorLabel");

            usernameEntry.setText("testuser");
            passwordEntry.setText("password123");

            // Simulate button click
            submitButton.doClick();

            // Verify no error message
            assertEquals("", errorLabel.getText());
        });
    }

    @Test
    public void testCreateAccountFunctionality() {
        SwingUtilities.invokeLater(() -> {
            // Simulate navigation to Create Account Panel
            JButton createAccountButton = (JButton) findComponentByName(clientGUI, "createAccountButton");
            createAccountButton.doClick();

            // Simulate user input for account creation
            JTextField newUsernameEntry = (JTextField) findComponentByName(clientGUI, "newUsernameTextBox");
            JPasswordField newPasswordEntry = (JPasswordField) findComponentByName(clientGUI, "newPasswordTextBox");
            JPasswordField verifyPasswordEntry = (JPasswordField) findComponentByName(clientGUI, "verifyPasswordTextBox");
            JButton createSubmitButton = (JButton) findComponentByName(clientGUI, "createSubmitButton");
            JLabel errorLabel = (JLabel) findComponentByName(clientGUI, "errorLabel");

            newUsernameEntry.setText("newuser");
            newPasswordEntry.setText("newpassword");
            verifyPasswordEntry.setText("newpassword");

            // Simulate button click
            createSubmitButton.doClick();

            // Verify no error message
            assertEquals("", errorLabel.getText());
        });
    }

    // Utility method to find components by name
    private Component findComponentByName(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
            if (component instanceof Container) {
                Component found = findComponentByName((Container) component, name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
