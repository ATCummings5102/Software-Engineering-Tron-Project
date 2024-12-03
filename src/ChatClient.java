import ocsf.client.AbstractClient;
import java.util.ArrayList;
import java.util.List;

public class ChatClient extends AbstractClient {
    private LoginControl loginControl;
    private CreateAccountControl createAccountControl;
    private ArrayList<Player> players = new ArrayList<>();
    private ClientGUI clientGUI; // Reference to ClientGUI

    public ChatClient()
    {
        super("localhost", 8300);
    }

    // Setter for ClientGUI
    public void setClientGUI(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }


    public void setLoginControl(LoginControl loginControl) {
        this.loginControl = loginControl;
    }

    public void setCreateAccountControl(CreateAccountControl createAccountControl) {
        this.createAccountControl = createAccountControl;
    }

    @Override
    public void handleMessageFromServer(Object msg)
    {
        if (msg instanceof String)
        {
            if (msg.equals("LoginSuccessful") || ((String) msg).contains("Player joined")) {
                loginControl.handleLoginResponse(msg);
            } else if (msg.equals("CreateAccountSuccessful"))
            {
                createAccountControl.handleServerResponse(msg);
            }
            else if (((String) msg).contains("Error"))
            {
                System.out.println((String) msg);
            }
        }
        else if (msg instanceof Error) {
            Error error = (Error) msg;
            String errorMessage = error.getMessage();
            if (errorMessage.contains("IncorrectInfo")) {
                loginControl.displayError(errorMessage);
            } else if (errorMessage.contains("already in use")) {
                createAccountControl.handleServerResponse(error);
            }
        }
        else if (msg instanceof Player)
        {
            players.add((Player) msg);
            System.out.println(players.size() + " players added");

            if (clientGUI != null) {
                clientGUI.addPlayerToGameUI(players.getLast()); // Notify ClientGUI
            } else {
                System.out.println("Error: clientGUI is null. Cannot update the UI.");
            }
        }
        else if (msg instanceof PlayerTrailData playerTrailData) {
            String playerName = playerTrailData.getPlayerName();
            List<Position> playerTrail = playerTrailData.getPlayerTrail();
            System.out.println("Received trail data for player: " + playerName + ", Trail: " + playerTrail);

            // Update the trail for the corresponding player
            for (Player player : players) {
                if (player.getName().equals(playerName)) {
                    player.getTrail().clear();
                    player.getTrail().addAll(playerTrail);
                    System.out.println("Updated trail for player: " + playerName);
                    break;
                }
            }

            // Notify the game UI to update the display
            if (clientGUI != null) {
                clientGUI.updateGameUI();
            }
        }
    }

    public void connectionException(Throwable exception) {
        System.out.println("Server connection exception: " + exception.getMessage());
        exception.printStackTrace();
    }

    public void connectionEstablished()
    {
        System.out.println("Connection Established");
    }
}
