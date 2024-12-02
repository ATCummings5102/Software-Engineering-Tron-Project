import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GameController {
    private Arena arena;
    private Player player1;
    private Player player2;
    private ScoreBoard scoreBoard;
    private Timer gameLoop; // Game loop timer
    private int round;
    private int player1Wins;
    private int player2Wins;
    private final int MAX_WINS = 3; // Number of wins to end the game

    // Constructor to initialize the game
    public GameController(Arena arena, Player player1, Player player2, ScoreBoard scoreBoard) {
        this.arena = arena;
        this.player1 = player1;
        this.player2 = player2;
        this.scoreBoard = scoreBoard;
        this.round = 1;
        this.player1Wins = 0;
        this.player2Wins = 0;

        // Initialize game loop
        this.gameLoop = new Timer(200, e -> updateGame());
    }

    // Starts the game by resetting positions, clearing the arena, and starting the game loop
    public void startGame() {
        arena.reset(player1, player2);
        player1.resetPosition(new Position(4, 4), Direction.DOWN);
        player2.resetPosition(new Position(95, 95), Direction.LEFT);
        this.gameLoop = new Timer(200, e -> updateGame());
        gameLoop.start();
    }

    // Updates the game state
    private void updateGame() {
        // Add their segments to their respective trails
        arena.addSegment(player1);
        arena.addSegment(player2);

        // Move players
        player1.move();
        player2.move();

        // Check for collisions
        if (checkCollision()) {
            gameLoop.stop(); // Stop the game loop
        }

        // Repaint the arena
        arena.repaint();
    }

    // Checks if a player collides with walls, themselves, or the other player's trail
    private boolean checkCollision() {
        boolean collision1 = arena.checkCollision(player1);
        boolean collision2 = arena.checkCollision(player2);

        // Check if both players occupy the same position
        if (player1.getPosition().getX() == (player2.getPosition().getX()) && player1.getPosition().getY() == (player2.getPosition().getY())) {
            showCountdownPopup("It's a draw!");
            return true; // Both players collided together
        }

        if (collision1 && collision2) {
            showCountdownPopup("It's a draw!");
            return true; // Both players collided with trails or walls
        } else if (collision1) {
            updateScore(player2); // Player 1 loses, Player 2 wins
            return true;
        } else if (collision2) {
            updateScore(player1); // Player 2 loses, Player 1 wins
            return true;
        }
        return false; // No collision
    }

    // Updates the score for the winning player
    private void updateScore(Player winner) {
        scoreBoard.incrementScore(winner);
        if (winner == player1) {
            player1Wins++;
        } else {
            player2Wins++;
        }

        // Check if a player has won the game
        if (player1Wins == MAX_WINS || player2Wins == MAX_WINS) {
            endGame();
        } else {
            showCountdownPopup(winner.getName() + " wins this round!");
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
        String overallWinner = (player1Wins == MAX_WINS) ? player1.getName() : player2.getName();
        JOptionPane.showMessageDialog(null, overallWinner + " is the winner!");
        resetGame(); // Reset for a new game
    }

    // Resets the game for a new session
    private void resetGame() {
        player1Wins = 0;
        player2Wins = 0;
        scoreBoard.resetScores();
        round = 1;
        scoreBoard.updateRound(round);
        startGame();
    }

    public void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Player 1 controls (WASD)
        if (keyCode == KeyEvent.VK_W) {
            player1.setDirection(Direction.UP);
        } else if (keyCode == KeyEvent.VK_A) {
            player1.setDirection(Direction.LEFT);
        } else if (keyCode == KeyEvent.VK_S) {
            player1.setDirection(Direction.DOWN);
        } else if (keyCode == KeyEvent.VK_D) {
            player1.setDirection(Direction.RIGHT);
        }

        // Player 2 controls (Arrow Keys)
        if (keyCode == KeyEvent.VK_UP) {
            player2.setDirection(Direction.UP);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            player2.setDirection(Direction.LEFT);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            player2.setDirection(Direction.DOWN);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            player2.setDirection(Direction.RIGHT);
        }
    }
}