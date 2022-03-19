package game.objects;

import game.utils.Utils;

import java.util.Random;

public class Grid {
    // Instance variables
    private final Random rand = new Random();
    private final int MAX_BOXES = 12;

    private final Tile[][] grid;
    private Box[] boxes;
    private Destination[] destinations;

    private final int boxCount;
    private final int height;
    private final int width;

    private final Player player;

    /*
     * Constructor for the Grid class, sets instance variables
     */
    public Grid(int width, int height, int boxCount, String playerEmote) {
        player = new Player(2, 2, this, playerEmote);

        if (boxCount > MAX_BOXES) boxCount = MAX_BOXES;
        this.boxCount = boxCount;
        boxes = new Box[boxCount];
        destinations = new Destination[boxCount];

        this.width = width;
        this.height = height;
        grid = new Tile[width][height];

        createBoxes();
        createDestinations();
        player.resetPosition();
        updateGrid();
    }

    /*
     * Returns the value of the name instance variable
     * Post: String of game name
     */
    public Player getPlayer() {
        return player;
    }

    /*
     * Resets the current grid by resetting the player and boxes to their initial position
     */
    public void reset() {
        for (int i = 0; i < boxCount; i++) {
            boxes[i].reset();
        }
        player.resetPosition();
        updateGrid();
    }

    /*
     * Resets the grid by generating new boxes and destinations
     */
    public void resetMap() {
        boxes = new Box[boxCount];
        destinations = new Destination[boxCount];

        createBoxes();
        createDestinations();

        player.resetPosition();
        updateGrid();
    }

    /*
     * Returns the status of the tile at the specified coordinates representing the type of tile
     * Pre: int x, int y
     * Post: Returns int representing status
     */
    public int getStatus(int x, int y) {
        return grid[x][y].getStatus();
    }

    /*
     * Sets the status of the tile at the specified coordinates on the grid
     * Pre: int x, int y, int status
     */
    public void setStatus(int x, int y, int status) {
        grid[x][y].setStatus(status);
    }

    /*
     * Returns whether the tile on the grid is a wall
     * Pre: int x, int y
     * Post: Returns boolean
     */
    public boolean isWall(int x, int y) {
        return grid[x][y].getStatus() == Utils.WALL;
    }

    /*
     * Returns whether the tile on the grid is a box
     * Pre: int x, int y
     * Post: Returns boolean
     */
    public boolean isBox(int x, int y) {
        return grid[x][y].getStatus() == Utils.BOX;
    }

    /*
     * Returns whether the tile on the grid is a destination
     * Pre: int x, int y
     * Post: Returns boolean
     */
    public boolean isDestination(int x, int y) {
        return grid[x][y].getStatus() == Utils.DESTINATION;
    }

    /*
     * Returns whether there is a box at the specified coordinates on the grids by checking the box array instead of the
     * actual grid
     * Pre: int x, int y
     * Post: Returns boolean
     */
    public boolean isBoxRaw(int x, int y) {
        for (int i = 0; i < boxCount; i++) {
            if (x == boxes[i].getX() && y == boxes[i].getY()) {
                return true;
            }
        }
        return false;
    }

    /*
     * Returns the instance of a box at a location if available
     * Pre: int x, int y
     * Post: Returns box
     */
    public Box getBox(int x, int y) {
        for (int i = 0; i < boxCount; i++) {
            if (x == boxes[i].getX() && y == boxes[i].getY()) {
                return boxes[i];
            }
        }
        return null;
    }

    /*
     * Updates the grid in order to make it correspond with the current state of the player, boxes, and destinations
     */
    public void updateGrid() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[j][i] = new Tile(Utils.GROUND);

                if (j == 0 || j == width - 1 || i == 0 || i == height - 1) {
                    grid[j][i] = new Tile(Utils.WALL);
                }

                for (int k = 0; k < boxCount; k++) {
                    if (destinations[k].getX() == j && destinations[k].getY() == i) {
                        grid[j][i] = destinations[k];
                    }
                }

                if (player.getX() == j && player.getY() == i) {
                    grid[j][i] = player;
                }

                for (int k = 0; k < boxCount; k++) {
                    if (boxes[k].getX() == j && boxes[k].getY() == i) {
                        if (boxes[k].onDestination()) {
                            grid[j][i] = new Tile(Utils.WALL);
                        } else {
                            grid[j][i] = boxes[k];
                        }
                    }
                }
            }
        }
    }

    /*
     * Creates/Generates a new set of destinations for the boxes
     */
    public void createDestinations() {
        for (int i = 0; i < boxCount; i++) {
            int x = rand.nextInt(width - 2) + 1;
            int y = rand.nextInt(height - 2) + 1;
            for (int j = 0; j < i; j++) {
                while (((x == destinations[j].getX() && y == destinations[j].getY())) || isBoxRaw(x, y)
                        || (x == player.getX() && y == player.getX())) {
                    x = rand.nextInt(width - 2) + 1;
                    y = rand.nextInt(height - 2) + 1;
                }
            }
            destinations[i] = new Destination(x, y, this);
        }
    }

    /*
     * Creates/Generates a new set of boxes
     */
    public void createBoxes() {
        for (int i = 0; i < boxCount; i++) {
            int x = rand.nextInt(width - 4) + 2;
            int y = rand.nextInt(height - 4) + 2;
            for (int j = 0; j < i; j++) {
                while ((x == boxes[j].getX() && y == boxes[j].getY()) || (x == 2 && y == 2) || (x - 1 == boxes[j].getX()
                        && y == boxes[j].getY()) || (x + 1 == boxes[j].getX() && y == boxes[j].getY())) {
                    x = rand.nextInt(width - 4) + 2;
                    y = rand.nextInt(height - 4) + 2;
                }
            }
            boxes[i] = new Box(x, y, this);
        }
    }

    /*
     * Returns if the game/current grid has been won
     * Post: Boolean
     */
    public boolean hasWon() {
        for (int i = 0; i < boxCount; i++) {
            if (!destinations[i].hasBox(this)) {
                return false;
            }
        }
        return true;
    }

    /*
     * Returns the 2d tile array representing the grid
     * Post: Returns 2d Tile array
     */
    public Tile[][] getGrid() {
        return grid;
    }

    /*
     * Returns an array of all the boxes
     * Post: Box Array
     */
    public Box[] getBoxes() {
        return boxes;
    }

    /*
     * Returns an array of all the destinations
     * Post: Destination Array
     */
    public Destination[] getDestinations() {
        return destinations;
    }

    /*
     * Returns the current box count
     * Post: int of box count
     */
    public int getBoxCount() {
        return boxCount;
    }

    /*
     * Returns the height of the grid
     * Post: int of height
     */
    public int getHeight() {
        return height;
    }

    /*
     * Returns the width of the grid
     * Post: int of width
     */
    public int getWidth() {
        return width;
    }

    /*
     * Returns a string containing information about this instance of the grid. Updates grid prior to returning string
     * Post: Returns a string
     */
    public String toString() {
        updateGrid();
        String s = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                s += String.format(" %s ", grid[j][i]);
            }
            s += "\n";
        }
        return s;
    }
}
