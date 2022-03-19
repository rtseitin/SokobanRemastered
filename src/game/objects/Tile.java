package game.objects;

import game.utils.Utils;

public class Tile {
    // Instance variables
    protected Integer x;
    protected Integer y;

    protected Grid currentGrid;

    private int status;

    /*
     * Constructor for the Tile class, sets instance variables
     */
    public Tile(int status) {
        this.x = null;
        this.y = null;

        this.status = status;
    }

    /*
     * Overflown constructor that allows to define currentGrid in addition to the x and y coordinates
     */
    public Tile (int x, int y, Grid currentGrid, int status) {
        this.x = x;
        this.y = y;

        this.currentGrid = currentGrid;

        this.status = status;
    }

    /*
     * Sets the status/type of the tile
     * int status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /*
     * Returns the status of the tile representing the type of the tile
     */
    public int getStatus() {
        return status;
    }

    /*
     * Returns x coordinate of the tile
     * Post: Returns int
     */
    public int getX() {
        return x;
    }

    /*
     * Returns y coordinate of the tile
     * Post: Returns int
     */
    public int getY() {
        return y;
    }

    /*
     * Returns a string representing the tile
     * Post: Returns a string
     */
    public String toString() {
        if (status == Utils.GROUND) {
            return "\u25A2";
        } else if (status == Utils.WALL) {
            return "\u25A6";
        } else return "null";
    }
}
