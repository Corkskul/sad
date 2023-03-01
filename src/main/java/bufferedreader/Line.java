package bufferedreader;

public class Line {
    StringBuilder sb = new StringBuilder();

    public void add(char c) {
        System.out.print((char) c);
        sb.append((char) c);
    }
}
