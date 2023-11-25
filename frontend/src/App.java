import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.*;
import javafx.stage.Stage;
import java.util.*;
import java.io.IOException;
import java.net.Socket;

// 20231118
public class App extends Application {
    Stage window;

    @FXML
    private Rectangle missscarlet;
    @FXML
    private Rectangle colmustard;
    @FXML
    private Rectangle profplum;
    @FXML
    private Rectangle mrspeacock;
    @FXML
    private Rectangle mrgreen;
    @FXML
    private Rectangle mrswhite;

    @FXML
    private TextField roomTextinput;
    @FXML
    private TextField positionTextinput;

    @FXML
    private VBox characterVbox;
    @FXML
    private VBox weaponVbox;
    @FXML
    private VBox roomVbox;
    @FXML
    private VBox cardVbox;

    @FXML
    private RadioButton missScarletRadioButton;
    @FXML
    private RadioButton colMustardRadioButton;
    @FXML
    private RadioButton professorPlumRadioButton;
    @FXML
    private RadioButton mrsPeacockRadioButton;
    @FXML
    private RadioButton mrGreenRadioButton;
    @FXML
    private RadioButton mrsWhiteRadioButton;

    @FXML
    private RadioButton knifeRadioButton;
    @FXML
    private RadioButton wrenchRadioButton;
    @FXML
    private RadioButton candleStickRadioButton;
    @FXML
    private RadioButton revolverRadioButton;
    @FXML
    private RadioButton ropeRadioButton;
    @FXML
    private RadioButton leadPipeRadioButton;

    @FXML
    private RadioButton studyRadioButton;
    @FXML
    private RadioButton hallRadioButton;
    @FXML
    private RadioButton loungeRadioButton;
    @FXML
    private RadioButton libraryRadioButton;
    @FXML
    private RadioButton billiardRoomRadioButton;
    @FXML
    private RadioButton diningRoomRadioButton;
    @FXML
    private RadioButton conservatoryRadioButton;
    @FXML
    private RadioButton ballroomRadioButton;
    @FXML
    private RadioButton kitchenRadioButton;

    @FXML
    private TextArea notificationBox;
    @FXML
    private Label characterNameBox;
    @FXML
    private Label characterTurnBox;
    @FXML
    private Rectangle characterColorBox;

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

    // Main Game Helper Functions

    // Calls the functions setCards, setCharacterNameBox, setCharacterColorBox,
    // setCharacterTurnbox
    // Should be called by Lobby.java at the beginning of the game to set each
    // players
    // current turn box, cards, character color box and character name box
    private void initializePlayer(String[] cards, String character, String currentCharactersTurn) {
        // This function should be called by the Lobby class when a game starts
        setCards(cards);
        setCharacterColorBox(character);
        setCharacterNameBox(character);
        setCharacterTurnBox(currentCharactersTurn);
    }

    @FXML
    // Sets the list of cards on the players UI
    private void setCards(String[] cards) {
        for (String card : cards) {
            cardVbox.getChildren().addAll(new Text(card));
        }
    }

    @FXML
    // Sets character turn box to the current player (characters) turn
    private void setCharacterTurnBox(String character) {
        characterTurnBox.setText(character + "'s Turn");
    }

    @FXML
    // Sets the large character name on the Players UI
    private void setCharacterNameBox(String character) {
        characterNameBox.setText(character);
    }

    @FXML
    // Sets the large character color box on the Players UI
    private void setCharacterColorBox(String character) {
        Color color;

        if (character.equals("Miss Scarlet")) {
            color = Color.web("#ff2203");
        } else if (character.equals("Colonel Mustard")) {
            color = Color.web("#ffc805");
        } else if (character.equals("Mrs. White")) {
            color = Color.web("#fffffe");
        } else if (character.equals("Mr. Green")) {
            color = Color.web(" #139405");
        } else if (character.equals("Mrs. Peacock")) {
            color = Color.web("#0518ff");
        } else if (character.equals("Professor Plum")) {
            color = Color.web("#c805ff");
        } else {
            color = Color.web("#ffffff");
        }

        characterColorBox.setFill(color);
    }

