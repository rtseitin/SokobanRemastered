/*
 * Name: Robert Tseitin
 * Course: ICS4U
 * Date: January 20th, 2022
 * Description: Sokoban is a game where the player is put into a warehouse, in this warehouse the player is instructed to push boxes/crates
 * into the right storage location. There is an infinite amount of levels, and they get progressively more difficult, your job is to see how far you can get!
 * This remastered rendition adds leaderboards, save files, and a brand new more efficient codebase!
 */

import game.Game;
import game.Leaderboard;
import game.utils.FileIO;
import game.utils.Utils;

import java.util.Scanner;

public class ICS4U_FP {
    // Global variables (Scanner instance and possible player emotes)
    public static Scanner input = new Scanner(System.in);

    public static String[] emotes = new String[]{"☃️", "\uD83D\uDE0E", "\uD83D\uDC7B", "\uD83E\uDD84", "\uD83E\uDD89"};
    public static String playerEmote;

    public static void main(String[] args) {
        System.out.println("Welcome to Sokoban!");

        int userInput;
        // Menu
        do {
            System.out.println();
            System.out.println("The following are actions you can do:");
            System.out.println("\t1) View the game instructions and controls");
            System.out.println("\t2) Choose your player emoticon");
            System.out.println("\t3) Start a new game");
            System.out.println("\t4) Resume a game");
            System.out.println("\t5) View leaderboard");
            System.out.println("\t6) Exit");
            System.out.println();

            System.out.print("Selection (1-6): ");
            userInput = input.nextInt();
            input.nextLine();

            System.out.println();

            if (userInput == 1) { // Instructions
                System.out.println("Game Instructions:");
                System.out.println("\t1. You are a player spawned on a map, you as the player are represented by a " +
                        "\"\uD83D\uDC7B️\" or an emote of your choice.");
                System.out.println("\t2. There will be boxes spawned across the map, they look like the following" +
                        " \"\uD83D\uDDBE\".");
                System.out.println("\t3. Your job will be to move these boxes to their drop-off/destination locations. They look " +
                        "like this \"☆\".");
                System.out.println("\t4. Any grid spot that looks like \"▢\" is unoccupied.");
                System.out.println("\t5. Once you drop-off a box by pushing, it will turn into \"▦\", when they are dropped off they are no longer able to move");
                System.out.println("\t6. You push the box when you move left, right, up, or down in the direction of the box.");
                System.out.println("\t7. Note: Boxes that are completed, or blocked by the wall or other boxes cannot be moved.");
                System.out.println("\t8. There are an infinite amount of levels and they get progressively more difficult until" +
                        " the difficulty cap is reached.");
                System.out.println();
                Game.printControls();

            } else if (userInput == 2) { // Player emote selection
                System.out.println("Character Selection:");
                for (int i = 0; i < emotes.length; i++) {
                    System.out.printf("%s) %s\n", i+1, emotes[i]);
                }
                System.out.println();

                int emoteChoice;
                do {
                    System.out.print("Selection: ");
                    emoteChoice = input.nextInt();
                    input.nextLine();
                } while (emoteChoice < 1 || emoteChoice > emotes.length);
                System.out.printf("Set %s has been set as your player emote.\n", emotes[emoteChoice-1]);
                playerEmote = emotes[emoteChoice-1];

            } else if (userInput == 3) { // Starts new game
                System.out.print("Please enter your name, this will show up in the leaderboard: ");
                String playerName = input.nextLine();

                Game game = new Game(playerName);
                if (playerEmote != null) game.setPlayerEmote(playerEmote);
                game.newGame();
            } else if (userInput == 4) { // Resumes a game based off of the selected game file
                String[] readItems = FileIO.readFile();

                String name = null;
                String level = null;
                String token = null;

                int levelInt;

                for (String readItem : readItems) {
                    if (readItem.startsWith("name=")) {
                        name = readItem.replaceFirst("name=", "");
                    } else if (readItem.startsWith("level=")) {
                        level = readItem.replaceFirst("level=", "");
                    } else if (readItem.startsWith("token=")) {
                        token = readItem.replaceFirst("token=", "");
                    }
                }

                if (name == null) {
                    System.out.println("Please ensure that the name is defined in the save file!");
                } else if (token == null) {
                    System.out.println("Please ensure that the token is defined in the save file!");
                } else if (level == null) {
                    System.out.println("Please ensure that the level is defined in the save file!");
                } else {
                    try {
                        levelInt = Integer.parseInt(level);

                        if ((name + "@" + levelInt).equals(Utils.decryptString(token, Utils.KEY))) {
                            Game game = new Game(name, levelInt);
                            if (playerEmote != null) game.setPlayerEmote(playerEmote);
                            game.newGame();
                        } else {
                            System.out.println("Invalid save file! Information mismatch!");
                        }
                    } catch(NumberFormatException e) {
                        System.out.println("Please ensure that the level is a valid integer!");
                    }
                }

            } else if (userInput == 5) { // Displays leaderboard
                System.out.println("====== Leaderboard ======");
                Leaderboard.displayLeaderboard();
            }
        } while (userInput != 6);
        System.out.println("Thanks for playing!");
    }
}
