public class Room {
    private String roomName;
    private String[] positions;
    private int[] trapDoorPosition;
    private int[][] adjacentHallways;

    public Room(String roomName, String[] positions, int[] trapDoorPosition, int[][] adjacentHallways) {
        this.roomName = roomName;
        this.positions = positions;
        this.trapDoorPosition = trapDoorPosition;
        this.adjacentHallways = adjacentHallways;
    }

    public void addPlayer() {
        // TODO: Add player logic here
        // This method could handle adding a player to the room
    }

    // Getters and setters for the attributes could be added here if needed
    // Example of getters:
    public String getRoomName() {
        return roomName;
    }

    public String[] getPositions() {
        return positions;
    }

    public int[] getTrapDoorPosition() {
        return trapDoorPosition;
    }

    public int[][] getAdjacentHallways() {
        return adjacentHallways;
    }

    // Example of setters:
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setPositions(String[] positions) {
        this.positions = positions;
    }

    public void setTrapDoorPosition(int[] trapDoorPosition) {
        this.trapDoorPosition = trapDoorPosition;
    }

    public void setAdjacentHallways(int[][] adjacentHallways) {
        this.adjacentHallways = adjacentHallways;
    }
}
