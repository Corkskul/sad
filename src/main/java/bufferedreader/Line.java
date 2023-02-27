package bufferedreader;

public class Line {
    StringBuilder sb = new StringBuilder();
    char c;

    public Line(char c) {

        this.c = c;

    }

    public void add(char c) {
        System.out.print((char) c);
        sb.append((char) c);
    }
}
