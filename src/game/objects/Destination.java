package game.objects;

import game.utils.Utils;

public class Destination extends Tile {

    /*
     * Constructor for the Destination class, sets instance variables
     */
    public Destination(int x, int y, Grid currentGrid){
        super(x, y, currentGrid, Utils.DESTINATION);
    }

    /*
     * Returns whether the inputted grid has a wall at the location of the destination
     * Pre: Grid
     * Post: Boolean
     */

    public boolean hasBox(Grid currentGrid) {
        return currentGrid.isWall(x, y);
    }

    /*
     * Returns a string representing the destination drop-off
     * Post: Returns a string
     */
    public String toString() {
        return "\u2606";
    }
}
