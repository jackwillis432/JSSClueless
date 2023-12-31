import java.util.*;

public class Player {
    private String playerName;

    private String characterName;

    private ArrayList<String> cards;

    private int[] position;

    public Boolean isPlayable = true;

    public ClientHandler clientHandler;

    public Player(String playerName, String characterName, ArrayList<String> cards, int[] startPosition,
            ClientHandler clientHandler) {
        this.playerName = playerName;
        this.characterName = characterName;
        this.cards = cards;
        this.position = startPosition;
        this.clientHandler = clientHandler;
    }

    public int[] getPosition() {
        return position;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public void setPosition(int[] newPosition) {
        position = newPosition;
    }
}
