package game;

import game.utils.LinkedList;
import game.utils.Utils;

import java.util.Scanner;

public class Leaderboard {
    // Private variables
    private static final LinkedList games = new LinkedList();
    private static final Scanner input = new Scanner(System.in);

    /*
     * Adds a game to the games linked list
     * Pre: Takes in an instance of the game object
     */
    public static void addGameToLeaderboard(Game game) {
        games.addToFront(game);
    }

    /*
     * Display a list of the default string outputted by the linked list
     */
    public static void displayGames() {
        System.out.println(games);
    }

    /*
     * Displays a leaderboard based on the games in the linked list
     */
    public static void displayLeaderboard() {
        Game[] sortedGames = Utils.insertionSort(games);
        if (sortedGames.length == 0) {
            System.out.println("No games have been played yet!");
        } else {
            int maximumPlayerNameLength = 6;
            int maximumPositionDigits = 4;
            int maximumLevelDigits = 5;

            for (int i = 0; i < sortedGames.length; i++) {
                if (sortedGames[i].getPlayerName().length() > maximumPlayerNameLength) {
                    maximumPlayerNameLength = sortedGames[i].getPlayerName().length();
                }

                if (String.valueOf(sortedGames[i].getLevel()).length() > maximumLevelDigits) {
                    maximumLevelDigits = String.valueOf(sortedGames[i].getLevel()).length();
                }

                if (String.valueOf(i).length() > maximumPositionDigits) {
                    maximumPositionDigits = String.valueOf(i).length();
                }
            }

            String formatter = "%-" + maximumPositionDigits + "s | %" + maximumPlayerNameLength +"s | %"
                    + maximumLevelDigits +"s\n";
            System.out.printf(formatter, "Pos.", "Player", "Level");

            for (int i = 0; i < sortedGames.length; i++) {
                System.out.printf(formatter, "#" + (i+1), sortedGames[i].getPlayerName(), sortedGames[i].getLevel());
            }

            System.out.println();
            System.out.println("The following are actions you can take:");
            System.out.println("\t1) View stats of a specific player");
            System.out.println("\t2) Exit");
            System.out.println();

            int userInput;
            do {
                System.out.print("Selection: ");
                userInput = input.nextInt();
                input.nextLine();
            } while (userInput < 1 || userInput > 2);

            if (userInput == 1) {
                System.out.print("Enter a name: ");
                String name = input.nextLine();

                LinkedList foundGames = new LinkedList();
                LinkedList.Node data = games.getHead();

                // Linear Search
                do {
                    if (data != null) {
                        if (data.getData().getPlayerName().equalsIgnoreCase(name)) {
                            foundGames.addToFront(data.getData());
                        }
                        data = data.getNext();
                    }
                } while (data != null);

                System.out.println();
                if (foundGames.getHead() != null) {
                    printFoundGames(foundGames.getHead());
                } else {
                    System.out.println("Could not find player by that name!");
                }
            }
        }
    }

    /*
     * Recursively prints all a list of all linked nodes
     * Pre: Takes in a LinkedList node
     */
    public static void printFoundGames(LinkedList.Node node) {
        if (node != null) { // General case (Prints until the node no longer links to any other nodes)
            Game data = node.getData();
            System.out.printf("Name: %s | Level: %s\n", data.getPlayerName(), data.getLevel());
            printFoundGames(node.getNext());
        } // Base case, does nothing/stops
    }
}
