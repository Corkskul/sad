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
    public static final int UP = 65;
    public static final int DOWN = 66;
    public static final int HOME = 72;
    public static final int END = 70;
    //int pos = 0;

    Line line = new Line();

    public EditableBufferedReaderD(Reader in) {
        super(in);

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

        return c;
    }

    @Override
    public String readLine() throws IOException {
        int pos = 0;
        int c;

        while ((c = read()) != -1 && c != CR) {
            if (c == 27) {
                // Escape sequence
                c = read();

                if (c == CORCHETE) {
                    c = read();

                    if (c == LEFT) {
                        // Move cursor left
                        if (pos > 0) {
                            System.out.print("\033[D");
                            pos--;
                        }
                    } else if (c == RIGHT) {
                        // Move cursor right
                        if (pos < line.str.length()) {
                            System.out.print("\033[C");
                            pos++;
                        }
                    }else if (c == HOME) {
                        // Move cursor to the beginning of the line
                        System.out.print("\033[1~");
                        pos = 0;
                    } else if (c == END) {
                        // Move cursor to the end of the line
                        System.out.print("\033[4~");
                        pos = line.str.length();
                    }
                }

                continue;
            }

            if (c == BACKSPACE || c == DELETE) {
                // Backspace/delete
                if (pos > 0) {
                    line.delete(pos - 1);;
                    pos--;
                    System.out.print("\033[D");
                    System.out.print("\033[P");
                }
            } else if (c >= 32 && c < 127) {
                // Insert character
                line.addChar(pos, (char) c);;
                pos++;
                System.out.print((char) c);

            }
        }
        unsetRaw();
        return line.toString();
    }

}
