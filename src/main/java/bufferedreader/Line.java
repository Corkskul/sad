package bufferedreader;


public class Line {
    static StringBuilder sb = new StringBuilder();
    private int CurrentIndex = 0;
    private boolean insert_ON = false;

    public void add(char c) {
        System.out.print((char) c);
        sb.append((char) c);
        CurrentIndex = CurrentIndex + 1;
    }

    public void remove() {
        if (CurrentIndex > 0) {
            sb.deleteCharAt(CurrentIndex - 1);
            CurrentIndex = CurrentIndex - 1;
            System.out.print("\033[1D");
            System.out.print("\033[K");
            System.out.print(sb.substring(CurrentIndex));
            System.out.print("\033[" + (sb.length() - CurrentIndex) + "D");
        }
    }
    public int size(){
        return sb.length();
    }
    public boolean hasNext(){
        return sb.length()> CurrentIndex;
    }

    public void moveLeft(){
        if(CurrentIndex>0){
            CurrentIndex--;
            System.out.print("\033[D");
        }
    }
    public void moveRight(){
        if(CurrentIndex<sb.length()){
            CurrentIndex++;
            System.out.print("\033[C");
        }
    }
    public void home(){
        CurrentIndex = 0;
        System.out.print("\033[H");
        }
    //Arreglar lo del primer final no va bien.
    public void end(){
        CurrentIndex = sb.length();
        System.out.print("\033[1;"+ sb.length() + "H");
        }
    public void insert(){
        insert_ON = !insert_ON;
    }
    
    public String toString(){
        return sb.toString();
    }


}

