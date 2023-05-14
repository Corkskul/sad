import java.io.IOException;
import java.net.*;

public class MyServerSocket {
    private ServerSocket serverSocket;

    public MyServerSocket(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public MySocket accept() throws IOException {
        return new MySocket(serverSocket.accept());
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