    @FXML
    // Bound to the moveLeft button on the UI, calls makeMove
    private void moveLeft() {
        System.out.println("Left Move Button Clicked");
        makeMove("Left");
    }

    @FXML
    // Bound to the moveRight button on the UI, calls makeMove
    private void moveRight() {
        System.out.println("Right Move Button Clicked");
        makeMove("Right");
    }

    @FXML
    // Bound to the moveUp button on the UI, calls makeMove
    private void moveUp() {
        System.out.println("Up Move Button Clicked");
        makeMove("Up");
    }

    @FXML
    // Bound to the moveDown button on the UI, calls makeMove
    private void moveDown() {
        System.out.println("Down Move Button Clicked");
        makeMove("Down");
    }

    @FXML
    // Disables other character radio buttons when one is selected
    // Enables other character radio buttons when one is unselected
    public void characterRadioButtonSelected() {
        boolean itemSelected = false;
        for (Node n : characterVbox.getChildren()) {
            RadioButton r = (RadioButton) n;
            if (r.isSelected()) {
                itemSelected = true;
            }
        }
        if (itemSelected) {
            for (Node n : characterVbox.getChildren()) {
                RadioButton r = (RadioButton) n;
                if (!r.isSelected()) {
                    r.setDisable(true);
                }
            }
        } else {
            for (Node n : characterVbox.getChildren()) {
                RadioButton r = (RadioButton) n;
                r.setDisable(false);
            }
        }
    }

    @FXML
    // Disables other weapon radio buttons when one is selected
    // Enables other weapon radio buttons when one is unselected
    public void weaponRadioButtonSelected() {
        boolean itemSelected = false;
        for (Node n : weaponVbox.getChildren()) {
            RadioButton r = (RadioButton) n;
            if (r.isSelected()) {
                itemSelected = true;
            }
        }
        if (itemSelected) {
            for (Node n : weaponVbox.getChildren()) {
                RadioButton r = (RadioButton) n;
                if (!r.isSelected()) {
                    r.setDisable(true);
                }
            }
        } else {
            for (Node n : weaponVbox.getChildren()) {
                RadioButton r = (RadioButton) n;
                r.setDisable(false);
            }
        }
    }

    @FXML
    // Disables other room radio buttons when one is selected
    // Enables other room weapon radio buttons when one is unselected
    public void roomRadioButtonSelected() {
        boolean itemSelected = false;
        for (Node n : roomVbox.getChildren()) {
            RadioButton r = (RadioButton) n;
            if (r.isSelected()) {
                itemSelected = true;
            }
        }
        if (itemSelected) {
            for (Node n : roomVbox.getChildren()) {
                RadioButton r = (RadioButton) n;
                if (!r.isSelected()) {
                    r.setDisable(true);
                }
            }
        } else {
            for (Node n : roomVbox.getChildren()) {
                RadioButton r = (RadioButton) n;
                r.setDisable(false);
            }
        }
    }

