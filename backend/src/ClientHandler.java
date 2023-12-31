import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private ArrayList<String> characters = new ArrayList<>(Arrays.asList("Miss Scarlet", "Colonel Mustard",
            "Mrs. White", "Mr. Green", "Mrs. Peacock", "Professor Plum"));
    private ArrayList<int[]> startingPositions = new ArrayList<>(
            Arrays.asList(new int[] { 665, 33 }, new int[] { 40, 215 }, new int[] { 890, 215 }, new int[] { 40, 515 },
                    new int[] { 265, 690 }, new int[] { 665, 690 }));
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername = "defaultName";
    private String character = "defaultCharacter";
    private int[] startingPosition;
    private int clientHandlerPosition;

    public ClientHandler(Socket socket) {
        try {
            // Only allow up to six players to connect
            if (clientHandlers.size() < 6) {
                this.character = characters.get(clientHandlers.size());
                this.startingPosition = startingPositions.get(clientHandlers.size());
                this.socket = socket;
                // this is what the server is sending (server is writing)
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                // this is what the client is sending (server is reading)
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.clientUsername = bufferedReader.readLine();
                clientHandlers.add(this);
                clientHandlerPosition = clientHandlers.size();
                sendMsg("Server: " + clientUsername + " (" + character + ") is here!");
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String msg;

        while (socket.isConnected()) {
            try {
                // blocking operation
                msg = bufferedReader.readLine();

                // Just for testing we don't need to send messages back on initial connect
                sendMsg(msg);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void sendMsg(String msgToSend) {
        // ClientHandler nextPlayer = clientHandlers.get(clientHandlerPosition + 1);
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    // remember each clientHandler object has a bufferedWrite which is used to write
                    // to its client
                    clientHandler.bufferedWriter.write(msgToSend);
                    // send newline after msgToSend, which tells the client the server is done
                    // writing
                    clientHandler.bufferedWriter.newLine();
                    // finally flush the writer
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        sendMsg("Server: " + clientUsername + " has left the chat!");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                // closes everything in the wrapper -> outputstreamwrite -> getoutputstream
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                // closes everything in the wrapper -> inputstreamread -> getinputstream
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return clientUsername;
    }

    public String getCharacter() {
        return character;
    }

    public int[] getStartingPosition() {
        return startingPosition;
    }
}