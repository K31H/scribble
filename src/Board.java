import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * The Board class represetns the game board for the game Scribble.
 * It handles the stateof the board, player points and game actions 
 * like the word validating and saving/loading the game. 
 * 
 * @author E. Pawulski
 * @version 1.0
 */

public class Board {
    // board 2d array
    private char[][] gameBoard = new char[15][15]; 
    // points for each character played
    private int[] charPoints = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
    // points per player
    private int[] playerPoints = new int[4];

    private static final int winCondition = 100;

    /**
     * The constructor to initialise the game board with blank spaces
     */
    public Board() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = '-';
            }
        }
    }

    /**
     * Displays the current state of the board
     */
    public void displayBoard() {
        System.out.print("\t");
        // prints the column numbers
        for (int i = 0; i < gameBoard.length; i++) {
            System.out.print((i + 1) + "\t");
        }
        System.out.println();
        
        for (int i = 0; i < gameBoard.length; i++) {
            // prints the row numbers 
            System.out.print((i + 1) + "\t");
            for (int j = 0; j < gameBoard[i].length; j++) {
                // prints the current board
                System.out.print(gameBoard[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Adds the word to game board 
     * 
     * @param word the word to add
     * @param x the starting row of the word
     * @param y the starting column of the word
     * @param isHorizontal if the word to be place horizontally true, false if vertically
     * @param playerIndex the counter for the player whos move it is
     * @param firstMove true if it is the first move, false otherwise
     * @return true if the word was place on the board, false otherwise
     */
    public boolean addWord(String word, int x, int y, boolean isHorizontal, int playerIndex, boolean firstMove) {
        // character array to store the word entered and captialised 
        char[] wordArray = word.toUpperCase().toCharArray();

        // if it is the first move
        if (firstMove) {
            // horizontal
            if (isHorizontal && firstMoveValidation(word, x, y, isHorizontal)) {
                for (int i = 0; i < word.length(); i++) {
                    gameBoard[y][x + i] = wordArray[i];
                }
            // vertical
            } else if (!isHorizontal && firstMoveValidation(word, x, y, isHorizontal)) {
                for (int j = 0; j < word.length(); j++) {
                    gameBoard[y + j][x] = wordArray[j];
                }
            } else {
                return false; // Invalid first move
            }
        // every other move
        } else {
            // horizontal
            if (isHorizontal && validMove(word, x, y, isHorizontal)) {
                for (int i = 0; i < word.length(); i++) {
                    gameBoard[y][x + i] = wordArray[i];
                }
            // vertical
            } else if (!isHorizontal && validMove(word, x, y, isHorizontal)) {
                for (int j = 0; j < word.length(); j++) {
                    gameBoard[y + j][x] = wordArray[j];
                }
            } else {
                return false; // Invalid move
            }
        }
        // calculates points for the word
        calcPoints(word, playerIndex);
        return true; 
    }

    /**
     * Validates the first move to make sure its in the center of the board (7,7)
     * 
     * @param word word to validate
     * @param x the starting row of the word
     * @param y the starting column of the word
     * @param isHorizontal if the word to be place horizontally true, false if vertically
     * @return true if the word is inside the board and is in the center, otherwise false
     */
    public boolean firstMoveValidation(String word, int x, int y, boolean isHorizontal) {
        // if the word is outside the board horizontally
        if (isHorizontal) {
            if ((x + word.length() > 15) || y >= 15) {
                return false;
            }
        // if the word is outside the board vertically 
        } else {
            if (y + word.length() > 15 || x >= 15) {
                return false;
            }
        }

        for (int i = 0; i < word.length(); i++) {
            int row;
            int col;

            if (isHorizontal) {
                // if horizontal increase the row number
                row = x + i;
                col = y;
            } else {
                // if vertical increase the column number
                row = x;
                col = y + i;
            }

            // if any letter is in the middle
            if (row == 7 && col == 7) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates all other move to make sure it on the board and placed where it intersects with another word
     * 
     * @param word word to validate
     * @param x the starting row of the word
     * @param y the starting column of the word
     * @param isHorizontal if the word to be place horizontally true, false if vertically
     * @return true if the word is inside the board and place correctly, otherwise false
     */
    public boolean validMove(String word, int x, int y, boolean isHorizontal) {
        // if the word is outside the board horizontally
        if (isHorizontal) {
            if ((x + word.length() > 15) || y >= 15) {
                return false;
            }
        // if the word is outside the board vertically 
        } else {
            if (y + word.length() > 15 || x >= 15) {
                return false;
            }
        }

        for (int i = 0; i < word.length(); i++) {
            int row;
            int col;

            if (isHorizontal) {
                // if horizontal increase the row number
                row = x + i;
                col = y;
            } else {
                // if vertical increase the column number
                row = x;
                col = y + i;
            }

            // if the letter position is not empty or is not equal a letter in that position
            if (gameBoard[col][row] != '-' && gameBoard[col][row] != word.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calculates the points total of the word and adds it to the player that played the word
     * 
     * @param word the word that was placed
     * @param playerIndex the player that played the word
     */
    public void calcPoints(String word, int playerIndex) {
        // loops through the word
        for (int i = 0; i < word.length(); i++) {
            // get ascii value of the letter and subtracts 65 ('A')
            // so that a is equal to 0
            char ch = word.toUpperCase().charAt(i);
            int ascii = ch - 'A';
            // get the position of the points array
            playerPoints[playerIndex] += charPoints[ascii];
        }
    }

    /**
     * Displays the points table with the player and their points
     */
    public void pointTable() {
        // points table to display players and points
        for (int i = 0; i < playerPoints.length; i++) {
            System.out.println("Player " + (i + 1) + " points: " + playerPoints[i]);
        }
    }

    /**
     * Checks if a player has met the win condition
     * 
     * @param playerIndex the player that has just played
     * @return true if the player has met the conditions, otehrwise false
     */
    public boolean checkWin(int playerIndex) {
        if (playerPoints[playerIndex] >= winCondition) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Saves the current state of the game to a file
     * 
     * @param fileName the name of the file to save to
     * @param currentPlayer the player to move next
     * @param firstMove true if the first word has not be played
     */

    public void saveGame(String fileName, int currentPlayer, boolean firstMove) {
        FileOutputStream outputStream = null;
        PrintWriter printWriter = null;
    
        try {
            outputStream = new FileOutputStream(fileName);
            printWriter = new PrintWriter(outputStream);
    
            // Save the game board
            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard[i].length; j++) {
                    printWriter.print(gameBoard[i][j] + ", ");
                }   
            }
            printWriter.println();
    
            // Save player points
            for (int i = 0; i < playerPoints.length; i++) {
                printWriter.print(playerPoints[i] + ", ");
            }
            printWriter.println();
    
            // Save current player's move
            printWriter.println(currentPlayer);
    
            // Save if the first move has been played
            printWriter.println(firstMove);
    
            System.out.println("Game saved to " + fileName);
    
        } catch (IOException e) {
            System.out.println("An error occurred while saving the game: " + e.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.out.println("Error closing file: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Quits the game after saving the game state
     * 
     * @param fileName the name of the file to save to
     * @param currentPlayer the player to move next
     * @param firstMove true if the first word has not be played
     */
    public void quitGame(String fileName, int currentPlayer, boolean firstMove) {
        saveGame(fileName, currentPlayer, firstMove);
        System.out.println("Quitting the game...");
        // stops the entire code
        System.exit(0);
    }

    /**
     * Load the game state from a file
     * 
     * @param fileName the name of the file containing the game state
     * @return a object containing the loaded state of the game
     */
    public GameState load(String fileName) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        boolean firstMoveLoaded = false;
        int currentPlayerLoaded = 0;
    
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
    
            // Load game board
            String nextLine = bufferedReader.readLine();
            String[] boardData = nextLine.split(", ");
            int index = 0;

            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard[i].length; j++) {
                    gameBoard[i][j] = boardData[index].charAt(0);
                    index++;
                }
            }
    
            // Load player points
            nextLine = bufferedReader.readLine();
            String[] pointsData = nextLine.split(", ");
            for (int i = 0; i < playerPoints.length; i++) {
                playerPoints[i] = Integer.parseInt(pointsData[i]);
            }
    
            // Load current player's move
            nextLine = bufferedReader.readLine();
            int currentPlayer = Integer.parseInt(nextLine);
            currentPlayerLoaded = currentPlayer;
    
            // Loads if the first move has been played
            nextLine = bufferedReader.readLine();
            boolean firstMove = Boolean.parseBoolean(nextLine);
            firstMoveLoaded = firstMove;
    
            System.out.println("Game loaded from " + fileName);
    
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);

        } catch (IOException e) {
            System.out.println("Error opening/reading file: ");

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("Error closing file: ");
                }
            }
        }
        // return both a boolean and integer via new class
        return new GameState(firstMoveLoaded, currentPlayerLoaded);
    }
    
}
