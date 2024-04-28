package ru.meshcheryakova.snake;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.text.Text;

import static javafx.scene.input.KeyCode.*;

/**
 * Class to control the game.
 */
public class Game {
    public AtomicBoolean gameOver = new AtomicBoolean(false);
    public AtomicBoolean win = new AtomicBoolean(false);
    public AtomicInteger totalScore = new AtomicInteger(0);
    public AtomicReference<KeyCode> lastPressedKey = new AtomicReference<>(RIGHT);
    private Snake snake;
    private Rectangle[][] field;
    private HashMap<String, Food> food = new HashMap<>();
    private Group rootGame;
    private Text totalScoreText;
    private List<Circle> eyes = new ArrayList<>();
    private final Coords[] obstacles = new Coords[] {new Coords(4, 0), new Coords(4, 1),
            new Coords(4, 2), new Coords(4, 3), new Coords(1, 6),
            new Coords(2, 6), new Coords(2, 7), new Coords(4, 15),
            new Coords(4, 14), new Coords(4, 13), new Coords(4, 12),
            new Coords(4, 11), new Coords(4, 10), new Coords(12, 3),
            new Coords(13, 3), new Coords(14, 3), new Coords(14, 4),
            new Coords(14, 15), new Coords(14, 14), new Coords(14, 13),
            new Coords(14, 12), new Coords(4, 0)};

