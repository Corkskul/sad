package bufferedreader;


public class Line {
    static StringBuilder sb = new StringBuilder();
    private int CurrentIndex = 0;


    public void add(char c) {
        System.out.print((char)c);
        sb.append((char)c);
        CurrentIndex = CurrentIndex + 1;
    }
    public void delete() {
        if (CurrentIndex > 0) {
            sb.deleteCharAt(CurrentIndex - 1);
            CurrentIndex--;
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
        System.out.print("\033[1;"+ CurrentIndex + "H");
        }

    public String toString(){
        return sb.toString();
    }




}
