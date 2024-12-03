import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class InitialPanel extends JPanel
{
    // Used to create buttons and also to check if false to ensure a non logged in player can't join.
    private JButton joinButton;
    private JButton hostButton;

    // Constructor for InitialPanel
    InitialPanel(InitialControl initialControl)
    {
        // Panel setup
        setBackground(new Color(12, 4, 41, 255)); // Blue that matches image
        setLayout(new BorderLayout()); // BorderLayout for Cardinal Direction panels (And center)

        // Logo panel (Top)
        add(LogoPanel(), BorderLayout.NORTH);

        // Everything on one row, split into two sides (cols)
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        mainPanel.setOpaque(false); // Makes panel BG color instead of the default white

        // Left Panel for Buttons
        mainPanel.add(ButtonPanel(initialControl));

        // Right Panel for Leaderboard
        mainPanel.add(LeaderboardPanel());

        // Add the main panel to the center
        add(mainPanel, BorderLayout.CENTER);
    }


    private JPanel LogoPanel()
    {
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false); // Makes panel BG color instead of the default white

        // Load and scale down image
        ImageIcon logoIcon = new ImageIcon(
                new ImageIcon(Objects.requireNonNull(getClass().getResource("Tron_logo.PNG")))
                        .getImage().getScaledInstance(450, 125, Image.SCALE_SMOOTH) // Smooth looks best IMO
        );

        // Set the logo label (using scaled tron logo)
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center of top Panel

        topPanel.add(logoLabel);

        return topPanel;
    }

    // Button panel with Login, Create, Join, Host, and Exit buttons
    private JPanel ButtonPanel(InitialControl initialControl)
    {
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setOpaque(false);

        JButton login = new JButton("Login");
        JButton create = new JButton("Create");
        joinButton = new JButton("Join Game");
        hostButton = new JButton("Host Game");
        JButton exitButton = new JButton("Exit");

        // Customize buttons + effects
        customizeButton(login);
        customizeButton(create);
        customizeButton(joinButton);
        customizeButton(hostButton);
        customizeButton(exitButton);

        // Setters for login and create actions
        login.setActionCommand("Login");
        create.setActionCommand("Create");
        login.addActionListener(initialControl);
        create.addActionListener(initialControl);

        // Initially disable the game buttons
        joinButton.setEnabled(false);
        hostButton.setEnabled(false);

        // Add exit button to the panel (With functionality)
        exitButton.addActionListener(_ -> System.exit(0));

        // Add all buttons to panel
        buttonPanel.add(login);
        buttonPanel.add(create);
        buttonPanel.add(joinButton);
        buttonPanel.add(hostButton);
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    // Panel to display the Leaderboard table
    private JPanel LeaderboardPanel()
    {
        // Table used to display each play and info
        JTable leaderboardTable = LeaderboardTable();
        JScrollPane leaderboardScrollPane = new JScrollPane(leaderboardTable); // Scrollable

        JPanel leaderboardPanel = new JPanel(new BorderLayout());
        leaderboardPanel.add(leaderboardScrollPane, BorderLayout.CENTER);

        return leaderboardPanel;
    }

    // Create the leaderboard table
    private JTable LeaderboardTable() {
        // Create a Database object
        Database db = new Database();

        // Fetch leaderboard data using the method from the Database class
        ArrayList<Object[]> leaderboardData = db.fetchLeaderboardData();

        // Column names for the table
        String[] columnNames = {"Rank", "Player", "Wins"};

        // Convert ArrayList<Object[]> to Object[][] for JTable
        Object[][] data = new Object[leaderboardData.size()][3];
        for (int i = 0; i < leaderboardData.size(); i++) {
            data[i] = leaderboardData.get(i);
        }

        // Creates the dynamic table
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Create the JTable with the model
        JTable table = new JTable(model);

        table.setEnabled(false); // Disable editing
        table.setRowHeight(32);  // Set row height

        // Customize table appearance
        table.setBackground(new Color(12, 4, 41, 255)); // Blue background
        table.setForeground(Color.WHITE); // Text color
        table.getTableHeader().setBackground(new Color(12, 4, 90, 255)); // Header background
        table.getTableHeader().setForeground(Color.WHITE); // Header text color

        // Set column widths
        table.getColumnModel().getColumn(0).setMaxWidth(40); // Rank column width
        table.getColumnModel().getColumn(2).setMaxWidth(50); // Wins column width

        // Fills in space below the table with blue instead of white
        table.setFillsViewportHeight(true);

        return table;
    }

    public void reinitializeLeaderboard() {
        // Remove the old leaderboard panel
        JPanel mainPanel = (JPanel) getComponent(1); // Main panel (with buttons and leaderboard)
        mainPanel.remove(1); // Remove the leaderboard panel (index 1)

        // Add the new leaderboard panel (recreates the leaderboard table)
        mainPanel.add(LeaderboardPanel(), BorderLayout.EAST); // Re-add the leaderboard panel

        // Refresh the layout to reflect changes
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    // Customize button appearance
    private void customizeButton(JButton button)
    {

        button.setBackground(new Color(12, 4, 41, 255)); // Needs to be set on top of the buttons as well
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); // Removes box around clicked button
        button.setFont(new Font("Arial", Font.BOLD, 20)); // Larger text
        button.setBorderPainted(false); // No border

        // Hover over button effect
        button.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                button.setBackground(new Color(12, 4, 90, 255)); // Brighter Blue
            }

            public void mouseExited(MouseEvent e)
            {
                button.setBackground(new Color(12, 4, 41, 255)); // Original Blue
            }
        });
    }

    public void enableGameButtons() {
        hostButton.setEnabled(true);
        joinButton.setEnabled(true);
    }
}
