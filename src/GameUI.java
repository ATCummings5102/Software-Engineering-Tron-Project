import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameUI extends JPanel {

    private Player player1;
    private Player player2;
    private Player Player;
    private Arena arena;
    private ScoreBoard scoreBoard;
    private GameController gameController;
    private CollisionHandler collisionHandler;
    private final int arenaWidth;
    private final int arenaHeight;

    // Constructor to initialize the game with a dynamic arena size
    public GameUI(int arenaWidth, int arenaHeight)
    {
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
            this.Player = player;
            arena.setPlayerTrail(Player.getTrail());
            scoreBoard.setPlayer1(Player);
            createGameController();
    }

    private void createArena() {
        arena = new Arena(arenaWidth, arenaHeight);
    }

    private void createScoreboard() {
        scoreBoard = new ScoreBoard();
    }

    private void createGameController() {
        gameController = new GameController(arena, Player, scoreBoard);
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
