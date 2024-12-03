import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GameController {
    private Arena arena;
    private Player player;
    private ScoreBoard scoreBoard;
    private Timer gameLoop; // Game loop timer
    private int round;
    private int playerWins;
    private final int MAX_WINS = 3; // Number of wins to end the game
    private CollisionHandler collisionHandler;

    // Constructor to initialize the game
    public GameController(Arena arena, Player player, ScoreBoard scoreBoard) {
        this.arena = arena;
        this.player = player;
        this.scoreBoard = scoreBoard;
        this.round = 1;
        this.playerWins = 0;
        this.collisionHandler = new CollisionHandler(); // Initialize collisionHandler

        // Initialize game loop
        this.gameLoop = new Timer(200, e -> updateGame());
    }

    // Starts the game by resetting positions, clearing the arena, and starting the game loop
    public void startGame() {
        arena.reset(player);
        player.resetPosition(new Position(4, 4), Direction.DOWN);
        gameLoop.start();
    }

    // Updates the game state
    private void updateGame() {
        // Add their segments to their respective trails
        arena.addSegment(player);

        // Move player
        player.move();

        // Check for collisions
        if (collisionHandler.checkCollision(player, arena)) {
            gameLoop.stop(); // Stop the game loop
            updateScore();
        }

        // Repaint the arena
        arena.repaint();
    }

    // Updates the score for the player
    private void updateScore() {
        scoreBoard.incrementScore(player);
        playerWins++;

        // Check if the player has won the game
        if (playerWins == MAX_WINS) {
            endGame();
        } else {
            showCountdownPopup(player.getName() + " wins this round!");
        }
    }

    // Displays a countdown popup with the given message
    private void showCountdownPopup(String message) {
        // Create a modal JDialog
        JDialog dialog = new JDialog((Frame) null, "Next Round", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(null); // Center the dialog

        // Create a panel for vertically centered content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        dialog.add(contentPanel, BorderLayout.CENTER);

        // Create labels for winner, round, and countdown
        JLabel winnerLabel = new JLabel(message, SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel roundLabel = new JLabel("Round " + round + " starts in", SwingConstants.CENTER);
        roundLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        roundLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel countdownLabel = new JLabel("", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 22));
        countdownLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the labels to the panel
        contentPanel.add(Box.createVerticalGlue()); // Add some spacing at the top
        contentPanel.add(winnerLabel);
        contentPanel.add(Box.createVerticalStrut(10)); // Space between winner and round label
        contentPanel.add(roundLabel);
        contentPanel.add(Box.createVerticalStrut(10)); // Space between round label and countdown
        contentPanel.add(countdownLabel);
        contentPanel.add(Box.createVerticalGlue()); // Add some spacing at the bottom

        // Create a countdown timer
        Timer countdownTimer = new Timer(1000, null);
        final int[] countdown = {5}; // Start from 5 seconds
        countdownTimer.addActionListener(e -> {
            if (countdown[0] > 0) {
                countdownLabel.setText(String.valueOf(countdown[0]));
                countdown[0]--;
            } else {
                countdownTimer.stop();
                dialog.dispose(); // Close the dialog when the countdown ends
                startNextRound();
            }
        });

        // Start the timer and show the dialog
        countdownTimer.start();
        dialog.setVisible(true);
    }

    // Starts the next round
    private void startNextRound() {
        round++;
        scoreBoard.updateRound(round);
        startGame();
    }

    // Ends the game and displays the overall winner
    private void endGame() {
        JOptionPane.showMessageDialog(null, player.getName() + " is the winner!");
        resetGame(); // Reset for a new game
    }

    // Resets the game for a new session
    private void resetGame() {
        playerWins = 0;
        scoreBoard.resetScores();
        round = 1;
        scoreBoard.updateRound(round);
        startGame();
    }

    public void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Player controls (WASD)
        switch (keyCode) {
            case KeyEvent.VK_W:
                if (player != null) player.setDirection(Direction.UP);
                break;
            case KeyEvent.VK_A:
                if (player != null) player.setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_S:
                if (player != null) player.setDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_D:
                if (player != null) player.setDirection(Direction.RIGHT);
                break;
        }
    }
}