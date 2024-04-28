package ru.meshcheryakova.snake;

/**
 * Class for coordinates.
 */
public class Coords {
    private int cordX;
    private int cordY;

    /**
     * Constructor.
     *
     * @param x coordinate getCordX
     * @param y coordinate getCordY
     */
    public Coords(int x, int y) {
        this.cordX = x;
        this.cordY = y;
    }

    /**
     * Get the value of getCordX coordinate.
     *
     * @return the value of getCordX coordinate
     */
    public int getCordX() {
        return cordX;
    }

    /**
     * Get the value of getCordY coordinate.
     *
     * @return the value of getCordY coordinate
     */
    public int getCordY() {
        return cordY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Coords)) {
            return false;
        }
        Coords other = (Coords) obj;
        return (this.cordX == other.getCordX()) && (this.cordY == other.getCordY());
    }
}