import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.*;
import javafx.stage.Stage;
import java.util.*;
import java.io.IOException;
import java.net.Socket;

public class App extends Application {
    Stage window;

    @FXML
    private Rectangle missscarlet;
    @FXML
    private Rectangle colmustard;
    @FXML
    private Rectangle profplum;
    @FXML
    private Rectangle mrspeakcock;
    @FXML
    private Rectangle mrgreen;
    @FXML
    private Rectangle mrswhite;

    @FXML
    private TextField roomTextinput;
    @FXML
    private TextField positionTextinput;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Gameboard.fxml"));
            Scene scene = new Scene(root, 980, 1080);

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

    @FXML
    public void moveCharacter() {
        setMissscarletPosition(roomTextinput.getText(),
                Integer.parseInt(positionTextinput.getText()));
    }

    @FXML
    private void setMissscarletPosition(String room, int pos) {
        int[] location = getPosition(room, pos);
        missscarlet.setLayoutX(location[0]);
        missscarlet.setLayoutY(location[1]);
    }

    @FXML
    private void setColonelmustardPosition(String room, int pos) {
        int[] location = getPosition(room, pos);
        colmustard.setLayoutX(location[0]);
        colmustard.setLayoutY(location[1]);
    }

    @FXML
    private void setProfessorplumPosition(String room, int pos) {
        int[] location = getPosition(room, pos);
        profplum.setLayoutX(location[0]);
        profplum.setLayoutY(location[1]);
    }

    @FXML
    private void setMrspeacockPosition(String room, int pos) {
        int[] location = getPosition(room, pos);
        mrspeakcock.setLayoutX(location[0]);
        mrspeakcock.setLayoutY(location[1]);
    }

    @FXML
    private void setMrgreenPosition(String room, int pos) {
        int[] location = getPosition(room, pos);
        mrgreen.setLayoutX(location[0]);
        mrgreen.setLayoutY(location[1]);
    }

    @FXML
    private void setMrswhitePosition(String room, int pos) {
        int[] location = getPosition(room, pos);
        mrswhite.setLayoutX(location[0]);
        mrswhite.setLayoutY(location[1]);
    }

