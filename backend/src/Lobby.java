import java.util.*;

public class Lobby {
    private Player currentPlayerTurn;
    private ClientHandler[] clientHandlers;
    private ArrayList<String> characters = new ArrayList<String>(Arrays.asList("Miss Scarlet", "Colonel Mustard",
            "Mrs. White", "Mr. Green", "Mrs. Peacock", "Professor Plum"));
    private ArrayList<String> weapons = new ArrayList<String>(
            Arrays.asList("Knife", "Revolver", "Rope", "Wrench", "Candle Stick", "Lead Pipe"));
    private ArrayList<String> rooms = new ArrayList<String>(
            Arrays.asList("study", "hall", "lounge", "library", "billiardroom", "diningoom",
                    "conservatory", "ballroom", "kitchen", "hallway"));
    public Player[] turnOrder;
    public String[] winCondition;
    public static HashMap<ClientHandler, Player> clientHandlersPlayerMap = new HashMap<>();

    public Lobby(ClientHandler[] clientHandlers) {
        this.clientHandlers = clientHandlers;
        startGame();
    }

    private void startGame() {
        // assign win condition and remove the items from their respective lists
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

        // call createPlayers
        createPlayers();

        // set currentPlayer to first players turn
        currentPlayerTurn = turnOrder[0];

        // msg the first player that it's there turn
        // msg the rest of the players that the game has started and the turn order

        // debug message
        System.out.println("Game has started!!!");
    }

    // private void endGame() {
    // }

    private void createPlayers() {
        // create a random list of cards not in win condition
        ArrayList<String> cards = new ArrayList<String>();
        for (String s : characters)
            cards.add(s);
        for (String s : weapons)
            cards.add(s);
        for (String s : rooms)
            cards.add(s);
        Collections.shuffle(cards);

        // create clientHandler number of hands, this way we can dynamically adjust
        // hand size based on the number of players playing
        HashMap<Integer, ArrayList<String>> hands = new HashMap<>();
        int numPlayers = clientHandlers.length;
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

        // create player classes for each clienthandler
        for (int i = 0; i < clientHandlers.length; i++) {
            ClientHandler currClientHandler = clientHandlers[i];
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
            turnOrder[i] = newPlayer;
        }

        // map clienthandlers to players in the map
        // add each player to the turn order array
    }

    // private void createWeapons() {
    // }

    // private void createRoom() {
    // }

    // private Boolean isValidMove() {
    // // check that the player trying to move is the currentplayer
    // //
    // return true;
    // }

    public void sendMessage(String message, ClientHandler[] recievers) {
        // hardcode game messages into specific message formats
        // tell specific clientHandlers to send a message to their clients
    }

    // public String makeGuess(Guess guess){}

    // public makeAccusation(Guess guess){}

    public void makeMove(ClientHandler clientHandler) {
        // check if move is valid -> call isValidMove
        // if yes
        // update player position in player class
        // tell clienthandler to message it's client to move the character to a new
        // position -> call sendMessage
        // tell other players of move -> call sendMessage
        // if no
        // tell clienthandler to message it's client that the move is invalid -> call
        // sendMessage
    }

    public String getCurrentPlayerTurn() {
        return currentPlayerTurn.getPlayerName();
    }

    // public String[] getPlayerCards(Player player){
    // return players cards
}
