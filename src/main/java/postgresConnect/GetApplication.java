package postgresConnect;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GetApplication {

    private Properties props = new Properties();

    private static final String Path = "src/main/resources/application.properties";

    private static class GetApplicationHolder{
        private static final GetApplication instance = new GetApplication();
    }
    private GetApplication(){
        try{
            props.load(new FileInputStream(Path));
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public static GetApplication getInstance(){
        return GetApplicationHolder.instance;
    }

    public String getValue(String key){
        return this.props.getProperty(key);
    }
}
