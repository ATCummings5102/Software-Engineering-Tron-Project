import java.io.Serializable;
import java.util.List;

public class PlayerTrailData implements Serializable {
    private String playerName;
    private List<Position> playerTrail;

    public PlayerTrailData(String playerName, List<Position> playerTrail) {
        this.playerName = playerName;
        this.playerTrail = playerTrail;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Position> getPlayerTrail() {
        return playerTrail;
    }
}