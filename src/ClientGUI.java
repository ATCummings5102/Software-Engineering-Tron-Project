import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame {
    private GameUI gameUI;
    private ChatClient client;
    private CardLayout cardLayout;
    private JPanel container;
    private Player player;

    public ClientGUI() {
        client = new ChatClient();
        client.setClientGUI(this);

        try {
            System.out.println("Attempting to connect to the server...");
            client.openConnection();
            System.out.println("Connected to the server.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to connect to the server. Please check the server and try again.",
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Print stack trace for debugging
            System.exit(1);
        }

        setTitle("Tron Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        InitialControl ic = new InitialControl(container);
        LoginControl lc = new LoginControl(container, client);
        CreateAccountControl cac = new CreateAccountControl(container, client);

        client.setLoginControl(lc);
        client.setCreateAccountControl(cac);

        gameUI = new GameUI(100, 100, this);

        JPanel view1 = new InitialPanel(ic);
        JPanel view2 = new LoginPanel(lc);
        JPanel view3 = new CreateAccountPanel(cac);
        JPanel view4 = gameUI;

        container.add(view1, "1");
        container.add(view2, "2");
        container.add(view3, "3");
        container.add(view4, "4");

        cardLayout.show(container, "1");

        setLayout(new BorderLayout());
        add(container, BorderLayout.CENTER);

        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addPlayerToGameUI(Player player) {
        this.player = player;
        if (gameUI != null) {
            gameUI.setPlayer(player);
            cardLayout.show(container, "4");
            gameUI.updateUI();
            System.out.println("Player added to the game: " + player.getName());
        }
    }


    public void sendPlayerTrailData() {
        if (player != null) {
            PlayerTrailData playerTrailData = new PlayerTrailData(player.getName(), player.getTrail());
            sendToServer(playerTrailData);
        }
    }

    public void sendToServer(Object msg) {
        try {
            client.sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateGameUI() {
        if (gameUI != null) {
            gameUI.repaint();
        }
    }

    public static void main(String[] args) {
        new ClientGUI();
    }
}