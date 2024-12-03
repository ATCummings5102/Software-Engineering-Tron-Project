import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ScoreBoard extends JPanel implements Serializable {
    private Player player1;
    private Player player2;
    private JLabel titleLabel;
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel roundLabel;

    public ScoreBoard() {
        setLayout(new GridLayout(4, 1)); // 4 rows: Title, Player 1, Player 2, Round
        setPreferredSize(new Dimension(200, 0)); // Fixed width, height adjusts dynamically

        // Create title label
        titleLabel = new JLabel("Score Board", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);

        // Create player 1 label
        player1Label = new JLabel("", SwingConstants.CENTER);
        player1Label.setFont(new Font("Arial", Font.PLAIN, 16));
        updatePlayer1Label();
        add(player1Label);

        // Create player 2 label
        player2Label = new JLabel("", SwingConstants.CENTER);
        player2Label.setFont(new Font("Arial", Font.PLAIN, 16));
        updatePlayer2Label();
        add(player2Label);

        // Create round label
        roundLabel = new JLabel("Round: 1", SwingConstants.CENTER);
        roundLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(roundLabel);
    }

    public void setPlayer1(Player player1)
    {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2)
    {
        this.player2 = player2;
    }

    public void incrementScore(Player winner) {
        winner.setScore(winner.getScore() + 1);
        updateScores();
    }

    public void resetScores() {
        player1.setScore(0);
        player2.setScore(0);
        updateScores();
    }

    // Update the labels based on the current scores
    public void updateScores() {
        updatePlayer1Label();
        updatePlayer2Label();
    }

    private void updatePlayer1Label()
    {
        if(player1 == null)
        {
            player1Label.setText("Player1: ..........." + ": " + "Score: ............");
        }
        else
        {
            player1Label.setText(player1.getName() + ": " + player1.getScore());
        }
    }

    private void updatePlayer2Label()
    {
        if(player2 == null)
        {
            player1Label.setText("Player1: ..........." + ": " + "Score: ............");
        }
        else
        {
            player2Label.setText(player2.getName() + ": " + player2.getScore());
        }
    }

    public void updateRound(int round) {
        roundLabel.setText("Round: " + round);
    }
}