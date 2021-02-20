package exceptions;

/**
 * Exception for no Entity Found
 */
public class NoEntityFound extends RuntimeException{

    public NoEntityFound(){
        super("No Entity Found!");
    }
}
