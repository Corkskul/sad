package src;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;

public class ChatWindow extends JFrame {
    private JTextArea messagesArea;
    private JTextField inputField;
    private JList<String> usersList;
    private DefaultListModel<String> usersListModel;

    public ChatWindow() {
        setTitle("Chat Gráfico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        messagesArea = new JTextArea();
        messagesArea.setEditable(false);
        add(new JScrollPane(messagesArea), BorderLayout.CENTER);

        inputField = new JTextField();
        add(inputField, BorderLayout.SOUTH);
        inputField.addActionListener(e -> {
            // Enviar mensaje al servidor
            // ...
            inputField.setText("");
        });

        usersListModel = new DefaultListModel<>();
        usersList = new JList<>(usersListModel);
        add(new JScrollPane(usersList), BorderLayout.EAST);

        setVisible(true);
    }

    public void addMessage(String message) {
        messagesArea.append(message + "\n");
    }

    public void addUser(String user) {
        usersListModel.addElement(user);
    }

    public void removeUser(String user) {
        usersListModel.removeElement(user);
    }
    public JTextField getInputField() {
        return this.inputField;
    }
}

