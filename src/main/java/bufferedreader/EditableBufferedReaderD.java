package bufferedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReaderD extends BufferedReader {
    public static final int ESCAPE = 27;
    public static final int SPACE = 32;
    public static final int BACKSPACE = 8;
    public static final int DELETE = 127;
    public static final int LEFT = 68;
    public static final int RIGHT = 67;
    public static final int CORCHETE = 91;
    public static final int CR = 13;
    private int pos;
    Line line = new Line();

    public EditableBufferedReaderD(Reader in) {
        super(in);
        pos = 0;
        setRaw();

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

        if (c == ESCAPE) {
            int c1 = super.read();
            int c2 = super.read();

            if (c1 == CORCHETE) {
                if (c2 == LEFT) {
                    if(pos>0){
                        pos--;
                        System.out.print("\033[D");
                    }
                } else if (c2 == RIGHT && pos < line.size()) {
                    pos++;
                    System.out.print("\033[C");
                }
            }
        } else if (c == DELETE || c == BACKSPACE) {
            System.out.print("\b \b");
        }

        return c;
    }

    @Override
    public String readLine() throws IOException {

        StringBuilder sb = new StringBuilder();
        int c;

        while ((c = read()) != -1 && c != '\n') {
            if (c == DELETE || c == BACKSPACE) {
                line.remove();
                pos--;
            } else if (c == CR) {
                System.out.print("\r\n");
                break;
            } else {
                pos++;
                line.add((char) c);
                //test
            }
        }
        unsetRaw();

        return sb.toString();

    }

}
