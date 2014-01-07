

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class ConsumerSample {



    static String  env ;
    static String zooKeeper  ;
    static String groupID  ;
    static String topic  ;
    static int threads  ;
    static String filename ;
    static String broker  ;



    public static String getEnv() {
        return env;
    }

    public static String getZooKeeper() {
        return zooKeeper;
    }

    public static String getGroupID() {
        return groupID;
    }

    public static String getTopic() {
        return topic;
    }

    public static int getThreads() {
        return threads;
    }

    public static String getFilename() {
        return filename;
    }

    public static String getBroker() {
        return broker;
    }

    public static void initializeApp(Properties p)
    {
        env = p.getProperty("environment");
        zooKeeper = p.getProperty("zooKeeper"+env)   ;
        groupID =  p.getProperty("groupID"+env)   ;
        topic = p.getProperty("topic"+env)   ;
        threads = Integer.parseInt(p.getProperty("threads"+env))  ;
        filename =   p.getProperty("filename"+env) ;
        broker = p.getProperty("consumer.broker.list"+env)  ;
    }


    public static void main(String[] args) throws Exception
    {

        ApplicationProperties ap = new ApplicationProperties()  ;
        Properties p = ap.getProperties() ;
        initializeApp(p);



        String name = ConsumerSample.class.getName();
        Logger logger = LogManager.getLogger(name);
        logger.info(name)   ;


        List<IOutputter> outputs = new ArrayList<IOutputter>();
        outputs.add(new ConsumerFileOutputter(filename) ) ;
        outputs.add(new ConsoleOutputter()) ;
        outputs.add(new KafkaMonitorOutput(broker,ConsumerSample.class.getName())) ;



        ConsumerGroup example = new ConsumerGroup(zooKeeper, groupID, topic,outputs);
        example.run(threads);
        try {

            while(true)
            {
                Thread.sleep(100000);
                System.out.println("still alive") ;
            }

        }
        catch (InterruptedException ie)
        {
        }
        example.shutdown();
    }
}
