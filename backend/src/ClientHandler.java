import java.util.ArrayList;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler(Socket socket) {
        try {
            // Only allow up to six players to connect
            if (clientHandlers.size() < 6) {
                this.socket = socket;
                // this is what the server is sending (server is writing)
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                // this is what the client is sending (server is reading)
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.clientUsername = bufferedReader.readLine();
                clientHandlers.add(this);
                broadcastMessage("Server: " + clientUsername + " has entered the chat!");
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
                broadcastMessage(msg);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String msgToSend) {
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
        broadcastMessage("Server: " + clientUsername + " has left the chat!");
    }

    public void closeEverything(Socket s, BufferedReader br, BufferedWriter bw) {
        removeClientHandler();
        try {
            if (br != null) {
                // closes everything in the wrapper -> outputstreamwrite -> getoutputstream
                br.close();
            }
            if (bw != null) {
                // closes everything in the wrapper -> inputstreamread -> getinputstream
                bw.close();
            }
            if (s != null) {
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}