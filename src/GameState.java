/**
 * The GameState class is used to store the information about saved game state
 * by storing informatino about the first move and the current player 
 * 
 * @author E. Pawulski
 * @version 1.0
 */
public class GameState {
    // new class to specifically to return 2 data types (boolean and integer)
    public boolean firstMove;
    public int currentPlayer;

    /**
     * Constructor the initailise the game state with the first move status and the current player
     *  
     * @param firstMove indicates if it the first move of the game
     * @param currentPlayer the player that is about to play
     */
    public GameState(boolean firstMove, int currentPlayer) {
        this.firstMove = firstMove;
        this.currentPlayer = currentPlayer;
    }
}
