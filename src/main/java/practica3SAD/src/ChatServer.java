package src;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class ChatServer extends NIOServer {
    private Map<SocketChannel, ByteBuffer> clients = new HashMap<>();

    public ChatServer(int port) throws IOException {
        super(port);
    }

    @Override
    protected void onAccept(SelectionKey key) throws IOException {
        SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(key.selector(), SelectionKey.OP_READ);
        clients.put(clientChannel, ByteBuffer.allocate(1024));
    }

    @Override
    protected void onRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = clients.get(clientChannel);
        int bytesRead = clientChannel.read(buffer);

        if (bytesRead == -1) {
            clients.remove(clientChannel);
            clientChannel.close();
            return;
        }

        String message = new String(buffer.array(), 0, bytesRead).trim();

        buffer.clear();

        for (SocketChannel channel : clients.keySet()) {
            if (channel != clientChannel) {
                buffer = ByteBuffer.wrap(message.getBytes());
                while (buffer.hasRemaining()) {
                    channel.write(buffer);
                }
            }
        }
    }
}
