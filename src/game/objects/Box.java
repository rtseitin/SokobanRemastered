package game.objects;

import game.utils.Utils;

public class Box extends Tile {
    // Instance variables
    private final int startingX;
    private final int startingY;

    /*
     * Constructor for the Box class, sets instance variables
     */
    public Box(int x, int y, Grid currentGrid) {
        super(x, y, currentGrid, Utils.BOX);

        startingX = x;
        startingY = y;
    }

    /*
     * Resets the position of the box to its starting position
     */
    public void reset() {
        x = startingX;
        y = startingY;
    }

    /*
     * Returns whether the grid provided when first initializing the objects has a destination at the same coordinates
     * as the current coordinates of the box
     * Post: Boolean
     */
    public boolean onDestination() {
        return currentGrid.isDestination(x, y);
    }

    /*
     * Tries moving the box up and returns whether it was successful or not
     * Post: Boolean
     */
    public boolean moveUp() {
        if (!currentGrid.isWall(x, y-1) && !currentGrid.isWall(x, y-1)) {
            y -= 1;
            return true;
        }
        return false;
    }

    /*
     * Tries moving the box down and returns whether it was successful or not
     * Post: Boolean
     */
    public boolean moveDown() {
        if (!currentGrid.isWall(x, y + 1) && !currentGrid.isBox(x, y + 1)) {
            y += 1;
            return true;
        }
        return false;
    }

    /*
     * Tries moving the box left and returns whether it was successful or not
     * Post: Boolean
     */
    public boolean moveLeft() {
        if (!currentGrid.isWall(x - 1, y) && !currentGrid.isBox(x - 1, y)) {
            x -= 1;
            return true;
        }
        return false;
    }

    /*
     * Tries moving the box right and returns whether it was successful or not
     * Post: Boolean
     */
    public boolean moveRight() {
        if (!currentGrid.isWall(x + 1, y) && !currentGrid.isBox(x + 1, y)) {
            x += 1;
            return true;
        }
        return false;
    }

    /*
     * Returns a string representing the box
     * Post: Returns a string
     */
    public String toString() {
        return "\uD83D\uDDBE";
    }
}
