package MessageServer;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



import java.util.Enumeration;
import java.util.Properties;



public class Main {
    private static Logger logger = LogManager.getLogger("HelloWorld");

    public static void main(String[] args) throws Exception {
        int port;

        String host;
        String topic;


        ApplicationProperties ap = new ApplicationProperties()  ;
        Properties p = ap.getProperties() ;
        host = p.getProperty("nettyhost")  ;
        port =   Integer.parseInt(p.getProperty("nettyport") ) ;
        topic = p.getProperty("topic")  ;


        PostMessageSender msg = new PostMessageSender(port,host,logger)   ;
        String message ;
        for (Integer i = 0;i<10;i++)
        {
            message = "message count" + ":" + i.toString();
            msg.sendMessage(topic,message);
        }



    }
}
