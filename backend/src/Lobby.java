
// ================================== NOTES  ================================== //
// -> There are two types of players: 1. Player 2. PlayerNPC. 
// When a player makes a guess and a character needs to be moved, this needs to 
// be accounted for
//
// ->
// ============================================================================ //
import java.util.*;

public class Lobby {
    // The player whose turn it currently is
    private Player currentPlayerTurn;

    // A global clientHandlers list, this list is the clientHandlers that this class
    // is constructed with
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    // The following are three lists of available cards to choose from, cards get
    // removed as they are assigned to players
    private ArrayList<String> characters = new ArrayList<String>(Arrays.asList("Miss Scarlet", "Colonel Mustard",
            "Mrs. White", "Mr. Green", "Mrs. Peacock", "Professor Plum"));
    private ArrayList<String> weapons = new ArrayList<String>(
            Arrays.asList("Knife", "Revolver", "Rope", "Wrench", "Candle Stick", "Lead Pipe"));
    private ArrayList<String> rooms = new ArrayList<String>(
            Arrays.asList("Study", "Hall", "Lounge", "Library", "Billiard Room", "Dining Room",
                    "Conservatory", "Ballroom", "Kitchen"));

    // Not mutated list that just keep track of general game informations
    private ArrayList<int[]> startingPositions = new ArrayList<>(
            Arrays.asList(new int[] { 665, 33 }, new int[] { 40, 215 }, new int[] { 890, 215 }, new int[] { 40, 515 },
                    new int[] { 265, 690 }, new int[] { 665, 690 }));
    private ArrayList<String> charactersImmutable = new ArrayList<String>(
            Arrays.asList("Miss Scarlet", "Colonel Mustard",
                    "Mrs. White", "Mr. Green", "Mrs. Peacock", "Professor Plum"));
    
    // A boolean to determine if the current player has made a valid move, gets changed by the endTurn() function
    private boolean didMove = false;

    // A boolean to determine if the current player has made a valid guess, gets changed by the endTurn() function
    private boolean didGuess = false;

    // The turn order for the connection clients, turns match the indexes of the
    // clientHandlers ArrayList
    public Player[] turnOrder = new Player[6];

    // A list of potential Non-Playable-Characters when the connected clients are
    // less than 6, i.e 4 people are playing
    public PlayerNPC[] npcList = new PlayerNPC[4];

    // The character, room & weapon that win the game if guessed
    public String[] winCondition = new String[3];

    // A mapping of each clienthandler its respective Player class
    public static HashMap<ClientHandler, Player> clientHandlersPlayerMap = new HashMap<>();

    public Lobby(ArrayList<ClientHandler> clientHandlersList) {
        clientHandlers = clientHandlersList;
        startGame();
    }

    // Starts the game of Clueless
    private void startGame() {
        // Assign win condition and remove the items from their respective lists
        Random rand = new Random();
        int characterIndex = rand.nextInt(5);
        winCondition[0] = characters.get(characterIndex);
        characters.remove(characterIndex);
        int weaponIndex = rand.nextInt(5);
        winCondition[1] = weapons.get(weaponIndex);
        weapons.remove(weaponIndex);
        int roomIndex = rand.nextInt(8);
        winCondition[2] = rooms.get(roomIndex);
        rooms.remove(roomIndex);

        // Call createPlayers to make a Player class for each user
        createPlayers();

        // Set currentPlayer to first players turn
        currentPlayerTurn = turnOrder[0];


        // Messsage the first player that it's there turn
        // Message the rest of the players that the game has started and the turn order

        // TODO: Debug message
        System.out.println("Game has started!");
    }

    // private void endGame() {
    // }

    

