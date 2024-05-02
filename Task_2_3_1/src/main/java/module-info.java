module ru.meshcheryakova.snake {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens ru.meshcheryakova.snake to javafx.fxml;
    exports ru.meshcheryakova.snake;
}