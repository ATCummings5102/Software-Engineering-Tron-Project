public class CollisionHandler {

    // Checks if a player collides with walls, themselves, or the other player's trail
    public boolean checkCollision(Player player, Arena arena) {
        boolean collision = arena.checkCollision(player);

        // Check if the player collides with walls or their own trail
        if (collision) {
            return true;
        }

        return false; // No collision
    }
}