package ru.meshcheryakova.snake;

import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static javafx.scene.input.KeyCode.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Game class.
 */
public class TestGame {
    private Game game;

    @BeforeEach
    public void initialize() {
        Group root = new Group();
        game = new Game(root);
    }

    @Test
    public void startGame() {
        assertEquals(game.lastPressedKey.get(), RIGHT);
        assertFalse(game.win.get());
        assertFalse(game.gameOver.get());
        assertEquals(game.totalScore.get(), 0);
    }

    @Test
    public void gameOver() {
        game.move();
        game.move();
        game.lastPressedKey.set(UP);
        for (int i = 0; i < 4; i++) {
            game.move();
        }
        assertTrue(game.gameOver.get());
    }

    @Test
    public void restartGame() {
        game.move();
        game.lastPressedKey.set(LEFT);
        assertEquals(game.lastPressedKey.get(), LEFT);
        game.lastPressedKey.set(UP);
        game.restartGame();
        assertEquals(game.lastPressedKey.get(), UP);
        assertFalse(game.win.get());
        assertFalse(game.gameOver.get());
        assertEquals(game.totalScore.get(), 0);
    }
}
