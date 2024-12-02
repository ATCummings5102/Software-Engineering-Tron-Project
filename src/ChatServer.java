import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import javax.swing.*;
import java.awt.*;

public class ChatServer extends AbstractServer {
    private JTextArea log;
    private JLabel status;

    public ChatServer() {
        super(12345);
    }

    public ChatServer(int port) {
        super(port);
    }

    public JTextArea getLog() {
        return log;
    }

    public void setLog(JTextArea log) {
        this.log = log;
    }

    public JLabel getStatus() {
        return status;
    }

    public void setStatus(JLabel status) {
        this.status = status;
    }

    @Override
    protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {

        if (arg0 instanceof LoginData loginData) {
            System.out.println("Username: " + loginData.getUsername() + " Password: " + loginData.getPassword());
        }
        // TODO Auto-generated method stub
        System.out.println("Message from Client" + arg0.toString() + arg1.toString());
        //log.append("Message from Client" + arg0.toString() + arg1.toString() + "\n");

    }

    protected void listeningException(Throwable exception) {
        //Display info about the exception
        System.out.println("Listening Exception:" + exception);
        exception.printStackTrace();
        System.out.println(exception.getMessage());

    }

    protected void serverStarted() {
        System.out.println("Server Started");
        status.setText("Server Started");
        status.setForeground(Color.GREEN);
        //log.append("Server Started\n");
    }

    protected void serverStopped() {
        System.out.println("Server Stopped");
        status.setText("Server Stopped");
        status.setForeground(Color.RED);
        //log.append("Server Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
    }

    protected void serverClosed() {
        System.out.println("Server and all current clients are closed - Press Listen to Restart");
        status.setText("Server and all current clients are closed - Press Listen to Restart");
        status.setForeground(Color.YELLOW);
        //log.append("Server and all current clients are closed - Press Listen to Restart\n");
    }


    protected void clientConnected(ConnectionToClient client) {
        System.out.println("Client Connected");
        log.append("Client Connected\n");
    }


}
