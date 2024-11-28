package TronGame;

public class GameController {
    private Arena arena;
    private Player player1;
    private Player player2;
    private ScoreBoard scoreBoard;

    // Constructor to initialize the game
    public GameController(Arena arena, Player player1, Player player2, ScoreBoard scoreBoard) {
        this.arena = arena;
        this.player1 = player1;
        this.player2 = player2;
        this.scoreBoard = scoreBoard;
    }

    // Starts the game by resetting positions, clearing the arena, and starting the game loop
    public void startGame() {
        arena.reset();
        player1.resetPosition(new Position(5,5), Direction.DOWN);
        player2.resetPosition(new Position(95,95), Direction.UP);
        scoreBoard.resetScores();
        // Begin the game loop (possibly using a Timer or another thread)
    }

    // Checks if a player collides with walls, themselves, or the other player's trail
    public boolean checkCollision() {
        boolean collision1 = arena.checkCollision(player1.getPosition());
        boolean collision2 = arena.checkCollision(player2.getPosition());

        if (collision1 && collision2) {
            //Implement draw if needed, but would be rare
            return true; // Both players collided
        } else if (collision1) {
            updateScore(player2); // Player 1 loses, Player 2 wins
            return true;
        } else if (collision2) {
            updateScore(player1); // Player 2 loses, Player 1 wins
            return true;
        }
        return false; // No collision
    }

    // Updates the score for the winning player
    public void updateScore(Player winner) {
        scoreBoard.incrementScore(winner);
        startGame(); // Restart the game for the next round
    }

    // Sends data to a server 
    public void sendToServer(Object object) {
        // Implement networking logic here 
    }
}
