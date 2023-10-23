import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;

public class App extends Application {
    Stage window;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Gameboard.fxml"));
            Scene scene = new Scene(root, 1000, 1000);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket("localhost", 1234);
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Enter your username: ");
                        String username = scanner.nextLine();
                        Client client = new Client(socket, username);

                        // blocking method on its own thread
                        client.listenForMessage();
                        // blocking method on its own thread
                        client.sendMsg();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            primaryStage.setTitle("Clueless");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
