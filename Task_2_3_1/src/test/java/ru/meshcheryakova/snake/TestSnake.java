package ru.meshcheryakova.snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Snake class.
 */
public class TestSnake {
    private Snake snake;

    @BeforeEach
    public void initialize() {
        snake = new Snake(new Coords(0, 0));
    }

    @Test
    public void changeSnake() {
        snake.changeSnake(new Coords(0, 1));
        assertEquals(snake.getLen(), 1);
    }

    @Test
    public void growSnake() {
        snake.growSnake(new Coords(0, 2));
        assertEquals(snake.getLen(), 2);
    }

    @Test
    public void getHead() {
        snake.changeSnake(new Coords(0, 1));
        assertEquals(snake.getHead(), new Coords(0, 1));
    }

    @Test
    public void getTail() {
        snake.growSnake(new Coords(0, 1));
        assertEquals(snake.getTail(), new Coords(0, 0));
    }

    @Test
    public void isSnake() {
        assertTrue(snake.isSnake(snake.getHead()));
        assertFalse(snake.isSnake(new Coords(10, 10)));
    }

    @Test
    public void restartSnake() {
        snake.restartSnake(new Coords(0, 0));
        assertEquals(1, snake.getLen());
    }
}
