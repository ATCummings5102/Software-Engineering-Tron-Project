package TronGame;

import javax.swing.*;
import java.awt.*;

public class ScoreBoard extends JPanel {
    private Player player1;
    private Player player2;
    private JLabel titleLabel;
    private JLabel player1Label;
    private JLabel player2Label;
    
    public ScoreBoard(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        
        setLayout(new GridLayout(3, 1)); // 3 rows: Title, Player 1, Player 2
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
    }
    
    //Not sure if needed honestly
    public int getPlayer1Score() {
        return player1.getScore();
    }
    
    public int getPlayer2Score() {
        return player2.getScore();
    }
    
    public void incrementScore(Player winner) {
        winner.setScore(winner.getScore() + 1);
    }
    
    public void resetScores() {
        player1.setScore(0);
        player2.setScore(0);
    }
    
    public void displayScore() {
        //Implement when ScoreBoard is created
    }
    
 // Update the labels based on the current scores
    public void updateScores() {
        updatePlayer1Label();
        updatePlayer2Label();
    }

    private void updatePlayer1Label() {
        player1Label.setText(player1.getName() + ": " + player1.getScore());
    }

    private void updatePlayer2Label() {
        player2Label.setText(player2.getName() + ": " + player2.getScore());
    }
}
