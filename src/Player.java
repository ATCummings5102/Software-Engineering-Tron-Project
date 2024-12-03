import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.Color;

public class Player implements Serializable {
    private String name;
    private int score;
    private Position position;
    private Direction direction;
    private List<Position> trail;
    private String playerLabel;
    private int ID;
    private Color trailColor;

    public Player(String name, int ID, Color trailColor) {
        this.name = name;
        this.ID = ID;
        this.trailColor = trailColor;
        this.trail = new CopyOnWriteArrayList<>();
    }

    public Player(String name, Position startPosition, Direction startDirection, Color trailColor) {
        this.name = name;
        this.position = startPosition;
        this.direction = startDirection;
        this.trailColor = trailColor;
        this.trail = new CopyOnWriteArrayList<>();
        this.trail.add(new Position(startPosition.getX(), startPosition.getY())); // Mark initial position
        this.ID = generateUniqueID();
    }

    private int generateUniqueID() {
        return (int) (System.currentTimeMillis() & 0xfffffff);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public List<Position> getTrail() {
        return trail;
    }

    public Color getTrailColor() {
        return trailColor;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    // Moves the player in their current direction
    public void move() {
        switch (direction) {
            case UP -> position = new Position(position.getX(), position.getY() - 1);
            case DOWN -> position = new Position(position.getX(), position.getY() + 1);
            case LEFT -> position = new Position(position.getX() - 1, position.getY());
            case RIGHT -> position = new Position(position.getX() + 1, position.getY());
        }
        trail.add(new Position(position.getX(), position.getY()));
    }

    // Resets the player's position, direction, and trail
    public void resetPosition(Position startPosition, Direction startDirection) {
        this.position = startPosition;
        this.direction = startDirection;
        this.trail.clear();
        this.trail.add(new Position(startPosition.getX(), startPosition.getY()));
    }

    // Checks if the player collides with their own trail
    public boolean checkSelfCollision() {
        for (int i = 0; i < trail.size() - 1; i++) {
            if (trail.get(i).equals(position)) {
                return true;
            }
        }
        return false;
    }

    // Checks if the player collides with the game board boundaries
    public boolean checkBoundaryCollision(int boardWidth, int boardHeight) {
        return position.getX() < 0 || position.getX() >= boardWidth || position.getY() < 0 || position.getY() >= boardHeight;
    }
}