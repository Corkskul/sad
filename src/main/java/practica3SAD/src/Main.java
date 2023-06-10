

package src;

import java.io.IOException;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                new ChatServer(1234).run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        SwingUtilities.invokeLater(() -> {
            try {
                ChatWindow chatWindow = new ChatWindow();
                ChatClient chatClient = new ChatClient("localhost", 1234, chatWindow);
                chatClient.startReading();

                chatWindow.getInputField().addActionListener(e -> {
                    String message = chatWindow.getInputField().getText();
                    chatWindow.getInputField().setText("");
                    try {
                        chatClient.sendMessage(message);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}


