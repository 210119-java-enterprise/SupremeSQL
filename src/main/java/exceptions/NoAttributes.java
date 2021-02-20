package exceptions;

/**
 * Exception for no attributes Found
 */
public class NoAttributes extends RuntimeException{

    public NoAttributes(){
        super("No Attributes Found!");
    }
}
