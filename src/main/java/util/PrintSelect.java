package util;

import java.util.Iterator;
import java.util.List;

/**
 * This classes is used for Printing the Select Statements
 * Every row will be in rows instead all in one single line
 */
public class PrintSelect {

    /**
     * Prints the rows of the entity list
     * @param session
     */
    public void printRow(List<?> session) {
        Iterator<?> i = session.iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }
}
