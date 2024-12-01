import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int score;
    private Position position;
    private Direction direction;
    private List<Position> trail;

    public Player(String name, Position startPosition, Direction startDirection) {
        this.name = name;
        this.position = startPosition;
        this.direction = startDirection;
        this.trail = new ArrayList<>();
        this.trail.add(new Position(startPosition.getX(), startPosition.getY())); // Mark initial position
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
    }

    // Resets the player's position, direction, and trail
    public void resetPosition(Position startPosition, Direction startDirection) {
        this.position = startPosition;
        this.direction = startDirection;
        this.trail.clear();
        this.trail.add(new Position(startPosition.getX(), startPosition.getY()));
    }
}
