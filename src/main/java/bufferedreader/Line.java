package bufferedreader;

public class Line {
    static StringBuilder sb = new StringBuilder();

    public  void add(char c) {
        System.out.print((char) c);
        sb.append((char) c);
    }

    public  void remove() {
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
    }
    public int size(){
        return sb.length();
        //eso es un comentario
    }


}

