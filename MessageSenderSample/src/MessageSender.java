import java.util.Properties;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;





public class MessageSender {
    private static Logger logger = LogManager.getLogger(MessageSender.class.getName());

    static int port;
    static String host;
    static String topic;
    static String  env;
    public static int getPort() {
        return port;
    }

    public static String getHost() {
        return host;
    }

    public static String getTopic() {
        return topic;
    }

    public static String getEnv() {
        return env;
    }



    public static void initializeApp(Properties p)
    {
        env = p.getProperty("environment");
        host = p.getProperty("nettyhost"+env)  ;
        port =   Integer.parseInt(p.getProperty("nettyport"+env) ) ;
        topic = p.getProperty("messageSenderTopic"+env)  ;
    }

    public static void main(String[] args) throws Exception {

        String [] sentences = new String []{ "the cow jumped over the moon", "an apple a day keeps the doctor away",
                "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature" };
        Random rand = new Random();

        ApplicationProperties ap = new ApplicationProperties()  ;
        Properties p = ap.getProperties() ;
        initializeApp(p);



        PostMessageSender msg = new PostMessageSender(port,host)   ;
        String message ;
        Integer i = 0;
        while(true)
        {
            message = sentences[rand.nextInt(sentences.length)] ;
            //message = "message count:"  + i.toString();
            Thread.sleep(100) ;
            msg.sendMessage(topic,message);
            i++;
        }

        //msg.close();



    }
}
