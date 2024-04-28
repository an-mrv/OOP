package ru.meshcheryakova.snake;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for snake.
 */
public class Snake {
    private int len;
    private List<Coords> snake;

    /**
     * Constructor.
     * The starting length of the snake is 1.
     *
     * @param start the coordinates of the snake's body at the beginning of the game
     */
    public Snake(Coords start) {
        this.len = 1;
        this.snake = new ArrayList<>();
        this.snake.add(start);
    }

    /**
     * Get the length of the snake.
     *
     * @return the length of the snake
     */
    public int getLen() {
        return this.len;
    }

    /**
     * Get the coordinates of the snake's head.
     *
     * @return coordinates of the snake's head
     */
    public Coords getHead() {
        return this.snake.get(0);
    }

    /**
     * Get the coordinates of the last element of the snake.
     *
     * @return coordinates of the last element of the snake
     */
    public Coords getTail() {
        return this.snake.get(this.len - 1);
    }

    /**
     * Checking that the giving coordinate is a snake.
     *
     * @param c coordinate to check
     * @return snake or not
     */
    public boolean isSnake(Coords c) {
        for (Coords element : this.snake) {
            if (element.equals(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Move the snake by one square in the direction of the new head coordinate.
     *
     * @param newHead new head coordinate
     */
    public void changeSnake(Coords newHead) {
        this.snake.remove(len - 1);
        this.snake.add(0, newHead);
    }

    /**
     * Increase the length of the snake by one square in the direction of the new head coordinate.
     *
     * @param newHead new head coordinate
     */
    public void growSnake(Coords newHead) {
        this.snake.add(0, newHead);
        len++;
    }

    /**
     * Updating snake data when restarting the game. The length of the snake becomes 1.
     *
     * @param restart the coordinates of the snake's body when restarting the game
     */
    public void restartSnake(Coords restart) {
        this.snake.clear();
        this.snake.add(restart);
        this.len = 1;
    }
}
