package ru.meshcheryakova.snake;

import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.text.Text;

/**
 * Class for food.
 */
public class Food {
    private Integer count = 0;
    private Text foodText;
    private List<Circle> food = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param foodText the inscription about the amount of food eaten in a specific color
     *                 on the side menu
     */
    public Food(Text foodText) {
        this.foodText = foodText;
    }

    /**
     * Increasing the counter of the amount of food eaten in a specific color.
     */
    public void incCount() {
        this.count++;
    }

    /**
     * Update the inscription about the amount of food eaten in a specific color on the side menu.
     */
    public void updateFoodText() {
        this.foodText.setText(this.count + "/5");
    }

    /**
     * Get the inscription about the amount of food eaten in a specific color on the side menu.
     *
     * @return the inscription
     */
    public Text getFoodText() {
        return this.foodText;
    }

    /**
     * Get the amount of food eaten in a specific color.
     *
     * @return the amount of food eaten
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Add a food object of a specific color to the rest of the food objects of that
     * color on the field.
     *
     * @param food food object to add
     */
    public void addFood(Circle food) {
        this.food.add(food);
    }

    /**
     * Remove a food object of a specific color from the list of all food objects of that color
     * in the field.
     *
     * @param food food object to remove
     */
    public void removeFood(Circle food) {
        this.food.remove(food);
    }

    /**
     * Get a food object by index.
     *
     * @param i index of food object
     * @return food object
     */
    public Circle getFood(int i) {
        return this.food.get(i);
    }

    /**
     * Get the current number of food objects of a specific color on the field.
     *
     * @return number of food objects of a specific color on the field
     */
    public int getAmountOfFood() {
        return this.food.size();
    }

    /**
     * Updating food data when restarting the game.
     */
    public void restartFood() {
        this.count = 0;
        this.foodText.setText("0/5");
        this.food.clear();
    }
}
