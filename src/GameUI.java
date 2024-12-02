import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameUI extends JPanel {

    private Player player1;
    private Player player2;
    private Arena arena;
    private ScoreBoard scoreBoard;
    private GameController gameController;
    private final int arenaWidth;
    private final int arenaHeight;

    // Constructor to initialize the game with a dynamic arena size
    public GameUI(int arenaWidth, int arenaHeight) {
        this.arenaWidth = arenaWidth;
        this.arenaHeight = arenaHeight;

        // Initialize components
        createArena();
        createScoreboard();

        // Set up layout and listeners
        setupLayout();
        setupKeyListener();

        // Make panel focusable for key events
        this.setVisible(true);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void setPlayer(Player player) {
        if (player1 == null) {
            player1 = player;
            arena.setPlayer1Trail(player1.getTrail());
            scoreBoard.setPlayer1(player1);
        } else if (player2 == null) {
            player2 = player;
            arena.setPlayer2Trail(player2.getTrail());
            scoreBoard.setPlayer2(player2);
        } else {
            throw new IllegalStateException("Maximum number of players reached. Only two players are allowed.");
        }

        // Create the game controller when both players are set
        if (player1 != null && player2 != null) {
            createGameController();
        }
    }

    private void createArena() {
        arena = new Arena(arenaWidth, arenaHeight);
    }

    private void createScoreboard() {
        scoreBoard = new ScoreBoard();
    }

    private void createGameController() {
        gameController = new GameController(arena, player1, player2, scoreBoard);
        gameController.startGame();
    }

    private void setupLayout() {
        this.setLayout(new BorderLayout());
        this.add(arena, BorderLayout.CENTER);
        this.add(scoreBoard, BorderLayout.EAST);
    }

    private void setupKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameController != null) {
                    gameController.handleKeyPress(e);
                    repaint();
                }
            }
        });

        // Ensure focus remains on the game panel
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                requestFocusInWindow();
            }
        });
    }
}
