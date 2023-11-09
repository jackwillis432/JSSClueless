import java.util.*;

public class Lobby {
    private Player currentPlayerTurn;
    private ClientHandler[] clientHandlers;
    public Player[] turnOrder;
    public String[] winCondition;

    public static HashMap<ClientHandler, Player> clientHandlersPlayerMap = new HashMap<>();

    public Lobby(ClientHandler[] clientHandlers) {
        this.clientHandlers = clientHandlers;
        createPlayers();
        startGame();
    }

    private void startGame() {
    }

    private void endGame() {
    }

    private void createPlayers() {
    }

    // private Boolean isValidMove(){}

    public void sendMessage(String message, ClientHandler[] recievers) {
    }

    // public String makeGuess(Guess guess){}

    // public makeAccusation(Guess guess){}

    public void makeMove(ClientHandler clientHandler) {
    }

    public String getCurrentPlayerTurn() {
        return currentPlayerTurn.getPlayerName();
    }

    // public String[] getPlayerCards(Player player){}

}
