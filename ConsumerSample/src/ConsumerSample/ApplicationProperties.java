package ConsumerSample;

import java.io.FileInputStream;
import java.util.Properties;


public class ApplicationProperties {

    public Properties getProperties() throws Exception
    {
        Properties defaultProps = new Properties();
        FileInputStream in = new FileInputStream("app.properties")      ;
        defaultProps.load(in);
        in.close();
        return defaultProps;


    }
}