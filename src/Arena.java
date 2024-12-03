import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Arena extends JPanel {
    private int width;
    private int height;
    private int cellSize = 6; // Size of each square
    private List<Position> playerTrail = new ArrayList<>(); // Player's trail
    private GameUI gameUI; // Reference to GameUI
    private BufferedImage offScreenImage; // Off-screen image for double buffering
    private Color playerColor; // Color of the player's trail

    public Arena(int width, int height, GameUI gameUI, Color playerColor) {
        this.width = width;
        this.height = height;
        this.gameUI = gameUI;
        this.playerColor = playerColor;

        setPreferredSize(new Dimension(width * cellSize, height * cellSize));
        setMinimumSize(new Dimension(width * cellSize, height * cellSize));
        setDoubleBuffered(true); // Enable double buffering

        // Initialize the off-screen image
        offScreenImage = new BufferedImage(width * cellSize, height * cellSize, BufferedImage.TYPE_INT_ARGB);
    }

    public void setPlayerTrail(List<Position> playerTrail) {
        this.playerTrail = playerTrail;
    }

    // Adds the player's current position to their trail
    public void addSegment(Player player) {
        player.getTrail().add(new Position(player.getPosition().getX(), player.getPosition().getY()));
        gameUI.sendTrailToServer(); // Send trail to server
        updateOffScreenImage();
        repaint();
    }

    // Checks for collisions with walls, trails, or other player's trail
    public boolean checkCollision(Player player, Color otherPlayerColor) {
        Position position = player.getPosition();

        // Check if out of bounds
        if (position.getX() < 0 || position.getX() >= width ||
                position.getY() < 0 || position.getY() >= height) {
            return true;
        }

        // Check for collisions with the trail color
        return isTrailColor(position, playerColor) || isTrailColor(position, otherPlayerColor);
    }

    // Helper to check if the position has the trail color
    private boolean isTrailColor(Position position, Color trailColor) {
        int x = position.getX() * cellSize;
        int y = position.getY() * cellSize;
        int color = offScreenImage.getRGB(x, y);
        return color == trailColor.getRGB();
    }

    // Resets the arena, clearing the player's trail
    public void reset(Player player) {
        playerTrail.clear();
        playerTrail.add(new Position(player.getPosition().getX(), player.getPosition().getY())); // Add starting position
        updateOffScreenImage();
        repaint();
    }

    // Clears the arena
    public void clear() {
        playerTrail.clear();
        updateOffScreenImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw off-screen image
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(offScreenImage, 0, 0, this);
    }

    // Method to update the off-screen image
    public void updateOffScreenImage() {
        Graphics2D g2d = offScreenImage.createGraphics();

        // Enable anti-aliasing for better visual quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Render the grid background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width * cellSize, height * cellSize);

        // Draw the player's trail
        g2d.setColor(playerColor);
        for (Position position : playerTrail) {
            g2d.fillRect(position.getX() * cellSize, position.getY() * cellSize, cellSize, cellSize);
        }

        // Draw grid lines
        g2d.setColor(Color.DARK_GRAY);
        for (int row = 0; row <= height; row++) {
            g2d.drawLine(0, row * cellSize, width * cellSize, row * cellSize);
        }
        for (int col = 0; col <= width; col++) {
            g2d.drawLine(col * cellSize, 0, col * cellSize, height * cellSize);
        }

        g2d.dispose();
    }
}