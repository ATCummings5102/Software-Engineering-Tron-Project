import java.io.Serializable;

public class Trail implements Serializable {
    private String playerName;
    private Position position;
    
    public Trail(String playerName, Position position) {
        this.playerName = playerName;
        this.position = position;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public Position getPosition() {
        return position;
    }
}