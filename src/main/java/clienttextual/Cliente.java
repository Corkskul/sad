import java.io.*;

public class Cliente {
    public static void main(String[] args) throws IOException {
        MySocket socket = new MySocket("localhost", 1234);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Ingresa el seu nom: ");
        String username = keyboard.readLine();
        socket.println(username);

        Thread inputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String line;
                try {
                    while ((line = keyboard.readLine()) != null) {
                        socket.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread outputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String line;
                try {
                    while ((line = socket.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        inputThread.start();
        outputThread.start();
    }
}
