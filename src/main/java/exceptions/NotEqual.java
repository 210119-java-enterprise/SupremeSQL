package exceptions;

/**
 * Exception for Objects not being equal
 */
public class NotEqual extends RuntimeException{

   public NotEqual(){
        super("The Objects are NOT equal");
    }
}
