package tron_game;

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

        if (command.equals("Cancel")){
            CardLayout cardLayout = (CardLayout)container.getLayout();
            cardLayout.show(container, "1");
        }
        else if (command.equals("Submit")){
            CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
            createAccountPanel.setError("");

            if (createAccountPanel.getPassword().length() <= 6){
                displayError("Your password must be longer than 6 characters");
                return;
            }
            else if (!createAccountPanel.getPassword().equals(createAccountPanel.getVerifiedPassword())){
                displayError("Your passwords must match");
                return;
            }
            else if (createAccountPanel.getUsername().isEmpty() || createAccountPanel.getPassword().isEmpty() ||
            createAccountPanel.getVerifiedPassword().isEmpty()){
                displayError("You must enter something to create an account");
                return;
            }

            CreateAccountData data = new CreateAccountData(createAccountPanel.getUsername(), createAccountPanel.getPassword());

            try {
                createAccountPanel.setUserEntry("");
                createAccountPanel.setPasswordEntry("");
                createAccountPanel.setVerifyPasswordEntry("");
                client.sendToServer(data);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void displayError(String error){
        CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
        createAccountPanel.setError(error);
    }
}
