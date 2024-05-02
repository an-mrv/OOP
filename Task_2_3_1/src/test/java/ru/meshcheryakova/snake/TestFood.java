package ru.meshcheryakova.snake;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Food class.
 */
public class TestFood {
    private Food food;

    @BeforeEach
    public void initialize() {
        food = new Food(new Text(1120, 75, "0/5"));
    }

    @Test
    public void incCount() {
        food.incCount();
        assertEquals(food.getCount(), 1);
    }

    @Test
    public void updateFoodText() {
        food.incCount();
        food.updateFoodText();
        assertEquals(food.getFoodText().getText(), "1/5");
    }

    @Test
    public void addFood() {
        food.addFood(new Circle());
        assertEquals(food.getAmountOfFood(), 1);
    }

    @Test
    public void removeFood() {
        Circle food1 = new Circle();
        Circle food2 = new Circle();
        food.addFood(food1);
        food.addFood(food2);
        food.removeFood(food1);
        assertEquals(food.getAmountOfFood(), 1);
    }

    @Test
    public void getFood() {
        food.addFood(new Circle(1085, 65, 25, Color.YELLOW));
        assertEquals(food.getFood(0).getCenterX(), 1085);
    }

    @Test
    public void restartFood() {
        food.addFood(new Circle(1085, 65, 25, Color.YELLOW));
        food.incCount();
        food.restartFood();
        assertEquals(food.getCount(), 0);
        assertEquals(food.getAmountOfFood(), 0);
    }
}
