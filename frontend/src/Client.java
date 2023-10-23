import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Client extends Application {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public static void main(String args[]) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, args[0]);
        // blocking method on its own thread
        client.listenForMessage();
        // blocking method on its own thread
        client.sendMsg();

        launch(args);
    }

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            // this is what the client is sending (client is writing)
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // this is what the server is sending (client is reading)
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Gameboard.fxml"));
            Scene scene = new Scene(root, 1000, 1000);

            primaryStage.setTitle("Clueless");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMsg() {
        try {
            // Send the username to the server
            bufferedWriter.write(username);
            // Send a newline to indicate the write is done, on the ClientHandler this
            // satisfies the
            // bufferedReader.readLine() part
            bufferedWriter.newLine();
            // Flush the rest of the buffer to fill it
            bufferedWriter.flush();

            // get cli input
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String msgToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    // create a thread to listen for msges so the client isn't stuck here
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromServer;

                while (socket.isConnected()) {
                    try {
                        msgFromServer = bufferedReader.readLine();
                        System.out.println(msgFromServer);
                    } catch (Exception e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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
}