    /**
     * Constructor.
     *
     * @param rootGame group node for game
     */
    public Game(Group rootGame) {
        this.field = new Rectangle[21][16];
        this.snake = new Snake(new Coords(12, 8));
        this.rootGame = rootGame;

        initField();

        initSideMenu();

        for (String key : this.food.keySet()) {
            for (int i = 0; i < 2; i++) {
                this.generateFood(key);
            }
        }

        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 16; j++) {
                if (this.isObstacle(new Coords(i, j))) {
                    this.field[i][j].setFill(Color.DARKGREEN);
                }
            }
        }

        List<Coords> eyesCoords = findEyesLocation();
        this.eyes.add(new Circle(eyesCoords.get(0).x(), eyesCoords.get(0).y(), 5, Color.BLACK));
        this.eyes.add(new Circle(eyesCoords.get(1).x(), eyesCoords.get(1).y(), 5, Color.BLACK));
        this.rootGame.getChildren().addAll(this.eyes.get(0), this.eyes.get(1));
    }

    /**
     * Snake movement.
     */
    public void move() {
        if (this.gameOver.get() || this.win.get()) {
            return;
        }

        Coords newHead = new Coords(0, 0);

        switch (lastPressedKey.get()) {
            case RIGHT:
                newHead = new Coords((snake.getHead().x() + 1) % 21, snake.getHead().y());
                break;
            case LEFT:
                newHead = new Coords((snake.getHead().x() - 1 + 21) % 21, snake.getHead().y());
                break;
            case UP:
                newHead = new Coords(snake.getHead().x(), (snake.getHead().y() - 1 + 16) % 16);
                break;
            case DOWN:
                newHead = new Coords(snake.getHead().x(), (snake.getHead().y() + 1) % 16);
                break;
            default:
                break;
        }

        this.checkCollision(newHead);
        field[snake.getHead().x()][snake.getHead().y()].setFill(Color.RED);

        List<Coords> eyesCoords = findEyesLocation();
        this.eyes.get(0).setCenterX(eyesCoords.get(0).x());
        this.eyes.get(0).setCenterY(eyesCoords.get(0).y());
        this.eyes.get(1).setCenterX(eyesCoords.get(1).x());
        this.eyes.get(1).setCenterY(eyesCoords.get(1).y());
    }

    /**
     * Restarting the game.
     */
    public void restartGame() {
        totalScore.set(0);
        this.totalScoreText.setText("Total score: 0");
        for (String key : this.food.keySet()) {
            Food currFood = this.food.get(key);
            int amountOfFood = currFood.getAmountOfFood();
            for (int i = 0; i < amountOfFood; i++) {
                Circle currFoodElem = currFood.getFood(i);
                this.rootGame.getChildren().remove(currFoodElem);
            }
            currFood.restartFood();
        }

        this.win.set(false);
        this.gameOver.set(false);
        this.snake.restartSnake(new Coords(12, 6));

        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 16; j++) {
                this.field[i][j].setFill(Color.LIGHTGREEN);
                this.field[i][j].setStroke(Color.BLACK);
                Coords current = new Coords(i, j);
                if (snake.isSnake(current)) {
                    this.field[i][j].setFill(Color.RED);
                }
                if (this.isObstacle(current)) {
                    this.field[i][j].setFill(Color.DARKGREEN);
                }
            }
        }

        for (String key : this.food.keySet()) {
            for (int i = 0; i < 2; i++) {
                this.generateFood(key);
            }
        }

        List<Coords> eyesCoords = findEyesLocation();
        this.eyes.get(0).setCenterX(eyesCoords.get(0).x());
        this.eyes.get(0).setCenterY(eyesCoords.get(0).y());
        this.eyes.get(1).setCenterX(eyesCoords.get(1).x());
        this.eyes.get(1).setCenterY(eyesCoords.get(1).y());
    }

    /**
     * Initializing the field at the beginning of the game.
     */
    private void initField() {
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 16; j++) {
                Rectangle rectangle = new Rectangle();
                this.field[i][j] = rectangle;
                rectangle.setX(50 * i);
                rectangle.setY(50 * j);
                rectangle.setHeight(50);
                rectangle.setWidth(50);
                rectangle.setFill(Color.LIGHTGREEN);
                rectangle.setStroke(Color.BLACK);
                if (this.snake.isSnake(new Coords(i, j))) {
                    rectangle.setFill(Color.RED);
                }
                this.rootGame.getChildren().add(rectangle);
            }
        }
    }

    /**
     * Initializing the side menu with current information about the game.
     */
    private void initSideMenu() {
        Rectangle rectangle = new Rectangle(1050, 0, 150, 1200);
        rectangle.setFill(Color.GRAY);

        Text toEat = new Text(1060, 30, "To eat:");
        toEat.setFont(Font.font("Verdana", 30));

        Circle foodYellow = new Circle(1085, 65, 25, Color.YELLOW);
        Text foodYellowText = new Text(1120, 75, "0/5");
        foodYellowText.setFont(Font.font("Verdana", 30));

        Circle foodBlue = new Circle(1085, 125, 25, Color.BLUE);
        Text foodBlueText = new Text(1120, 135, "0/5");
        foodBlueText.setFont(Font.font("Verdana", 30));

        Circle foodOrange = new Circle(1085, 185, 25, Color.ORANGE);
        Text foodOrangeText = new Text(1120, 195, "0/5");
        foodOrangeText.setFont(Font.font("Verdana", 30));

        this.totalScoreText = new Text(1055, 245, "Total score: 0");
        this.totalScoreText.setFont(Font.font("Verdana", 18));

        this.food.put("Yellow", new Food(foodYellowText));
        this.food.put("Blue", new Food(foodBlueText));
        this.food.put("Orange", new Food(foodOrangeText));

        this.rootGame.getChildren().addAll(rectangle, toEat, foodYellow, foodYellowText, foodBlue,
                foodBlueText, foodOrange, foodOrangeText, this.totalScoreText);
    }

    /**
     * Finding the location of the snake's eyes depending on the direction of movement
     * and the coordinates of the head.
     *
     * @return new eye coordinates
     */
    private List<Coords> findEyesLocation() {
        List<Coords> eyesCoords = new ArrayList<>();
        Coords snakeHead = this.snake.getHead();

        if (this.lastPressedKey.get() == RIGHT) {
            eyesCoords.add(new Coords((snakeHead.x() + 1) * 50, snakeHead.y() * 50 + 12));
            eyesCoords.add(new Coords((snakeHead.x() + 1) * 50, snakeHead.y() * 50 + 38));
        } else if (this.lastPressedKey.get() == LEFT) {
            eyesCoords.add(new Coords(snakeHead.x() * 50, snakeHead.y() * 50 + 12));
            eyesCoords.add(new Coords(snakeHead.x() * 50, snakeHead.y() * 50 + 38));
        } else if (this.lastPressedKey.get() == UP) {
            eyesCoords.add(new Coords(snakeHead.x() * 50 + 12, snakeHead.y() * 50));
            eyesCoords.add(new Coords(snakeHead.x() * 50 + 38, snakeHead.y() * 50));
        } else {
            eyesCoords.add(new Coords(snakeHead.x() * 50 + 12, (snakeHead.y() + 1) * 50));
            eyesCoords.add(new Coords(snakeHead.x() * 50 + 38, (snakeHead.y() + 1) * 50));
        }
        return eyesCoords;
    }

    /**
     * Generating food in different colors.
     *
     * @param color the color of the food
     */
    private void generateFood(String color) {
        int x = (int) (Math.random() * 21);
        int y = (int) (Math.random() * 16);
        Coords newFoodCoords = new Coords(x, y);

        if (snake.isSnake(newFoodCoords)) {
            this.generateFood(color);
            return;
        }

        if (this.isObstacle(newFoodCoords)) {
            this.generateFood(color);
            return;
        }

        for (String key : this.food.keySet()) {
            Food currFood = this.food.get(key);
            int amountOfFood = currFood.getAmountOfFood();
            for (int i = 0; i < amountOfFood; i++) {
                Circle currFoodElem = currFood.getFood(i);
                if ((currFoodElem.getCenterX() + 25) % 50 == x &&
                        (currFoodElem.getCenterY() + 25) % 50 == y) {
                    this.generateFood(color);
                    return;
                }
            }
        }

        Circle newFood = new Circle((x + 1) * 50 - 25,
                (y + 1) * 50 - 25, 25, getColor(color));
        this.rootGame.getChildren().add(newFood);
        this.food.get(color).addFood(newFood);
    }

    /**
     * Finding a color by its name.
     *
     * @param color the line with the name of the color
     * @return Object Сolor
     */
    private Color getColor(String color) {
        if (color.equals("Yellow")) {
            return Color.YELLOW;
        } else if (color.equals("Blue")) {
            return Color.BLUE;
        } else {
            return Color.ORANGE;
        }
    }

    /**
     * Checking for eating food, winning, a snake colliding with its body
     * or with an obstacle and losing.
     *
     * @param newHead coordinates of the new snake head
     */
    private void checkCollision(Coords newHead) {
        for (String key : this.food.keySet()) {
            Food currFood = this.food.get(key);
            int amountOfFood = currFood.getAmountOfFood();
            for (int i = 0; i < amountOfFood; i++) {
                Circle currFoodElem = currFood.getFood(i);
                if (newHead.x() == (((int) currFoodElem.getCenterX() + 25) / 50 - 1)
                        && newHead.y() == ((int) currFoodElem.getCenterY() + 25) / 50 - 1) {
                    this.rootGame.getChildren().remove(currFoodElem);
                    currFood.incCount();
                    totalScore.getAndIncrement();
                    totalScoreText.setText("Total score: " + totalScore.get());
                    snake.growSnake(newHead);
                    currFood.updateFoodText();
                    if (this.food.get("Yellow").getCount() >= 5
                            && this.food.get("Blue").getCount() >= 5
                            && this.food.get("Orange").getCount() >= 5) {
                        this.win.set(true);
                    }
                    this.generateFood(key);
                    currFood.removeFood(currFoodElem);
                    return;
                }
            }
        }

        if (snake.isSnake(newHead) || this.isObstacle(newHead)) {
            this.gameOver.set(true);
            return;
        }

        field[snake.getTail().x()][snake.getTail().y()].setFill(Color.LIGHTGREEN);
        snake.changeSnake(newHead);
    }

    /**
     * Checking that the giving coordinate is an obstacle.
     *
     * @param c coordinate to check
     * @return obstacle or not
     */
    private boolean isObstacle(Coords c) {
        for (Coords element : this.obstacles) {
            if (element.equals(c)) {
                return true;
            }
        }
        return false;
    }
}
