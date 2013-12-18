import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;




public class MessageSender {
    private static Logger logger = LogManager.getLogger(MessageSender.class.getName());

    public static void main(String[] args) throws Exception {
        int port;

        String host;
        String topic;



        logger.info("found log4j2")  ;
        logger.error("blah")  ;

        ApplicationProperties ap = new ApplicationProperties()  ;
        Properties p = ap.getProperties() ;
        host = p.getProperty("nettyhost")  ;
        port =   Integer.parseInt(p.getProperty("nettyport") ) ;
        topic = p.getProperty("topic")  ;


        PostMessageSender msg = new PostMessageSender(port,host)   ;
        String message ;
        for (Integer i = 0;i<10;i++)
        {
            message = "message count:"  + i.toString();
            msg.sendMessage(topic,message);
        }

        msg.close();



    }
}
