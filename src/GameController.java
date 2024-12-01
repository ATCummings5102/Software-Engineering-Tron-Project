import javax.swing.*;
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
        player1.resetPosition(new Position(4, 4), player1.getDirection());
        player2.resetPosition(new Position(95, 95), player2.getDirection());
        scoreBoard.resetScores();

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

        if (collision1 && collision2) {
            JOptionPane.showMessageDialog(null, "It's a draw!");
            return true; // Both players collided
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
        //JOptionPane.showMessageDialog(null, winner.getName() + " wins this round!");

        // Increment the winner's win count
        if (winner == player1) {
            player1Wins++;
        } else {
            player2Wins++;
        }

        // Update the scoreboard
        scoreBoard.incrementScore(winner);

        // Check if a player has won 3 rounds
        if (player1Wins == MAX_WINS || player2Wins == MAX_WINS) {
            endGame();
        } else {
            // Start a new round
            round++;
            startGame();
        }
    }
    
    // Ends the game and displays the overall winner
    private void endGame() {
        String overallWinner = (player1Wins == MAX_WINS) ? player1.getName() : player2.getName();
        JOptionPane.showMessageDialog(null, overallWinner + " is the overall winner!");
        resetGame(); // Reset for a new game
    }

    // Resets the game for a new session
    private void resetGame() {
        player1Wins = 0;
        player2Wins = 0;
        scoreBoard.resetScores();
        startGame();
    }

    // Sends data to a server 
    public void sendToServer(Object object) {
        // Implement networking logic here 
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
