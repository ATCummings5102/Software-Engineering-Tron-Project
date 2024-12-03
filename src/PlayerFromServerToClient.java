import java.util.ArrayList;

public class PlayerFromServerToClient
{
    private ArrayList<Player> players = new ArrayList<>();
    private Player tempPlayer;


    public PlayerFromServerToClient(Player player)
    {
        tempPlayer = player;
        players.add(player);
    }

    protected Player getPlayer(int index)
    {
        return players.get(index);
    }
}
