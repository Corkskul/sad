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
    public static final int UP = 65;
    public static final int DOWN = 66;

    public EditableBufferedReaderD(Reader in) {
        super(in);
    }

    public void setRaw() { // set the terminal to raw mode
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

        if (c == ESCAPE) { // if the first character is an escape character
            int c1 = super.read();
            int c2 = super.read();

            if (c1 == 91) { // if the second character is '['
                if (c2 == 68 || c2 == '^') { // if the third character is 'D' or '^' (arrow left)
                    System.out.print("\033[1D");
                } else if (c2 == 67) { // if the third character is 'C' (arrow right)
                    System.out.print("\033[1C");
                }
            }
        }

        return c;
    }

    @Override
    public String readLine() throws IOException {
        setRaw();
        StringBuilder sb = new StringBuilder();
        int c;
        switch (c = read()) {
            case 127:
            case 8:
                break;
            default:
                sb.append((char) c);
                System.out.print((char) c);
                break;
        }
        while ((c = read()) != -1 && c != '\n') {
            if (c == 127 || c == 8) { // if the character is a backspace or delete
                if (sb.length() > 0) {
                    System.out.print("\b \b");
                    sb.deleteCharAt(sb.length() - 1);
                }
            } else if (c >= 32 && c < 127) { // if the character is a printable ASCII character
                System.out.print((char) c);
                sb.append((char) c);
            }
        }
        unsetRaw();
        return sb.toString();
    }


 }
