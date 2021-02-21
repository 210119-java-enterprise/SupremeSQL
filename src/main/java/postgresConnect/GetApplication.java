package postgresConnect;

import entity.EntityDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class gets the Path of the application.properties
 */
public class GetApplication {

    private static final Logger logger = LogManager.getLogger(GetApplication.class);

    private Properties props = new Properties();

    private static final String Path = "src/main/resources/application.properties";

    /**
     * Create an Instance of GetApplication
     */
    private static class GetApplicationHolder{
        private static final GetApplication instance = new GetApplication();
    }

    /**
     * Loads the Path to properties
     */
    private GetApplication(){
        try{
            props.load(new FileInputStream(Path));
        }
        catch(IOException e){
            logger.error("Failed", e);
        }

    }

    /**
     * Gets the Instance of Get Application
     * @return the Instance of Get Application
     */
    public static GetApplication getInstance(){
        return GetApplicationHolder.instance;
    }

    /**
     * Gets the value of the properties specifying the key
     * @param key
     * @return the value of properties of given key
     */
    public String getValue(String key){
        return this.props.getProperty(key);
    }
}
