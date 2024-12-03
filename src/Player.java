import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private String name;
    private int score;
    private Position position;
    private Direction direction;
    private List<Position> trail;
    private String playerLabel;
    private static int idAssigned = 0;
    private int ID;

    public Player(String name, Position startPosition, Direction startDirection) {
        this.name = name;
        this.position = startPosition;
        this.direction = startDirection;
        this.trail = new ArrayList<>();
        this.trail.add(new Position(startPosition.getX(), startPosition.getY())); // Mark initial position
        ID = setID();
    }

    public int setID() {
        if (idAssigned == 0) {
            idAssigned = 1;  // Assign ID 1 to the first player
            return 1;
        } else {
            return 0;  // The second player gets ID 0
        }
    }

    public int getID(String name)
    {
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