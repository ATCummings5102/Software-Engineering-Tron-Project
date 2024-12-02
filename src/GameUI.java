import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameUI extends JPanel {

    private Player player1;
    private Player player2;
    private Arena arena;
    private ScoreBoard scoreBoard;
    private GameController gameController;
    private final int arenaWidth = 100;
    private final int arenaHeight = 100;

    private void setPlayer(Player player)
    {
        if(player1 == null)
        {
            player1 = player;
            arena.setPlayer1Trail(player1.getTrail());
            scoreBoard.setPlayer1(player1);
        }
        else if(player2 == null)
        {
            player2 = player;
            arena.setPlayer2Trail(player2.getTrail());
            scoreBoard.setPlayer2(player2);
        }
        else
        {
            System.out.println("Max amount of players reached");
        }
    }

    private void createArena()
    {
        // Create the arena
        arena = new Arena(arenaWidth, arenaHeight);
    }

    private void createScoreboard()
    {
        // Create the scoreboard
        scoreBoard = new ScoreBoard();
    }

    public GameUI()
    {
        createArena();
        createScoreboard();

        if(player1 != null && player2 != null)
        {
            // Create the game controller
            gameController = new GameController(arena, player1, player2, scoreBoard);
            // Start the game
            gameController.startGame();
        }

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

    }

}
