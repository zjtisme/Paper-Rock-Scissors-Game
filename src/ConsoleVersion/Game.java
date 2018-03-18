package ConsoleVersion;

import java.io.Console;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Game {
    final static Scanner scanner = new Scanner(System.in);
    final static Console console = System.console();


    public static void main(String[] args) {

        Player player1 = null;
        Player player2 = null;

        while(true) {
            displayMenu();
            String choice = scanner.nextLine().toLowerCase().trim();

            if(choice.equals("2 players")) {
                if(player1 == null) {
                    String player1Name = "";
                    do {
                        System.out.println("Please input your name, player1:");
                        player1Name = scanner.nextLine();
                    } while (player1Name.length() == 0);

                    player1 = new Player(player1Name);
                }
                String player2Name = "";
                do {
                    System.out.println("Please input your name, player2:");
                    player2Name = scanner.nextLine();
                } while (player2Name.length() == 0);
                player2 = new Player(player2Name);
                handle2Players(player1, player2);
            } else if(choice.equals("vs. computer")) {
                if(player1 == null) {
                    String player1Name = "";
                    do {
                        System.out.println("Please input your name, player1:");
                        player1Name = scanner.nextLine();
                    } while (player1Name.length() == 0);

                    player1 = new Player(player1Name);
                }
                handleVSComputer(player1);
            } else if(choice.equals("quit")) {
                String name = (player1 == null)?"":player1.getName();
                System.out.println("See you! " + name);
                break;
            } else if(choice.equals("history")) {
                if(player1 != null)
                    player1.viewHistory();
                else
                    System.out.println("No History!");
            } else {
                System.out.println("Invalid input, please refer to menu and try again!");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Welcome to Rock Paper Scissors!");
        System.out.println();
        System.out.println("MAIN MENU");
        System.out.println("=====");
        System.out.println("1. Type '2 players' to play this game with other player");
        System.out.println("2. Type 'vs. Computer' to play this game with computer" );
        System.out.println("3. Type 'history' to view your game history");
        System.out.println("4. Type 'quit' to quit this game");
    }

    private static void displayInstructions() {
        System.out.println("Type in 'rock', 'paper' or 'scissors' to play");
        System.out.println("Type 'quit' to go back to the main menu");
    }

    private static void handleVSComputer(Player p) {
        String gameChoice = "";
        do {
            displayInstructions();
            gameChoice = scanner.nextLine().toLowerCase().trim();
            String res = p.playWithComputer(gameChoice);
            System.out.println(res);
        } while (!gameChoice.equals("quit"));
    }

    private static void handle2Players(Player p1, Player p2) {
        String player1Choice = "";
        String player2Choice = "";
        final Set<String> operations = new HashSet<>();
        operations.add("rock");
        operations.add("paper");
        operations.add("scissors");
        do {
            displayInstructions();
            try {
                player1Choice = new String(console.readPassword("Player1's input:"));
                if(player1Choice.toLowerCase().trim().equals("quit")) {
                    System.out.println("Back to main menu");
                    break;
                }
                if(!operations.contains(player1Choice.toLowerCase().trim())) {
                    System.out.println("Invalid input, please refer to instructions and try again!");
                    continue;
                }
                player2Choice = new String(console.readPassword("Player2's input:"));
            } catch (NullPointerException e) {
                System.out.println("Cannot find console object, please run this program in pure terminal!");
                break;
            }

            String res = p1.playWithPlayer(p2, player1Choice.toLowerCase().trim(), player2Choice.toLowerCase().trim());
            System.out.println(res);
        } while (!player1Choice.equals("quit") && !player2Choice.equals("quit"));
    }
}
