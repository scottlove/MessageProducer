import org.apache.logging.log4j.Logger;

import java.util.Properties;
import org.apache.logging.log4j.LogManager;



public class MessageServerApp {

    private int port;
    private static Logger logger ;
    String brokerList;

    public static void main(String[] args) throws Exception {
        int port;
        String brokerList;
        ApplicationProperties ap = new ApplicationProperties()  ;
        Properties p = ap.getProperties()  ;


        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = Integer.parseInt( p.getProperty("port") ) ;
        }

        brokerList = p.getProperty("metadata.broker.list:q!")   ;

        logger = LogManager.getLogger(MessageServerApp.class.getName());

        logger.info("Starting Message Server")        ;



        new MessageServer(port,brokerList).run();
    }
}
