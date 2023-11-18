import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private static int numberOfPlayers;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Clueless Game Server!\n");
        System.out.println("Please enter the number of people playing: ");
        numberOfPlayers = scanner.nextInt();

        if (numberOfPlayers >= 2) {
            ServerSocket serverSocket = new ServerSocket(1234);
            Server server = new Server(serverSocket);
            System.out.println("\nServer Ready, ServerSocket listening to port 1234...");
            scanner.close();
            server.startServer();
        } else {
            System.out.println("\nInvalid number of players. Exiting...");
            scanner.close();
        }
    }

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        // ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
        try {
            while (!serverSocket.isClosed()) {
                // Blocking method, server halted until someone connects
                Socket socket = serverSocket.accept();
                System.out.println("A new player has connected\n");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();

                if (clientHandler.clientHandlers.size() == numberOfPlayers) {
                    Lobby gameLobby = new Lobby(new ArrayList<>(clientHandler.clientHandlers));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
