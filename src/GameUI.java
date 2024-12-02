import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameUI extends JPanel {

    public GameUI() {

        // Create player trails
        int arenaWidth = 100;
        int arenaHeight = 100;

        // Create players
        Player player1 = new Player("Player 1", new Position(4, 4), Direction.RIGHT);
        Player player2 = new Player("Player 2", new Position(95, 95), Direction.LEFT);

        // Create the arena
        Arena arena = new Arena(arenaWidth, arenaHeight, player1.getTrail(), player2.getTrail());

        // Create the scoreboard
        ScoreBoard scoreBoard = new ScoreBoard(player1, player2);

        // Create the game controller
        GameController gameController = new GameController(arena, player1, player2, scoreBoard);

        // Set up the layout
        this.setLayout(new BorderLayout());
        this.add(arena, BorderLayout.CENTER);
        this.add(scoreBoard, BorderLayout.EAST);

        this.setVisible(true);

        // Ensure the panel is focusable
        this.setFocusable(true);
        this.requestFocusInWindow();

        // Add key listener to capture WASD (Player 1) and Arrow keys (Player 2)
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not used
            }

            @Override
            public void keyPressed(KeyEvent e) {
                gameController.handleKeyPress(e);
                // Repaint the UI to reflect changes
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not used
            }
        });

        // Start the game
        gameController.startGame();
    }
}
