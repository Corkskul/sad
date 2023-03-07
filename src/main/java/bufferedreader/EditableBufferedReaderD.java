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
        int c;
        setRaw();

        while ((c = read()) != -1 && c != '\n') {
            switch(c) {
                case ESCAPE:
                    int c1 = super.read();
                    int c2 = super.read();
                    if (c1 == CORCHETE) {
                        switch(c2) {
                            case LEFT:
                                line.moveLeft();
                                break;
                            case RIGHT:
                                line.moveRight();
                                break;
                            case UP:
                                line.where();
                                break;
                        }
                    }
                    break;
                case DELETE:
                case BACKSPACE:
                    line.remove();
                    break;
                case CR:
                    System.out.print("\r\n");
                    break;
                default:
                    line.add((char) c);
                    //test
                    break;
            }
        }
        unsetRaw();

        return line.toString();
    }




}
