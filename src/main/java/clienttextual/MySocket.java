import java.io.*;
import java.net.*;

public class MySocket {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public MySocket(String host, int port) throws IOException {
        socket = new Socket(host, port);
        initializeStreams();
    }

    public MySocket(Socket socket) throws IOException {
        this.socket = socket;
        initializeStreams();
    }

    private void initializeStreams() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public String readLine() throws IOException {
        return in.readLine();
    }

    public void println(String message) {
        out.println(message);
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
