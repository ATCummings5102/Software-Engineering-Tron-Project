import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private Player tempPlayer;
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
                sendToAllClients(tempPlayer);
            } else {
                log.append("Player rejected: " + username + " (Maximum players reached)\n");
                try {
                    client.sendToClient("Error: Maximum players reached. You cannot join the game.");
                    log.append("Error: Maximum players reached: " + players.size() + "\n");
                } catch (IOException e) {
                    log.append("Error sending message to client: " + e.getMessage() + "\n");
                }
            }
        } else if (arg0 instanceof CreateAccountData createAccountData) {
            // Handling account creation logic
            String username = createAccountData.getUsername();
            String password = createAccountData.getPassword();
            log.append("Account creation attempt: Username=" + username + "\n");

            Object result;

            if (database.verifyAccount(username, password)) {
                result = new Error("Username already exists.", "AccountError");
                log.append("Account creation failed: Username already exists\n");
            } else {
                if (database.createNewAccount(username, password)) {
                    result = "CreateAccountSuccessful";
                    log.append("Account created successfully: " + username + "\n");
                } else {
                    result = new Error("Account creation failed. Please try again.", "AccountError");
                    log.append("Account creation failed for: " + username + "\n");
                }
            }

            try {
                client.sendToClient(result);  // Sending success or error message
            } catch (IOException e) {
                log.append("Error sending message to client: " + e.getMessage() + "\n");
            }
        } else if (arg0 instanceof PlayerTrailData playerTrailData) {
            String playerName = playerTrailData.getPlayerName();
            List<Position> playerTrail = playerTrailData.getPlayerTrail();
            log.append("Received trail data from player: " + playerName + "\n");
            System.out.println("Received trail data from player: " + playerName + ", Trail: " + playerTrail);

            // Find the player and update their trail
            for (Player player : players) {
                if (player.getName().equals(playerName)) {
                    player.getTrail().clear();
                    player.getTrail().addAll(playerTrail);
                    log.append("Updated trail for player: " + playerName + "\n");
                    break;
                }
            }

            // Send the updated trail data to all other clients
            for (Thread clientThread : getClientConnections()) {
                ConnectionToClient otherClient = (ConnectionToClient) clientThread;
                if (otherClient != client) {
                    try {
                        otherClient.sendToClient(playerTrailData);
                    } catch (IOException e) {
                        log.append("Error sending trail data to client: " + e.getMessage() + "\n");
                    }
                }
            }
        } else {
            log.append("Received message from client: " + arg0 + "\n");
            sendToAllClients(arg0); // Relay the message to all clients
        }
    }

    private void createPlayer(String username) {
        Position startPosition = new Position(5, 15); // Default starting position
        Direction startDirection = Direction.RIGHT;  // Default direction
        Color trailColor = getRandomColor(); // Generate a random trail color
        Player newPlayer = new Player(username, startPosition, startDirection, trailColor);
        tempPlayer = newPlayer;
        players.add(newPlayer);
    }

    private Color getRandomColor() {
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        return new Color(r, g, b);
    }

    private ArrayList<Player> getPlayer() {
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
