import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The Game class handles the main game loop and interaction
 * 
 * @author E. Pawulski
 * @version 1.0
 */

public class Game {
    private boolean firstMove = true;
    private Scanner s = new Scanner(System.in);
    private Board board = new Board();
    private HashMap<String, Boolean> dictionary = new HashMap<>();

    int currentPlayer = 1;
    String fileName = "scribbleSave.txt";

    /**
     * Contructor for loading the dictionary
     */
    public Game() {
        loadDictionary();
    }

    /**
     * Main game loop that continues until a player wins or quits
     */
    public void gameLoop() {
        while (true) {
            boolean validMove = false;

            System.out.println("Would you like to play or quit");

            String command = s.nextLine().trim().toLowerCase();

            if (command.equals("play")) {
                // Clear the console at the start of each turn
                clearConsole();

                // Display the table
                board.pointTable();
                System.out.println();

                // Display the game board
                board.displayBoard();

                // Inputs for the words
                System.out.println("Player " + currentPlayer + " turn. Enter a word:");
                String turnMove = s.nextLine().toUpperCase();

                // Is the word in the dictionary
                if (isWordValid(turnMove)) {
                    System.out.println("What column?");
                    int turnX = s.nextInt() - 1;

                    System.out.println("What row?");
                    int turnY = s.nextInt() - 1;

                    System.out.println("Horizontal? (true or false)?");
                    boolean turnDirection = s.nextBoolean();
                    s.nextLine();

                    validMove = board.addWord(turnMove, turnX, turnY, turnDirection, currentPlayer - 1, firstMove);

                    // If word validation fails
                    if (!validMove) {
                        System.out.println("Invalid move. Try again.");
                    } else {
                        // check if player has won
                        if (board.checkWin(currentPlayer-1)) {
                            System.out.println("Player " + currentPlayer + "wins!");
                            board.displayBoard();
                            board.pointTable();
                            break;
                        }

                        // Cycles between each player
                        currentPlayer = (currentPlayer % 4) + 1;
                    
                        if (firstMove) {
                            firstMove = false;
                        }
                    }

                } else {
                    System.out.println("The word entered is not in the dictionary. Try again.");
                }
                
            } else if (command.equals("quit")) {
                board.quitGame(fileName, currentPlayer, firstMove);
            }
        }
    }

    /**
     * Clears the console using ANSI escape code  
     */
    private void clearConsole() {
        // ANSI escape code to clear the console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Load the dictionary from a file a stores it in a hashmap
     */
    private void loadDictionary() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            fileReader = new FileReader("dictionary.txt");
            bufferedReader = new BufferedReader(fileReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                dictionary.put(nextLine.trim().toUpperCase(), true);
            }
            System.out.println("Dictionary loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading dictionary: " + e.getMessage());
        }
    } 

    /**
     * Checks if a word in the dicitonary hashmap
     * 
     * @param word the word to check
     * @return true if the word is in the dictionary hashmap
     */
    private boolean isWordValid(String word) {
        System.out.println(dictionary.containsKey(word.trim().toUpperCase()));
        return dictionary.containsKey(word.trim().toUpperCase());
    }

    /**
     * Loads the saved game state from the file
     * 
     * @param fileName the name of the file to load the game state
     */
    public void loadGame(String fileName) {
        // new object to return 2 different data types (boolean and integer)
        // so that the code can load the game and the player to move
        GameState gameState = board.load(fileName);
        this.firstMove = gameState.firstMove;
        this.currentPlayer = gameState.currentPlayer;
    }
}
