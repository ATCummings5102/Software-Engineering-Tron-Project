import java.awt.Color;

public class CollisionHandler {

    // Checks if a player collides with walls, themselves, or the other player's trail
    public boolean checkCollision(Player player, Arena arena, Color otherPlayerColor) {
        return arena.checkCollision(player, otherPlayerColor);
    }
}