    private int[] getPosition(String room, int pos) {
        Map<Integer, int[]> study = new HashMap<Integer, int[]>() {
            {
                put(0, new int[] { 15, 0 });
                put(1, new int[] { 65, 0 });
                put(2, new int[] { 115, 0 });
                put(3, new int[] { 15, 50 });
                put(4, new int[] { 65, 50 });
                put(5, new int[] { 115, 50 });
                put(6, new int[] { 15, 150 });
                put(7, new int[] { 65, 150 });
                put(8, new int[] { 115, 150 });
            }
        };

        Map<Integer, int[]> hall = new HashMap<Integer, int[]>() {
            {
                put(0, new int[] { 415, 0 });
                put(1, new int[] { 465, 0 });
                put(2, new int[] { 515, 0 });
                put(3, new int[] { 415, 50 });
                put(4, new int[] { 465, 50 });
                put(5, new int[] { 515, 50 });
                put(6, new int[] { 415, 150 });
                put(7, new int[] { 465, 150 });
                put(8, new int[] { 515, 150 });
            }
        };

        Map<Integer, int[]> lounge = new HashMap<Integer, int[]>() {
            {
                put(0, new int[] { 815, 0 });
                put(1, new int[] { 865, 0 });
                put(2, new int[] { 915, 0 });
                put(3, new int[] { 815, 50 });
                put(4, new int[] { 865, 50 });
                put(5, new int[] { 915, 50 });
                put(6, new int[] { 815, 150 });
                put(7, new int[] { 865, 150 });
                put(8, new int[] { 915, 150 });
            }
        };

        Map<Integer, int[]> library = new HashMap<Integer, int[]>() {
            {
                put(0, new int[] { 15, 300 });
                put(1, new int[] { 65, 300 });
                put(2, new int[] { 115, 300 });
                put(3, new int[] { 15, 350 });
                put(4, new int[] { 65, 350 });
                put(5, new int[] { 115, 350 });
                put(6, new int[] { 15, 400 });
                put(7, new int[] { 65, 400 });
                put(8, new int[] { 115, 400 });
            }
        };

        Map<Integer, int[]> billiardroom = new HashMap<Integer, int[]>() {
            {
                put(0, new int[] { 415, 300 });
                put(1, new int[] { 465, 300 });
                put(2, new int[] { 515, 300 });
                put(3, new int[] { 415, 350 });
                put(4, new int[] { 465, 350 });
                put(5, new int[] { 515, 350 });
                put(6, new int[] { 415, 400 });
                put(7, new int[] { 465, 400 });
                put(8, new int[] { 515, 400 });
            }
        };

        Map<Integer, int[]> diningroom = new HashMap<Integer, int[]>() {
            {
                put(0, new int[] { 815, 300 });
                put(1, new int[] { 865, 300 });
                put(2, new int[] { 915, 300 });
                put(3, new int[] { 815, 350 });
                put(4, new int[] { 865, 350 });
                put(5, new int[] { 915, 350 });
                put(6, new int[] { 815, 400 });
                put(7, new int[] { 865, 400 });
                put(8, new int[] { 915, 400 });
            }
        };

        Map<Integer, int[]> conservatory = new HashMap<Integer, int[]>() {
            {
                put(0, new int[] { 15, 600 });
                put(1, new int[] { 65, 600 });
                put(2, new int[] { 115, 600 });
                put(3, new int[] { 15, 650 });
                put(4, new int[] { 65, 650 });
                put(5, new int[] { 115, 650 });
                put(6, new int[] { 15, 700 });
                put(7, new int[] { 65, 700 });
                put(8, new int[] { 115, 700 });
            }
        };

        Map<Integer, int[]> ballroom = new HashMap<Integer, int[]>() {
            {
                put(0, new int[] { 415, 600 });
                put(1, new int[] { 465, 600 });
                put(2, new int[] { 515, 600 });
                put(3, new int[] { 415, 650 });
                put(4, new int[] { 465, 650 });
                put(5, new int[] { 515, 650 });
                put(6, new int[] { 415, 700 });
                put(7, new int[] { 465, 700 });
                put(8, new int[] { 515, 700 });
            }
        };

        Map<Integer, int[]> kitchen = new HashMap<Integer, int[]>() {
            {
                put(0, new int[] { 815, 600 });
                put(1, new int[] { 865, 600 });
                put(2, new int[] { 915, 600 });
                put(3, new int[] { 815, 650 });
                put(4, new int[] { 865, 650 });
                put(5, new int[] { 915, 650 });
                put(6, new int[] { 815, 700 });
                put(7, new int[] { 865, 700 });
                put(8, new int[] { 915, 700 });
            }
        };

        Map<Integer, int[]> hallway = new HashMap<Integer, int[]>() {
            {
                put(0, new int[] { 256, 33 });
                put(1, new int[] { 665, 33 });
                put(2, new int[] { 40, 215 });
                put(3, new int[] { 465, 215 });
                put(4, new int[] { 890, 215 });
                put(5, new int[] { 265, 365 });
                put(6, new int[] { 665, 365 });
                put(7, new int[] { 40, 515 });
                put(8, new int[] { 465, 515 });
                put(9, new int[] { 890, 515 });
                put(10, new int[] { 265, 690 });
                put(11, new int[] { 665, 690 });
            }
        };

        Map<String, Map<Integer, int[]>> positionMap = new HashMap<String, Map<Integer, int[]>>() {
            {
                put("study", study);
                put("hall", hall);
                put("lounge", lounge);
                put("library", library);
                put("billiardroom", billiardroom);
                put("diningroom", diningroom);
                put("conservatory", conservatory);
                put("ballroom", ballroom);
                put("kitchen", kitchen);
                put("hallway", hallway);
            }
        };

        return new int[] { positionMap.get(room).get(pos)[0], positionMap.get(room).get(pos)[1] };
    }
}
