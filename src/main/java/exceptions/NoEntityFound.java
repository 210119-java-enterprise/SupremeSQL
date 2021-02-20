package exceptions;

public class NoEntityFound extends RuntimeException{

    public NoEntityFound(){
        super("No Entity Found!");
    }
}
