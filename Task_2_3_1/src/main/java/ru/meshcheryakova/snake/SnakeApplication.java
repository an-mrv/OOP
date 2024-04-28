package ru.meshcheryakova.snake;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.stage.Stage;

import static javafx.scene.input.KeyCode.*;

/**
 * Main class for 'Snake game' application.
 * Rules: The goal of the game is to eat the amount of food set on the side menu of each color.
 * When the snake collides with an obstacle or with its body, the game ends.
 * The initial length of the snake is 1.
 */
public class SnakeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Image img = new Image(
                new FileInputStream("src/main/resources/ru/meshcheryakova/snake/snake.png"));
        stage.getIcons().add(img);
        stage.setTitle("Snake game");

        Group rootStart = new Group();
        Scene scene = new Scene(rootStart, 1200, 800);

        startMenuInit(rootStart, scene);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization of the menu that appears at the beginning of the game.
     *
     * @param rootStart group node for start menu
     * @param scene scene of the app
     */
    private void startMenuInit(Group rootStart, Scene scene) throws FileNotFoundException {
        rootStart.getChildren().add(new Rectangle(1200, 800, Color.LIGHTGREEN));

        Rectangle rect = new Rectangle(600, 700, Color.DARKGREEN);
        rect.setX(300);
        rect.setY(50);

        Text welcomeText = new Text(350, 150, "Welcome to the Snake game!");
        welcomeText.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        welcomeText.setFill(Color.LIGHTGREEN);

        Image image = new Image(
                new FileInputStream("src/main/resources/ru/meshcheryakova/snake/snake.png"),
                200, 200, false, true);
        ImageView imageView = new ImageView(image);
        imageView.setX(500);
        imageView.setY(500);

        Button startGame = new Button("Start game");
        startGame.setPrefSize(200, 100);
        startGame.setLayoutX(500);
        startGame.setLayoutY(200);
        startGame.setStyle("-fx-font-size: 30; -fx-background-color: #90ee90; "
                + "-fx-text-fill: #006400;");

        Button exit1 = new Button("Exit");
        exit1.setPrefSize(200, 100);
        exit1.setLayoutX(500);
        exit1.setLayoutY(350);
        exit1.setStyle("-fx-font-size: 35; -fx-background-color: #90ee90; -fx-text-fill: #006400;");
        exit1.setOnAction(value -> {
            Stage stage2 = (Stage) exit1.getScene().getWindow();
            stage2.close();
        });

        startGame.setOnAction(value -> newGame(scene));

        rootStart.getChildren().addAll(rect, welcomeText, imageView, startGame, exit1);
    }

    /**
     * Starting new game.
     *
     * @param scene scene of the app
     */
    private void newGame(Scene scene) {
        Group rootGame = new Group();
        scene.setRoot(rootGame);

        Game game = new Game(rootGame);

        rootGame.requestFocus();
        rootGame.setOnKeyPressed(event -> {
            if (event.getCode() == RIGHT || event.getCode() == LEFT
                    || event.getCode() == UP || event.getCode() == DOWN) {
                if (!((event.getCode() == RIGHT && game.lastPressedKey.get() == LEFT)
                        || (event.getCode() == LEFT && game.lastPressedKey.get() == RIGHT)
                        || (event.getCode() == UP && game.lastPressedKey.get() == DOWN)
                        || (event.getCode() == DOWN && game.lastPressedKey.get() == UP))) {
                    game.lastPressedKey.set(event.getCode());
                }
            }
        });

        Group rootGameOver = new Group();
        Text scoreForGameOver = new Text();
        Button restartGame1 = new Button("Restart game");
        Button exit1 = new Button("Exit");
        gameOverMenuInit(rootGameOver, scoreForGameOver, restartGame1, exit1);

        Group rootWin = new Group();
        Text scoreForWin = new Text();
        Button restartGame2 = new Button("Restart game");
        Button exit2 = new Button("Exit");
        winMenuInit(rootWin, scoreForWin, restartGame2, exit2);

        Thread gameLogic = new Thread(() -> {
            Runnable updater = game::move;
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                Platform.runLater(updater);
            }
        });

        Thread gameOverCheck = new Thread(() -> {
            Runnable updater = () -> {
                if (game.gameOver.get()) {
                    scoreForGameOver.setText("Your score: " + game.totalScore.get());
                    scene.setRoot(rootGameOver);
                }
            };
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                Platform.runLater(updater);
            }
        });

        Thread winCheck = new Thread(() -> {
            Runnable updater = () -> {
                if (game.win.get()) {
                    scoreForWin.setText("Your score: " + game.totalScore.get());
                    scene.setRoot(rootWin);
                }
            };
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                Platform.runLater(updater);
            }
        });

        gameLogic.start();
        gameOverCheck.start();
        winCheck.start();

        restartGame1.setOnAction(value ->  {
            game.restartGame();
            scene.setRoot(rootGame);
            rootGame.requestFocus();
        });

        restartGame2.setOnAction(value ->  {
            game.restartGame();
            scene.setRoot(rootGame);
            rootGame.requestFocus();
        });

        exit1.setOnAction(value ->  {
            gameLogic.interrupt();
            gameOverCheck.interrupt();
            winCheck.interrupt();
            Stage stage2 = (Stage) exit1.getScene().getWindow();
            stage2.close();
        });

        exit2.setOnAction(value ->  {
            gameLogic.interrupt();
            gameOverCheck.interrupt();
            winCheck.interrupt();
            Stage stage2 = (Stage) exit2.getScene().getWindow();
            stage2.close();
        });
    }

    /**
     * Initialization of the menu that appears when you lose.
     *
     * @param rootGameOver group node for game over menu
     * @param scoreForGameOver an inscription with final score
     * @param restartGame button to restart game
     * @param exit button to exit from the game
     */
    private void gameOverMenuInit(Group rootGameOver, Text scoreForGameOver,
                                  Button restartGame, Button exit) {
        rootGameOver.getChildren().add(new Rectangle(1200, 800, Color.LIGHTGREEN));

        scoreForGameOver.setX(465);
        scoreForGameOver.setY(250);
        scoreForGameOver.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        scoreForGameOver.setFill(Color.LIGHTGREEN);

        Text textGameOver = new Text(450, 150, "Game over!");
        textGameOver.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        textGameOver.setFill(Color.LIGHTGREEN);

        Rectangle rect = new Rectangle(600, 700, Color.DARKGREEN);
        rect.setX(300);
        rect.setY(50);

        restartGame.setPrefSize(300, 150);
        restartGame.setLayoutX(460);
        restartGame.setLayoutY(300);
        restartGame.setStyle("-fx-font-size: 35; -fx-background-color: #90ee90; "
                + "-fx-text-fill: #006400;");

        exit.setPrefSize(300, 150);
        exit.setLayoutX(460);
        exit.setLayoutY(500);
        exit.setStyle("-fx-font-size: 35; -fx-background-color: #90ee90; -fx-text-fill: #006400;");

        rootGameOver.getChildren().addAll(rect, textGameOver, scoreForGameOver, restartGame, exit);
    }

    /**
     * Initialization of the menu that appears when you win.
     *
     * @param rootWin group node for win menu
     * @param scoreForWin an inscription with final score
     * @param restartGame button to restart game
     * @param exit button to exit from the game
     */
    private void winMenuInit(Group rootWin, Text scoreForWin, Button restartGame, Button exit) {
        rootWin.getChildren().add(new Rectangle(1200, 800, Color.LIGHTGREEN));

        scoreForWin.setX(465);
        scoreForWin.setY(250);
        scoreForWin.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        scoreForWin.setFill(Color.LIGHTGREEN);

        Text textWin = new Text(450, 150, "You are win!");
        textWin.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        textWin.setFill(Color.LIGHTGREEN);

        Rectangle rect = new Rectangle(600, 700, Color.DARKGREEN);
        rect.setX(300);
        rect.setY(50);

        restartGame.setPrefSize(300, 150);
        restartGame.setLayoutX(460);
        restartGame.setLayoutY(300);
        restartGame.setStyle("-fx-font-size: 35; -fx-background-color: #90ee90; "
                + "-fx-text-fill: #006400;");

        exit.setPrefSize(300, 150);
        exit.setLayoutX(460);
        exit.setLayoutY(500);
        exit.setStyle("-fx-font-size: 35; -fx-background-color: #90ee90; -fx-text-fill: #006400;");

        rootWin.getChildren().addAll(rect, textWin, restartGame, exit, scoreForWin);
    }

    public static void main(String[] args) {
        launch();
    }
}