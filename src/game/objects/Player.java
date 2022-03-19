package game.objects;

import game.utils.Utils;

public class Player extends Tile {
    // Instance variable
    private final String playerEmote;

    /*
     * Constructor for the Player class, sets instance variables
     */
    public Player(int x, int y, Grid currentGrid, String playerEmote) {
        super(x, y, currentGrid, Utils.PLAYER);
        this.playerEmote = playerEmote;
    }

    /*
     * Resets the position of the player
     */
    public void resetPosition() {
        int setX = 2;
        int setY = 2;
        while (currentGrid.isBoxRaw(setX, setY)) {
            if (setX >= currentGrid.getWidth() - 1) {
                setY++;
                setX = 1;
            } else setX++;
        }
        this.x = setX;
        this.y = setY;
    }

    /*
     * Tries moving the player up and returns whether it was successful or not
     * Post: Boolean
     */
    public boolean moveUp() {
        if (!currentGrid.isWall(x, y - 1)) {
            if (currentGrid.isBox(x, y - 1)) {
                if (currentGrid.getBox(x, y - 1).moveUp()) {
                    y -= 1;
                    return true;
                }
                return false;
            }
            y -= 1;
            return true;
        }
        return false;
    }

    /*
     * Tries moving the player down and returns whether it was successful or not
     * Post: Boolean
     */
    public boolean moveDown() {
        if (!currentGrid.isWall(x, y + 1)) {
            if (currentGrid.isBox(x, y + 1)) {
                if (currentGrid.getBox(x, y + 1).moveDown()) {
                    y += 1;
                    return true;
                }
                return false;
            }
            y += 1;
            return true;
        }
        return false;
    }

    /*
     * Tries moving the player right and returns whether it was successful or not
     * Post: Boolean
     */
    public boolean moveRight() {
        if (!currentGrid.isWall(x + 1, y)) {
            if (currentGrid.isBox(x + 1, y)) {
                if (currentGrid.getBox(x + 1, y).moveRight()) {
                    x += 1;
                    return true;
                }
                return false;
            }
            x += 1;
            return true;
        }
        return false;
    }

    /*
     * Tries moving the player left and returns whether it was successful or not
     * Post: Boolean
     */
    public boolean moveLeft() {
        if (!currentGrid.isWall(x - 1, y)) {
            if (currentGrid.isBox(x - 1, y)) {
                if (currentGrid.getBox(x - 1, y).moveLeft()) {
                    x -= 1;
                    return true;
                }
                return false;
            }
            x -= 1;
            return true;
        }
        return false;
    }

    /*
     * Returns a string representing the player
     * Post: Returns a string
     */
    public String toString() {
        return playerEmote;
    }
}
