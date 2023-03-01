package bufferedreader;

public class Line {
    static StringBuilder sb = new StringBuilder();

    public static void add(char c) {
        System.out.print((char) c);
        sb.append((char) c);
    }

    public static void remove() {
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
