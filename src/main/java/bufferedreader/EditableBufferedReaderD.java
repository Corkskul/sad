package bufferedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
//import java.util.Scanner;

public class EditableBufferedReaderD extends BufferedReader {
    public static final int ESCAPE = 27;
    public static final int SPACE = 32;
    public static final int BACKSPACE = 8;
    public static final int DELETE = 127;
    public static final int LEFT = 68;
    public static final int RIGHT = 67;
    public static final int CORCHETE = 91;
    public static final int CR = 13;
    public static final int HOME = 72;
    public static final int FINAL = 70;
    Line line = new Line();

    public EditableBufferedReaderD(Reader in) {
        super(in);

    }

    public void setRaw() {
        try {
            String[] cmd = { "/bin/sh", "-c", "stty -echo raw </dev/tty" };
            Runtime.getRuntime().exec(cmd).waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    

    public void unsetRaw() {
        try {
            String[] cmd = { "/bin/sh", "-c", "stty echo cooked </dev/tty" };
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    

    @Override
    public int read() throws IOException {
        int c = super.read();
        return c;
    }
    @Override
    public String readLine() throws IOException {
        int c;
        setRaw();
        System.out.print("\033[H\033[2J");
        
        while ((c = read()) != -1 && c != '\n') {
            if (c == ESCAPE) {
                int c1 = super.read();
                int c2 = super.read();
    
                if (c1 == CORCHETE) {
                    if (c2 == LEFT) {
                        line.moveLeft();
                    } else if (c2 == RIGHT) {
                        line.moveRight();
                    }
                    else if(c2 == HOME){
                        line.home();
                    }
                    else if(c2 == FINAL){
                        line.end();
                    }
                    else if(c2 == 50){
                        int c3 = super.read();
                        if(c3 == 126){
                            line.insert();
                        }
                    }

                }
            } 
            else if (c == DELETE || c == BACKSPACE) {
                line.remove();
            } 
            else if (c == CR) {
                System.out.print("\r\n");
                break;
            } else {
                line.add((char) c);
            }
        }
        unsetRaw();

        return line.toString();

    }

}
