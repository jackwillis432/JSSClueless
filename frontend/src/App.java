import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {
    Stage window;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("My First JavaFX Project");

        Button button = new Button("Button");

        VBox layout = new VBox();
        layout.getChildren().addAll(button);
        Scene scene = new Scene(layout, 600, 600);
        window.setScene(scene);
        window.show();
    }
}
