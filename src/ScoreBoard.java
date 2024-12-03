import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class ScoreBoard extends JPanel implements Serializable {
    private Player player;
    private JLabel titleLabel;
    private JLabel playerLabel;
    private JLabel roundLabel;

    public ScoreBoard() {
        setLayout(new GridLayout(3, 1)); // 3 rows: Title, Player, Round
        setPreferredSize(new Dimension(200, 0)); // Fixed width, height adjusts dynamically

        // Create title label
        titleLabel = new JLabel("Score Board", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);

        // Create player label
        playerLabel = new JLabel("", SwingConstants.CENTER);
        playerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        updatePlayerLabel();
        add(playerLabel);

        // Create round label
        roundLabel = new JLabel("Round: 1", SwingConstants.CENTER);
        roundLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(roundLabel);
    }

    public void setPlayer(Player player) {
        this.player = player;
        updatePlayerLabel();
    }

    public void incrementScore(int playerId) {
        if (player != null && player.getID() == playerId) {
            player.setScore(player.getScore() + 1);
            updateScores();
        }
    }

    public void resetScores() {
        if (player != null) {
            player.setScore(0);
            updateScores();
        }
    }

    // Update the label based on the current score
    public void updateScores() {
        updatePlayerLabel();
    }

    private void updatePlayerLabel() {
        if (player == null) {
            playerLabel.setText("Player: ........... Score: ............");
        } else {
            playerLabel.setText(player.getName() + ": " + player.getScore());
        }
    }

    public void updateRound(int round) {
        roundLabel.setText("Round: " + round);
    }
}