package bufferedreader;

class Line {
    public StringBuilder str;

    public Line() {
        str = new StringBuilder();
    }

    public void delete(int pos) {
        if (pos > 0 && pos <= str.length()) {
            str.deleteCharAt(pos-1);
        }

    }

    public void addChar(int c, int pos) {
        if (pos <= str.length()) {
        str.insert(pos, (char) c);
        }
    }

    public String toString() {
        return str.toString();
    }
}

/*
 * import java.util.ArrayList;
 *
 * public class Line {
 *
 * private ArrayList<Character> str;
 *
 * public Line() {
 * str = new ArrayList<>();
 * }
 *
 * public String delete(int pos) {
 * if (pos > 1) {
 * str.remove(pos - 1);
 * }
 * return listToString(str);
 * }
 *
 * public String addChar(int c, int pos) {
 * str.add(pos - 1, (char) c);
 * return listToString(str);
 * }
 *
 * private String listToString(ArrayList<Character> list) {
 * StringBuilder sb = new StringBuilder();
 * for (char c : list) {
 * sb.append(c);
 * }
 * return sb.toString();
 * }
 * }
 */
