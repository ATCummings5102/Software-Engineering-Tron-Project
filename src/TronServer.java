import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import ocsf.server.*;
import ocsf.server.ConnectionToClient;

public class TronServer extends AbstractServer {
    private JTextArea log;
    private JLabel status;
    private boolean running = false;
    private Database database;
    private ArrayList<Player> players = new ArrayList<>();
    private static final int MAX_PLAYERS = 2; // Limit to 2 players

    public TronServer() {
        super(12345); // Default port
        this.setTimeout(500);
    }

    public void setLog(JTextArea log) {
        this.log = log;
    }

    public void setStatus(JLabel status) {
        this.status = status;
    }

    @Override
    public void serverStarted() {
        running = true;
        status.setText("Listening");
        status.setForeground(Color.GREEN);
        log.append("Server has been started successfully\n");
    }

    @Override
    public void serverStopped() {
        running = false;
        status.setText("Stopped");
        status.setForeground(Color.RED);
        log.append("Server has been stopped.\n");
    }

    @Override
    public void serverClosed() {
        running = false;
        status.setText("Closed");
        status.setForeground(Color.RED);
        log.append("Server has been stopped. Press Start to restart server\n");
    }

    @Override
    public void clientConnected(ConnectionToClient client) {
        log.append("Client " + client.getId() + " has connected.\n");
    }

    @Override
    public void handleMessageFromClient(Object arg0, ConnectionToClient client) {
        if (arg0 instanceof LoginData loginData) {
            String username = loginData.getUsername();
            log.append("Login Attempt: Username=" + username + ", Password=" + loginData.getPassword() + "\n");

            if (players.size() < MAX_PLAYERS) {
                createPlayer(username);
                log.append("Player added: " + username + "\n");
                log.append("Player count: " + players.size() + "\n");
                sendToAllClients("Player joined: " + username);
            } else {
                log.append("Player rejected: " + username + " (Maximum players reached)\n");
                try {
                    client.sendToClient("Error: Maximum players reached. You cannot join the game.");
                    log.append("Error: Maximum players reached: " + players.size() + "\n");
                } catch (IOException e) {
                    log.append("Error sending message to client: " + e.getMessage() + "\n");
                }
            }
        } else if (arg0 instanceof CreateAccountData) {
            // Account creation logic here (if needed)
        } else {
            log.append("Received message from client: " + arg0 + "\n");
            sendToAllClients(arg0); // Relay the message to all clients
        }
    }

    private void createPlayer(String username){
        Position startPosition = new Position(5, 15); // Default starting position
        Direction startDirection = Direction.RIGHT;  // Default direction
        Player newPlayer = new Player(username, startPosition, startDirection);
        sendToAllClients(newPlayer);
        players.add(newPlayer);
    }

    private ArrayList<Player> getPlayer()
    {
        return players;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    protected void listeningException(Throwable exception) {
        log.append("Listening Exception: " + exception.getMessage() + "\n");
        exception.printStackTrace();
    }

    @Override
    public void clientDisconnected(ConnectionToClient client) {
        log.append("Client " + client.getId() + " disconnected.\n");
    }
}
