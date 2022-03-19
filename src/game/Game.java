package game;

import game.objects.Grid;
import game.utils.FileIO;
import game.utils.Utils;

import java.util.Scanner;
import java.util.UUID;

public class Game {
    // Instance variables
    private final Scanner input = new Scanner(System.in);
    private String playerEmote = Utils.DEFAULT_PLAYER_EMOTE;
    private final String playerName;
    private final UUID id = UUID.randomUUID();
    private final Long timestamp = System.currentTimeMillis();

    private int level = 1;
    private int width = 9;
    private int height = 7;

    private Grid grid = new Grid(width, height, level, playerEmote);

    /*
     * Constructor for the Game class, sets instance variables
     */
    public Game(String playerName) {
        this.playerName = playerName;
    }

    /*
     * Overflown constructor that also sets the current level
     */
    public Game(String playerName, int level) {
        this.playerName = playerName;
        this.level = level;
    }

    /*
     * Sets the value of the player's emote
     * Pre: String of emote
     */
    public void setPlayerEmote(String emote) {
        playerEmote = emote;
    }

    /*
     * Starts a new game
     */
    public void newGame() {
        for (int i = 1; i < level; i++) updateWidthHeight();
        grid = new Grid(width, height, level, playerEmote);
        run();
    }

    /*
     * Runs the actual functionality of the game
     */
    private void run() {
        String userInput = "";
        System.out.printf("Hi %s! Have fun playing sokoban!\n", playerName);
        System.out.println();

        do {
            System.out.printf("============ LEVEL %s ============\n", level);
            while (!grid.hasWon() && !(userInput.equalsIgnoreCase("stop") || userInput.equalsIgnoreCase("exit"))) {
                System.out.print(grid);
                System.out.print("Enter move: ");
                userInput = input.nextLine();

                switch (userInput.toLowerCase()) {
                    case "w":
                    case "up":
                        grid.getPlayer().moveUp();
                        break;
                    case "s":
                    case "down":
                        grid.getPlayer().moveDown();
                        break;
                    case "a":
                    case "left":
                        grid.getPlayer().moveLeft();
                        break;
                    case "d":
                    case "right":
                        grid.getPlayer().moveRight();
                        break;
                    case "map-reset":
                    case "mr":
                        grid.resetMap();
                        break;
                    case "reset":
                    case "r":
                        grid.reset();
                        break;
                    case "controls":
                    case "c":
                        printControls();
                        break;
                    case "stop":
                    case "exit":
                        System.out.println();
                        System.out.println("* Note: If you choose to save your progress your current run will not be added to the leaderboard. *");

                        String saveProgress;
                        do {
                            System.out.print("Would you like to save your progress (Y/N)? ");
                            saveProgress = input.nextLine();
                        } while (!(saveProgress.equalsIgnoreCase("Y") || saveProgress.equalsIgnoreCase("N")
                                || saveProgress.equalsIgnoreCase("yes") || saveProgress.equalsIgnoreCase("no")));

                        if (saveProgress.equalsIgnoreCase("Y") || saveProgress.equalsIgnoreCase("yes")) {
                            exportSaveFile();
                        } else {
                            Leaderboard.addGameToLeaderboard(this);
                        }
                        break;
                    default:
                        System.out.println("You did not enter a valid command. Enter \"c\" or \"control\" to review controls.");
                        break;
                }
                grid.updateGrid();
                if (!(userInput.equalsIgnoreCase("exit") || userInput.equalsIgnoreCase("stop"))) {
                    System.out.println();
                }
            }
            if (!(userInput.equalsIgnoreCase("stop") || userInput.equalsIgnoreCase("exit"))) {
                System.out.print(grid);
                System.out.println();
                level += 1;
                updateWidthHeight();
                grid = new Grid(width, height, level, playerEmote);
            }
        } while (!(userInput.equalsIgnoreCase("stop") || userInput.equalsIgnoreCase("exit")));
    }

    /*
     * Returns the player's name
     * Post: String of player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /*
     * Returns the player id
     * Post: Returns a UUID representing the player id
     */
    public UUID getId() {
        return id;
    }

    /*
     * Returns the current value of the level variable
     * Post: Integer of game level
     */
    public int getLevel() {
        return level;
    }

    /*
     * Returns a timestamp of when the instance of game was first made
     * Post: Long representing timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /*
     * Updates the width and height of the game's grid in order to adjust difficulty
     */
    private void updateWidthHeight() {
        if (width < 23) {
            width += 2;
        }
        if (height < 10) {
            height += 1;
        }
    }

    /*
     * Exports the game data in to a save file in order to be accessed later
     */
    private void exportSaveFile() {
        String token = Utils.encryptString(playerName + "@" + level, Utils.KEY);
        String toWrite = String.format("name=%s" +
                "\nlevel=%s" +
                "\ntoken=%s", playerName, level, token);

        System.out.println();
        FileIO.writeToFile(toWrite);
    }

    /*
     * Global method that prints the controls for the game
     */
    public static void printControls() {
        System.out.println("\nControls:");
        System.out.println(
                "\tUp: Type either \"up\" or \"w\" to make the character move up\n"
                        + "\tLeft: Type either \"left\" or \"a\" to make the character move left\n"
                        + "\tDown: Type either \"down\" or \"s\" to make the character move down\n"
                        + "\tRight: Type either \"right\" or \"d\" to make the character move right\n"
                        + "\tRestart: Type either \"reset\" or \"r\" to reset the level from the beginning\n"
                        + "\tMap Restart: Type either \"map-reset\" or \"mr\" to generate a new map at your current level\n"
                        + "\tSee Controls: Type either \"controls\" or \"c\" to see the controls\n"
                        + "\tExit: Type either \"exit\" or \"stop\" to exit the game"
        );
    }

    /*
     * Returns whether the inputted object equals the instance of this object
     * Pre: Takes in an instance of object
     * Post: Returns a boolean representing equals or not equals
     */
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) return false;

        Game game = (Game) obj;
        return id.equals(game.getId());
    }

    /*
     * Returns a string containing information about this instance of the object
     * Post: Returns a string
     */
    public String toString() {
        return "Game{" +
                "playerName='" + playerName + '\'' +
                ", id=" + id +
                ", level=" + level +
                '}';
    }
}
