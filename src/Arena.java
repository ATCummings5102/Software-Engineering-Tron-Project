import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Arena extends JPanel {
    private int width;
    private int height;
    private int cellSize = 6; // Size of each square
    private List<Position> playerTrail = new ArrayList<>(); // Player's trail
    private GameUI gameUI; // Reference to GameUI

    public Arena(int width, int height, GameUI gameUI) {
        this.width = width;
        this.height = height;
        this.gameUI = gameUI;

        setPreferredSize(new Dimension(width * cellSize, height * cellSize));
        setMinimumSize(new Dimension(width * cellSize, height * cellSize));
    }

    public void setPlayerTrail(List<Position> playerTrail) {
        this.playerTrail = playerTrail;
    }

    // Adds the player's current position to their trail
    public void addSegment(Player player) {
        player.getTrail().add(new Position(player.getPosition().getX(), player.getPosition().getY()));
        gameUI.sendTrailToServer(); // Send trail to server
        repaint();
    }

    // Checks for collisions with walls or trails
    public boolean checkCollision(Player player) {
        Position position = player.getPosition();

        // Check if out of bounds
        if (position.getX() < 0 || position.getX() >= width ||
                position.getY() < 0 || position.getY() >= height) {
            return true;
        }

        // Check for collisions with the player's trail
        return isPositionInTrail(position, playerTrail);
    }

    // Helper to check if a position is in a given trail
    private boolean isPositionInTrail(Position position, List<Position> trail) {
        return trail.stream().anyMatch(p -> p.getX() == position.getX() && p.getY() == position.getY());
    }

    // Resets the arena, clearing the player's trail
    public void reset(Player player) {
        playerTrail.clear();
        playerTrail.add(new Position(player.getPosition().getX(), player.getPosition().getY())); // Add starting position
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render the grid background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width * cellSize, height * cellSize);

        g.setColor(Color.GREEN);
        for (Position position : playerTrail) {
            g.fillRect(position.getX() * cellSize, position.getY() * cellSize, cellSize, cellSize);
        }

        // Draw grid lines
        g.setColor(Color.DARK_GRAY);
        for (int row = 0; row <= height; row++) {
            g.drawLine(0, row * cellSize, width * cellSize, row * cellSize);
        }
        for (int col = 0; col <= width; col++) {
            g.drawLine(col * cellSize, 0, col * cellSize, height * cellSize);
        }
    }
}