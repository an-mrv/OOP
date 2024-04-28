package ru.meshcheryakova.snake;

/**
 * Class for coordinates.
 */
public class Coords {
    private int x;
    private int y;

    /**
     * Constructor.
     *
     * @param x coordinate getX
     * @param y coordinate getY
     */
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the value of getX coordinate.
     *
     * @return the value of getX coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Get the value of getY coordinate.
     *
     * @return the value of getY coordinate
     */
    public int getY() {
        return y;
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
        return (this.x == other.getX()) && (this.y == other.getY());
    }
}