    // Creates players for each client and playernpcs for the remaining characters
    private void createPlayers() {
        // Create a random list of cards not in win condition
        ArrayList<String> cards = new ArrayList<String>();
        for (String s : characters)
            cards.add(s);
        for (String s : weapons)
            cards.add(s);
        for (String s : rooms)
            cards.add(s);
        Collections.shuffle(cards);

        // Create clientHandler number of hands, this way we can dynamically adjust
        // Hand size based on the number of players playing
        HashMap<Integer, ArrayList<String>> hands = new HashMap<>();
        int numPlayers = clientHandlers.size();
        for (int i = 0; i < 18; i++) {
            int handIndex = i % numPlayers;
            if (hands.containsKey(handIndex)) {
                ArrayList<String> val = hands.get(handIndex);
                val.add(cards.get(i));
                hands.put(handIndex, val);
            } else {
                ArrayList<String> val = new ArrayList<>(Arrays.asList(cards.get(i)));
                hands.put(handIndex, val);
            }
        }

        // Create player classes for each clienthandler
        for (int i = 0; i < numPlayers; i++) {
            ClientHandler currClientHandler = clientHandlers.get(i);
            Player newPlayer = new Player(
                    // playerName -> assign get username in clienthandler
                    currClientHandler.getUsername(),

                    // characterName -> hard coded, matches the order of clienthandlers
                    currClientHandler.getCharacter(),

                    // cards -> hard coded here but random, assign each player cards not in
                    // winCondition
                    hands.get(i),

                    // startPosition -> clienthandler starting position
                    currClientHandler.getStartingPosition(),

                    // clientHandler
                    currClientHandler);

            // map clienthandlers to players in the map
            clientHandlersPlayerMap.put(currClientHandler, newPlayer);

            // add each player to the turn order array
            turnOrder[i] = newPlayer;

            // TODO: Debug message
            System.out.println(newPlayer.getPlayerName() + " is playing " + newPlayer.getCharacterName()
                    + ", they have the following cards: " + hands.get(i) + "\n");
        }

        // create playerNPC classes for the remainding spots if less then 6 players are
        // playing
        int index = numPlayers;
        int end = 6 - numPlayers;
        for (int i = 0; i < end; i++) {
            PlayerNPC newPlayerNPC = new PlayerNPC(charactersImmutable.get(index), startingPositions.get(index));
            npcList[i] = newPlayerNPC;
            index++;

            // TODO: Debug message
            System.out.println("The computer is playing " + newPlayerNPC.getCharacterName() + "\n");
        }

    }

    private void createWeapons() {
        // make the six weapon classes with default position
    }

    private void createRoom() {
        // make the nine ro
    }

    private Boolean isValidMove() {
        // check that the player trying to move is the currentplayer
        // check hashmap for valid moves
        return true;
    }

    public void sendMessage(String message, ClientHandler[] recievers) {
        // hardcode game messages into specific message formats
        // tell specific clientHandlers to send a message to their clients
    }

    public void makeGuess(String guess) {
        // Ensure they didn't guess already
        if(didGuess) {
            // Send message to the current player that they already guess this turn

            // Leave the function
            return;
        }

        // Update didGuess for the current player
        didGuess = true;
    }

    public void makeAccusation(String guess) {
    }

    public void makeMove(ClientHandler clientHandler) {
        // Ensure they didn't move already
        if(didMove) {
            // Send message to the current player that they already moved this turn

            // Leave the function
            return;
        }

        // Check if move is valid
        if (isValidMove()) {
            // Update player position in player class
            // Send message to player gameboards to move this players square
            // Tell clienthandler to message it's client to move the character to a new
            // Tell other players of move, call sendMessage

            // Update didMove to true for the current player
            didMove = true;
        } else {
            // Send message to the current player that they made an invalid move and to try again 
        }
    }

    private void endTurn() {
        // Set didMove and didGuess to false
        didMove = false;
        didGuess = false;

        // Update the current player to the next player
        // currentCharactersTurn
    }

    public String getCurrentPlayerTurn() {
        return currentPlayerTurn.getPlayerName();
    }

    public ArrayList<String> getPlayerCards(Player player) {
        return player.getCards();
    }
}
