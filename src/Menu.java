import java.util.Scanner;
/**
 * A class that represents menu system for users to interact with the game
 * 
 * @author E. Pawulski
 * @version 1.0
 */
public class Menu {
    Scanner s = new Scanner(System.in);

    /**
     * Displays the menu options and gets the user's menu choice
     * 
     * @return the user's menu choice as a integer
     */
    public int getMenuChoice() {
        System.out.println();
        System.out.println("0. Exit");
        System.out.println("1. Play");
        System.out.println("2. Load");
        System.out.println("3. Help");
        
        return s.nextInt(); 
    }

    /**
     * Displays help instructions on how to play the game
     */
    public void help() {
        // help instructions
        System.out.println("How to play the game...");
        System.out.println();
        System.out.println("To play type 1 and then type play to start playing Scribble.");
        System.out.println("The first word always has to be play in the middle and be at least 2 letters long.");
        System.out.println("All words have to be in the dictionary and not be outside of the board.");
        System.out.println("They also have to have a letter that overlaps with a letter that is already on the board.");
        System.out.println("The next player must then type play if they to continue playing.");
        System.out.println();
        System.out.println("If they type quit the game will save to a file and exit.");
        System.out.println("You can always load the game by typing 2 in the menu.");
        System.out.println();
        System.out.println("Player get points based on the letter in the word they play.");
        System.out.println("To win Scribble a player must get to a score of 100.");
        
    }

    /**
     * Displays the menu and process the user's menu choices
     * in a loop until the user decides to exit.
     */
    public void displayMenu() {
        Menu menu = new Menu();
        int menuChoice;

        Game game = new Game();

        do {
            menuChoice = menu.getMenuChoice();

            switch (menuChoice) {
                case 0:
                    System.out.println("Exiting the game.");
                    break;
                case 1:
                    System.out.println("Starting game...");
                    game.gameLoop();
                    break;
                case 2:
                    String fileName = "scribbleSave.txt";
                    game.loadGame(fileName);
                    break;
                case 3:
                    System.out.println("Showing help...");
                    help();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            
        } while (menuChoice != 0);
    }

    /**
     * The main method to start the game
     *
     */

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.displayMenu();
    }
}
