import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChatServer extends AbstractServer {
    private JTextArea log;
    private JLabel status;
    private ArrayList<Player> players = new ArrayList<>();
    private static final int MAX_PLAYERS = 2;

    public ChatServer() {
        super(12345); // Default port
    }

    public ChatServer(int port) {
        super(port); // Custom port
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
    protected void handleMessageFromClient(Object arg0, ConnectionToClient client) {
        if (arg0 instanceof LoginData loginData) {
            String username = loginData.getUsername();
            System.out.println("Login Attempt: Username=" + username + ", Password=" + loginData.getPassword());

            if (players.size() < MAX_PLAYERS) {
                createPlayer(username);
                log.append("Player added: " + username + "\n");
                sendToAllClients("Player joined: " + username);
            } else {
                log.append("Player rejected: " + username + " (Maximum players reached)\n");
                try {
                    client.sendToClient("Error: Maximum players reached. You cannot join the game.");
                } catch (Exception e) {
                    log.append("Error sending message to client: " + e.getMessage() + "\n");
                }
            }
        } else {
            log.append("Received message from client: " + arg0 + "\n");
            sendToAllClients(arg0); // Relay the message to all clients
        }
    }

    private void createPlayer(String username) {
        Position startPosition = new Position(4, 4); // Default starting position
        Direction startDirection = Direction.RIGHT;  // Default direction
        Player newPlayer = new Player(username, startPosition, startDirection);
        players.add(newPlayer);
    }

    @Override
    protected void listeningException(Throwable exception) {
        log.append("Listening Exception: " + exception.getMessage() + "\n");
        exception.printStackTrace();
    }

    @Override
    protected void serverStarted() {
        System.out.println("Server Started");
        if (status != null) {
            status.setText("Server Started");
            status.setForeground(Color.GREEN);
        }
        log.append("Server Started\n");
    }

    @Override
    protected void serverStopped() {
        System.out.println("Server Stopped");
        if (status != null) {
            status.setText("Server Stopped");
            status.setForeground(Color.RED);
        }
        log.append("Server Stopped - No longer accepting new clients\n");
    }

    @Override
    protected void serverClosed() {
        System.out.println("Server Closed");
        if (status != null) {
            status.setText("Server Closed");
            status.setForeground(Color.YELLOW);
        }
        log.append("Server and all clients closed - Restart to accept new clients\n");
    }

    @Override
    protected void clientConnected(ConnectionToClient client) {
        System.out.println("Client Connected: " + client);
        log.append("Client Connected: " + client + "\n");
    }
}
