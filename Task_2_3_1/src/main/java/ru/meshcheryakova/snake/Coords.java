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
     * @param x coordinate x
     * @param y coordinate y
     */
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the value of x coordinate.
     *
     * @return the value of x coordinate
     */
    public int x() {
        return x;
    }

    /**
     * Get the value of y coordinate.
     *
     * @return the value of y coordinate
     */
    public int y() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Coords other)) {
            return false;
        }
        return (this.x == other.x()) && (this.y == other.y());
    }
}