package src;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;

import javax.swing.SwingWorker;

public class ChatClient {
    private SocketChannel socketChannel;
    private ChatWindow chatWindow;

    public ChatClient(String host, int port, ChatWindow chatWindow) throws IOException {
        socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        this.chatWindow = chatWindow;
    }

    public void sendMessage(String message) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        socketChannel.write(buffer);
    }

    public void startReading() {
        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                ByteBuffer buffer = ByteBuffer.allocate(1024);

                while (true) {
                    buffer.clear();
                    int bytesRead = socketChannel.read(buffer);

                    if (bytesRead > 0) {
                        buffer.flip();
                        byte[] bytes = new byte[bytesRead];
                        buffer.get(bytes);
                        String message = new String(bytes);
                        publish(message);
                    }
                }
            }

            @Override
            protected void process(List<String> messages) {
                for (String message : messages) {
                    chatWindow.addMessage(message);
                }
            }
        }.execute();
    }
}
