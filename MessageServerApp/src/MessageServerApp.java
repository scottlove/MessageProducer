import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.Properties;




public class MessageServerApp {

    static int port;
    static String env;
    static Logger logger ;
    static String brokerList;

    public static String getBrokerList() {
        return brokerList;
    }

    public static int getPort() {
        return port;
    }



    public static void initializeApp(Properties p)
    {

        env = p.getProperty("environment");
        brokerList = p.getProperty("metadata.broker.list"+env)  ;
        port =   Integer.parseInt(p.getProperty("port"+env) ) ;

    }

    public static void main(String[] args) throws Exception {

        ApplicationProperties ap = new ApplicationProperties()  ;
        Properties p = ap.getProperties()  ;
        initializeApp(p);



        logger = LogManager.getLogger(MessageServerApp.class.getName());

        logger.info("Starting Message Server")        ;
        logger.info("brokerList="+brokerList);
        logger.info("port="+port);



        new MessageServer(port,brokerList).run();
    }
}
