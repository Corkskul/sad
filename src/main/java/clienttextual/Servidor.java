import java.io.IOException;
import java.util.concurrent.*;

public class Servidor {
    private static final ConcurrentHashMap<String, MySocket> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        MyServerSocket serverSocket = new MyServerSocket(1234);

        while (true) {
            final MySocket clientSocket = serverSocket.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handleClient(clientSocket);
                }
            }).start();
        }
    }

    private static void handleClient(MySocket clientSocket) {
        try {
            String nick = clientSocket.readLine();
            clients.put(nick, clientSocket);

            String line;
            while ((line = clientSocket.readLine()) != null) {
                broadcastMessage(nick, line);
            }

            clients.remove(nick);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcastMessage(String sender, String message) {
        for (MySocket clientSocket : clients.values()) {
            clientSocket.println("[" + sender + "]: " + message);
        }
    }
}
