package TronGame;

import javax.swing.*;
import java.awt.*;

public class CreateAccountPanel extends JPanel {

    private JTextField userEntry;
    private JPasswordField passwordEntry;
    private JPasswordField verifyPasswordEntry;
    private JLabel errorLabel;

    public CreateAccountPanel(CreateAccountControl createAccountControl){
        Color MAIN_BACKGROUND = new Color(0, 0, 102);
        Color BOX_BACKGROUND = new Color(102, 153, 204);

        JPanel mainPanel = new JPanel(new GridLayout(10, 6, 3, 3));
        JPanel fieldPanel = new JPanel(new GridLayout(3, 2, 3, 5));
        JPanel labelPanel = new JPanel(new GridLayout(3, 1, 3, 1));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        errorLabel = new JLabel("", JLabel.CENTER);
        JLabel label1 = new JLabel("Enter a username and password to create an account");
        JLabel label2 = new JLabel("Your password must be at least 6 characters");
        JLabel userLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JLabel verifyPasswordLabel = new JLabel("Verify Password: ");
        userEntry = new JTextField(10);
        passwordEntry = new JPasswordField(10);
        verifyPasswordEntry = new JPasswordField(10);
        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel");
        setBackground(MAIN_BACKGROUND);
        mainPanel.setBackground(MAIN_BACKGROUND);
        fieldPanel.setBackground(MAIN_BACKGROUND);
        labelPanel.setBackground(MAIN_BACKGROUND);
        buttonPanel.setBackground(MAIN_BACKGROUND);
        label1.setForeground(Color.white);
        label2.setForeground(Color.white);
        userLabel.setForeground(Color.white);
        passwordLabel.setForeground(Color.white);
        verifyPasswordLabel.setForeground(Color.white);
        submit.setBackground(BOX_BACKGROUND);
        cancel.setBackground(BOX_BACKGROUND);
        submit.setForeground(Color.white);
        cancel.setForeground(Color.white);


        errorLabel.setForeground(Color.RED);
        label1.setHorizontalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);

        submit.addActionListener(createAccountControl);
        cancel.addActionListener(createAccountControl);

        labelPanel.add(errorLabel);
        labelPanel.add(label1);
        labelPanel.add(label2);

        fieldPanel.add(userLabel);
        fieldPanel.add(userEntry);
        fieldPanel.add(passwordLabel);
        fieldPanel.add(passwordEntry);
        fieldPanel.add(verifyPasswordLabel);
        fieldPanel.add(verifyPasswordEntry);

        buttonPanel.add(submit);
        buttonPanel.add(cancel);

        mainPanel.add(labelPanel);
        mainPanel.add(fieldPanel);
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    public String getUsername(){
        return userEntry.getText();
    }

    public String getPassword(){
        return passwordEntry.getText();
    }

    public String getVerifiedPassword(){
        return verifyPasswordEntry.getText();
    }

    public void setError(String error){
        errorLabel.setText(error);
    }

    public void setUserEntry(String userEntry){
        this.userEntry.setText(userEntry);
    }

    public void setPasswordEntry(String passwordEntry){
        this.passwordEntry.setText(passwordEntry);
    }

    public void setVerifyPasswordEntry(String passwordEntry){
        this.verifyPasswordEntry.setText(passwordEntry);
    }
}
