package TronGame;

import javax.swing.*;
import java.awt.*;

public class GameUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tron Game");

        // Create the arena
        Arena arena = new Arena(100, 100);

        // Create players and scoreboard
        Player player1 = new Player("Player 1", new Position(5, 5), Direction.RIGHT);
        Player player2 = new Player("Player 2", new Position(95, 95), Direction.LEFT);
        ScoreBoard scoreBoard = new ScoreBoard(player1, player2);

        // Set up the layout
        frame.setLayout(new BorderLayout());
        frame.add(arena, BorderLayout.CENTER);
        frame.add(scoreBoard, BorderLayout.EAST);

        // Configure frame
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}