package TronGame;

import javax.swing.*;
import java.awt.*;

public class Arena extends JPanel {
    private int width;
    private int height;
    private Trail[][] grid; // Represents the trails left by players
    private int cellSize = 6; // Size of each square

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Trail[height][width];
        setPreferredSize(new Dimension(width * cellSize, height * cellSize));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void reset() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = null;
            }
        }
        repaint(); // Refresh the visual grid
    }

    public void addSegment(Player player, Position position) {
        grid[position.getY()][position.getX()] = new Trail(player.getName(), position);
        repaint();
    }

    public boolean checkCollision(Position position) {
        if (position.getX() < 0 || position.getX() >= width || 
            position.getY() < 0 || position.getY() >= height) {
            return true; // Out of bounds
        }
        return grid[position.getY()][position.getX()] != null; // Collision with trail
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render the grid
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (grid[row][col] == null) {
                    g.setColor(Color.BLACK); // Empty cell
                } else {
                    g.setColor(Color.BLUE); // Example: Use player-specific color later
                }
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
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
