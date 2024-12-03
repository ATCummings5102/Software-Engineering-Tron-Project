import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class GameUI extends JPanel {
    private Player player;
    private List<Position> playerTrail;
    private Arena arena;
    private ScoreBoard scoreBoard;
    private GameController gameController;
    private final int arenaWidth;
    private final int arenaHeight;
    private ClientGUI clientGUI;

    public GameUI(int arenaWidth, int arenaHeight, ClientGUI clientGUI) {
        this.arenaWidth = arenaWidth;
        this.arenaHeight = arenaHeight;
        this.clientGUI = clientGUI;

        createArena(); // Initialize arena before setting up layout
        createScoreboard();
        setupLayout();
        setupKeyListener();

        this.setVisible(true);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void setPlayer(Player player) {
        this.player = player;
        arena.setPlayerTrail(player.getTrail());
        scoreBoard.setPlayer(player);
        createGameController();
    }

    public void sendTrailToServer() {
        if (player != null) {
            playerTrail = player.getTrail();
            PlayerTrailData data = new PlayerTrailData(player.getName(), playerTrail);
            clientGUI.sendToServer(data);
        }
    }

    private void createArena() {
        if (player != null) {
            Color playerColor = player.getTrailColor(); // Get the player's trail color
            arena = new Arena(arenaWidth, arenaHeight, this, playerColor);
        } else {
            System.out.println("Player is not initialized.");
            arena = new Arena(arenaWidth, arenaHeight, this, Color.GRAY); // Default color if player is not initialized
        }
    }

    private void createScoreboard() {
        scoreBoard = new ScoreBoard();
    }

    private void createGameController() {
        gameController = new GameController(arena, player, scoreBoard);
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

        this.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                requestFocusInWindow();
            }
        });
    }
}