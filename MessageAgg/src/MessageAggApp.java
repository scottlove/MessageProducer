import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;


public class MessageAggApp {
    static int port;
    static String env;
    static Logger logger ;




    public static int getPort() {
        return port;
    }



    public static void initializeApp(Properties p)
    {

        env = p.getProperty("environment");;
        port =   Integer.parseInt(p.getProperty("agg_port"+env) ) ;

    }

    public static void main(String[] args) throws Exception {

        ApplicationProperties ap = new ApplicationProperties()  ;
        Properties p = ap.getProperties()  ;
        initializeApp(p);



        logger = LogManager.getLogger(MessageAggApp.class.getName());

        logger.info("Starting Message Aggregator")        ;
        logger.info("port="+port);


        AggOutputter a = new AggOutputter();
        new TCPServer(port,a).run();
    }

}
