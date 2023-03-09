package bufferedreader;


public class Line {
    static StringBuilder sb = new StringBuilder();
    private int CurrentIndex = 0;

    public void add(char c) {
        System.out.print((char) c);
        sb.append((char) c);
        CurrentIndex = CurrentIndex + 1;
    }

    public void remove() {
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            System.out.print("\b \b");
        }
    }
    public int size(){
        return sb.length();
        //eso es un comentario
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
    
    public void end(){
        CurrentIndex = sb.length();
        System.out.print("\033[F");
        }
    
    public String toString(){
        return sb.toString();
    }




}

