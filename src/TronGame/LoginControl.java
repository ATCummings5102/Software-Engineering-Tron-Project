package TronGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginControl implements ActionListener {
    private JPanel container;
    private ChatClient client;

    public LoginControl(JPanel container, ChatClient client){
        this.container = container;
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        //Initialize loginPanel here to make sure error text refreshes
        LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
        loginPanel.setError("");

        if (command.equals("Cancel")){
            //Allow user entered data to refresh after pressing "cancel"
            loginPanel.setUsername("");
            loginPanel.setPasswordEntry("");

            CardLayout cardLayout = (CardLayout)container.getLayout();
            cardLayout.show(container, "1");
        }
        else if (command.equals("Submit")){
            LoginData data = new LoginData(loginPanel.getUsername(), loginPanel.getPassword());

            if (data.getUsername().isEmpty() || data.getPassword().isEmpty()){
                displayError("You must enter a username and password");
                return;
            }

            //Send login information to server and have server
            //return data to client to display GameUI if successful
            //(4th view in CardLayout)

            try {
                client.sendToServer(data);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void displayError(String error){
        LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
        loginPanel.setError(error);
    }
}
