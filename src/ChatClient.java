import ocsf.client.AbstractClient;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatClient extends AbstractClient {
    private LoginControl loginControl;
    private CreateAccountControl createAccountControl;
    private ArrayList<Player> players = new ArrayList<>();
    private ClientGUI clientGUI; // Reference to ClientGUI

    public ChatClient()
    {
        super("10.252.147.176", 8300);
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
    public void handleMessageFromServer(Object msg) {
        if (msg instanceof String) {
            if (msg.equals("LoginSuccessful") || ((String) msg).contains("Player joined")) {
                loginControl.handleLoginResponse(msg);
            } else if (msg.equals("CreateAccountSuccessful")) {
                createAccountControl.handleServerResponse(msg);
            } else if (((String) msg).contains("Error")) {
                System.out.println((String) msg);
            }
        } else if (msg instanceof Error) {
            Error error = (Error) msg;
            String errorMessage = error.getMessage();
            if (errorMessage.contains("IncorrectInfo")) {
                loginControl.displayError(errorMessage);
            } else if (errorMessage.contains("already in use")) {
                createAccountControl.handleServerResponse(error);
            }
        } else if (msg instanceof Player) {
            players.add((Player) msg);
            System.out.println(players.size() + " players added");

            if (clientGUI != null) {
                clientGUI.addPlayerToGameUI(players.getLast()); // Notify ClientGUI
            } else {
                System.out.println("Error: clientGUI is null. Cannot update the UI.");
            }
        } else if (msg instanceof PlayerTrailData playerTrailData) {
            String playerName = playerTrailData.getPlayerName();
            List<Position> playerTrail = playerTrailData.getPlayerTrail().stream()
                    .map(pos -> new Position(pos.getX(), pos.getY()))
                    .collect(Collectors.toList());
            System.out.println("Received trail data for player: " + playerName + ", Trail: " + playerTrail);

            updatePlayerTrail(playerName, playerTrail);

            // Notify the game UI to update the display
            if (clientGUI != null) {
                clientGUI.updateGameUI();
            }
        }
    }
        private void updatePlayerTrail(String playerName, List<Position> playerTrail) {
        players.stream()
                .filter(player -> player.getName().equals(playerName))
                .findFirst()
                .ifPresent(player -> {
                    player.getTrail().clear();
                    List<ColoredPosition> coloredTrail = playerTrail.stream()
                            .map(pos -> new ColoredPosition(pos.getX(), pos.getY(), player.getTrailColor()))
                            .toList();
                    player.getTrail().addAll(coloredTrail);
                    System.out.println("Updated trail for player: " + playerName);
                });
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
