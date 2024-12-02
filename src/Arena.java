import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Arena extends JPanel {
    private int width;
    private int height;
    private int cellSize = 6; // Size of each square
    private List<Position> player1Trail; // Player 1's trail
    private List<Position> player2Trail; // Player 2's trail

    public Arena(int width, int height, List<Position> player1Trail, List<Position> player2Trail) {
        this.width = width;
        this.height = height;
        this.player1Trail = player1Trail;
        this.player2Trail = player2Trail;

        setPreferredSize(new Dimension(width * cellSize, height * cellSize));
        setMinimumSize(new Dimension(width * cellSize, height * cellSize));
    }

    // Adds the player's current position to their trail
    public void addSegment(Player player) {
        player.getTrail().add(new Position(player.getPosition().getX(), player.getPosition().getY()));
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

        // Check for collisions with any trail
        return isPositionInTrail(position, player1Trail) || isPositionInTrail(position, player2Trail);
    }

    // Helper to check if a position is in a given trail
    private boolean isPositionInTrail(Position position, List<Position> trail) {
        return trail.stream().anyMatch(p -> p.getX() == position.getX() && p.getY() == position.getY());
    }

    // Resets the arena, clearing both players' trails
    public void reset(Player player1, Player player2) {
        player1Trail.clear();
        player2Trail.clear();
        player1Trail.add(new Position(player1.getPosition().getX(), player1.getPosition().getY())); // Add starting position
        player2Trail.add(new Position(player2.getPosition().getX(), player2.getPosition().getY())); // Add starting position
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render the grid background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width * cellSize, height * cellSize);

        // Render Player 1's trail
        g.setColor(Color.BLUE);
        for (Position position : player1Trail) {
            g.fillRect(position.getX() * cellSize, position.getY() * cellSize, cellSize, cellSize);
        }

        // Render Player 2's trail
        g.setColor(Color.YELLOW);
        for (Position position : player2Trail) {
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