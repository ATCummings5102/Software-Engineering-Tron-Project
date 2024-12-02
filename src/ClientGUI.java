import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame
{

    private Arena arena;
    private Player player1;
    private Player player2;
    private ScoreBoard scoreBoard;

    public ClientGUI()
    {
        // Set up the chat client.
        ChatClient client = new ChatClient();
        client.setHost("localhost");
        client.setPort(8300);

        // Try to open a connection to the server
        try {
            client.openConnection();
        } catch (IOException e) {
            // Show error message if connection fails
            JOptionPane.showMessageDialog(this,
                    "Failed to connect to the server. Please check the server and try again.",
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
            // Close the application if the connection fails
            System.exit(1);
        }

        // Set the title and default close operation.
        setTitle("Tron Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the CardLayout and container panel
        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);


        // Create the Controllers and pass the client to them
        InitialControl ic = new InitialControl(container);
        LoginControl lc = new LoginControl(container, client);
        CreateAccountControl cac = new CreateAccountControl(container, client);
        GameController gameController = new GameController(arena, player1, player2, scoreBoard);

        // Set the client info in the controllers
        client.setLoginControl(lc);
        client.setCreateAccountControl(cac);

        // Create the views and pass the respective controllers
        JPanel view1 = new InitialPanel(ic);
        JPanel view2 = new LoginPanel(lc);
        JPanel view3 = new CreateAccountPanel(cac);
        JPanel view4 = new GameUI();

        // Add the views to the card layout container
        container.add(view1, "1");
        container.add(view2, "2");
        container.add(view3, "3");
        container.add(view4, "4");

        // Show the initial view in the card layout
        cardLayout.show(container, "1");

        // Set layout for JFrame
        setLayout(new BorderLayout()); // Use BorderLayout to make sure the container fills the window

        // Add the card layout container to the JFrame
        add(container, BorderLayout.CENTER);

        // Set up the JFrame size and visibility
        setSize(800, 400);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    public static void main(String[] args) {
        new ClientGUI();
    }
}