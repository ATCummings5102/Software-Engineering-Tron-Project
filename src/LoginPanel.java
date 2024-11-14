import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private final JTextField usernameEntry;
    private final JPasswordField passwordEntry;
    private final JLabel errorLabel;

    public LoginPanel(LoginControl loginControl) {
        JPanel mainPanel = new JPanel(new GridLayout(4, 4, 3, -5));
        JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel userPanel = new JPanel(new FlowLayout());
        JPanel passwordPanel = new JPanel(new FlowLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Enter your username and password to log in");
        JLabel usernameLabel = new JLabel("Username: ");
        usernameEntry = new JTextField(10);
        JLabel passwordLabel = new JLabel("Password: ");
        passwordEntry = new JPasswordField(10);
        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel");
        errorLabel = new JLabel("", JLabel.CENTER);

        errorLabel.setForeground(Color.RED);

        submit.addActionListener(loginControl);
        cancel.addActionListener(loginControl);

        labelPanel.add(errorLabel);
        labelPanel.add(label);
        userPanel.add(usernameLabel);
        userPanel.add(usernameEntry);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordEntry);
        buttonPanel.add(submit);
        buttonPanel.add(cancel);

        mainPanel.add(labelPanel);
        mainPanel.add(userPanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    public String getUsername() {
        return usernameEntry.getText();
    }

    public String getPassword() {
        return new String(passwordEntry.getPassword());
    }

    public void setError(String error) {
        errorLabel.setText(error);
    }
}
