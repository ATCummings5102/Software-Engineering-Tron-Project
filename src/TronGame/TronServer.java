package TronGame;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;


public class TronServer extends AbstractServer
{
    private JTextArea log;
    private JLabel status;
    private boolean running = false;
    private Database database;

    public TronServer()
    {
        super(12345);
        this.setTimeout(500);
    }

    public void setLog(JTextArea log)
    {
        this.log = log;
    }

    public void setStatus(JLabel status)
    {
        this.status = status;
    }

    public void serverStarted()
    {
        running = true;
        status.setText("Listening");
        status.setForeground(Color.GREEN);
        log.append("Server has been started successfully\n");
    }

    public void serverStopped()
    {
        status.setText("Stopped");
        status.setForeground(Color.RED);
        log.append("Server has been stopped.\n");
    }

    public void serverClosed()
    {
        running = false;
        status.setText("Closed");
        status.setForeground(Color.RED);
        log.append("Server has been stopped. Press Start to restart server\n");
    }

    public void clientConnected(ConnectionToClient client)
    {
        log.append("Client " + client.getId() + " has connected.\n");
    }

    public void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
    {
        if (database == null) {
            log.append("Database is not initialized\n");
            return;
        }
        // If we received LoginData, verify the account information.
        if (arg0 instanceof LoginData data) {
            log.append("Received login attempt: Username=" + data.getUsername() + ", Password=" + data.getPassword() + "\n");

            Object result;
            if (database.verifyAccount(data.getUsername(), data.getPassword())) {
                result = "LoginSuccessful";
                log.append("Client " + arg1.threadId() + " successfully logged in as " + data.getUsername() + "\n");
            } else {
                result = new Error("The username and password are incorrect.", "IncorrectInfo");
                log.append("Client " + arg1.threadId() + " failed to log in\n");
            }

            try {
                arg1.sendToClient(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // If we received CreateAccountData, create a new account.
        else if (arg0 instanceof CreateAccountData) {
            // Try to create the account
            CreateAccountData data = (CreateAccountData)arg0;
            Object result;

            if (database.createNewAccount(data.getUsername(), data.getPassword())) {
                result = "CreateAccountSuccessful";
                log.append("Client " + arg1.threadId() + " created a new account called " + data.getUsername() + "\n");
            } else {
                result = new Error("The username is already in use.", "AccountError");
                log.append("Client " + arg1.threadId() + " failed to create a new account (Duplicate user)\n");
            }

            // Send the result to the client.
            try {
                arg1.sendToClient(result);  // Sending error or success message
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void setDatabase(Database database)
    {
        this.database = database;
    }
}