package util;

import java.util.Iterator;
import java.util.List;

public class PrintSelect {

    public void printRow(List<?> session) {
        Iterator<?> i = session.iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }
}
