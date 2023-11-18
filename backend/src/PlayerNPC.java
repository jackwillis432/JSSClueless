import java.util.*;

public class PlayerNPC {
    private String characterName;

    private int[] position;

    public Boolean isPlayable = false;

    public PlayerNPC(String characterName, int[] startPosition) {
        this.characterName = characterName;
        this.position = startPosition;
    }

    public int[] getPosition() {
        return position;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setPosition(int[] newPosition) {
        position = newPosition;
    }
}