    @FXML
    // Gets a string array for each [ character, weapon, room ] radio button
    // selected
    public String[] getRadioSelection() {
        String characterSelected;
        String roomSelected;
        String weaponSelected;

        if (missScarletRadioButton.isSelected()) {
            characterSelected = "Miss Scarlet";
        } else if (colMustardRadioButton.isSelected()) {
            characterSelected = "Colonel Mustard";
        } else if (professorPlumRadioButton.isSelected()) {
            characterSelected = "Professor Plum";
        } else if (mrsPeacockRadioButton.isSelected()) {
            characterSelected = "Mrs. Peacock";
        } else if (mrGreenRadioButton.isSelected()) {
            characterSelected = "Mr. Green";
        } else if (mrsWhiteRadioButton.isSelected()) {
            characterSelected = "Mrs. White";
        } else {
            characterSelected = "none";
        }

        if (knifeRadioButton.isSelected()) {
            weaponSelected = "Knife";
        } else if (wrenchRadioButton.isSelected()) {
            weaponSelected = "Wrench";
        } else if (candleStickRadioButton.isSelected()) {
            weaponSelected = "Candle Stick";
        } else if (revolverRadioButton.isSelected()) {
            weaponSelected = "Revolver";
        } else if (ropeRadioButton.isSelected()) {
            weaponSelected = "Rope";
        } else if (leadPipeRadioButton.isSelected()) {
            weaponSelected = "Lead Pipe";
        } else {
            weaponSelected = "none";
        }

        if (studyRadioButton.isSelected()) {
            roomSelected = "Study";
        } else if (hallRadioButton.isSelected()) {
            roomSelected = "Hall";
        } else if (loungeRadioButton.isSelected()) {
            roomSelected = "Lounge";
        } else if (libraryRadioButton.isSelected()) {
            roomSelected = "Library";
        } else if (billiardRoomRadioButton.isSelected()) {
            roomSelected = "Billiard Room";
        } else if (diningRoomRadioButton.isSelected()) {
            roomSelected = "Dining Room";
        } else if (conservatoryRadioButton.isSelected()) {
            roomSelected = "Conservatory";
        } else if (ballroomRadioButton.isSelected()) {
            roomSelected = "Ballroom";
        } else if (kitchenRadioButton.isSelected()) {
            roomSelected = "Kitchen";
        } else {
            roomSelected = "none";
        }

        return new String[] { characterSelected, roomSelected, weaponSelected };
    }

    // Main Game Functions

    @FXML
    // Allows a player to make a guess
    private void makeGuess() {
        String[] selection = getRadioSelection();
        System.out.println("The guess is: " + Arrays.toString(selection));
        // Kick off guessing logic to server here

        String[] cards = new String[] { "Card 1", "Card 2", "Card 3", "Card 4", "Card 5" };
        String characterName = "Colonel Mustard";
        String characterTurn = "Miss Scarlet";
        initializePlayer(cards, characterName, characterTurn);
    }

    @FXML
    // Allows a player to make an accusation
    private void makeAccusation() {
        String[] selection = getRadioSelection();
        System.out.println("The accusation is: " + Arrays.toString(selection));
        // Kick off accusation logic to server here
    }

    @FXML
    // Allows a player to move their character around the map
    private void makeMove(String direction) {
        // This function is called by moveLeft, moveRight, moveUp & moveDown buttons

        // Move logic to server goes here

        // If moved then call set<Character>Position function here
    }

    @FXML
    // Sets the notification box on the playersUI
    private void setNotificationBox(String message) {
        notificationBox.setText("Notification Message: " + message);
    }

    @FXML
    // Allows a player to skip their turn
    private void passOnTurn() {
        // Logic for passing on you turn
        System.out.println("Pass on Turn Button Clicked");
    }

    @FXML
    // Allows a player to answer a guess directed towards them
    private void answerGuess() {
        // Logic for answering a guess proposed by another player
        String[] selection = getRadioSelection();
        System.out.println("Your answer is: " + Arrays.toString(selection));
        System.out.println("Answer Guess Button Clicked");
    }

    @FXML
    // Allows a player to deny a guess directed towards them (i.e the don't have any
    // cards in the guess)
    private void denyGuess() {
        // Logic for denying all parts of a guess
        System.out.println("Deny Guess Button Clicked");
    }

    // Move Functions

    // @FXML
    // // Test function
    // private void moveCharacter() {
    // setMissscarletPosition(roomTextinput.getText(),
    // Integer.parseInt(positionTextinput.getText()));
    // }

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
        mrspeacock.setLayoutX(location[0]);
        mrspeacock.setLayoutY(location[1]);
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
                put("Study", study);
                put("Hall", hall);
                put("Lounge", lounge);
                put("Library", library);
                put("Billiard Room", billiardroom);
                put("Dining Room", diningroom);
                put("Conservatory", conservatory);
                put("Ballroom", ballroom);
                put("Kitchen", kitchen);
                put("Hallway", hallway);
            }
        };

        return new int[] { positionMap.get(room).get(pos)[0], positionMap.get(room).get(pos)[1] };
    }

}
