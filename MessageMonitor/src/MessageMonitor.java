import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.util.*;


public class MessageMonitor {

    static Logger logger;
    static MonitorOutputter mo;



    private static void WriteMonitorFile()
    {
       ProcessingMonitor pm = mo.getProcessingMonitor()    ;

        ArrayList<String> s = pm.getStats();

        for (String k : s)
        {
            logger.info(k);
        }

    }


    public static void main(String[] args) throws Exception
    {

        logger = LogManager.getLogger(MessageMonitor.class.getName());

        ApplicationProperties ap = new ApplicationProperties()  ;
        Properties p = ap.getProperties() ;
        String zooKeeper = p.getProperty("zooKeeper")   ;
        String groupID =  p.getProperty("traceGroupID")   ;
        String topic = p.getProperty("traceTopic")   ;
        int threads = Integer.parseInt(p.getProperty("threads"))  ;
        String filename =   p.getProperty("traceFilename") ;
        String broker = p.getProperty("metadata.broker.list")  ;

        mo = new MonitorOutputter();

        List<IOutputter> outputs = new ArrayList<IOutputter>();
        outputs.add(mo ) ;





        ConsumerGroup example = new ConsumerGroup(zooKeeper, groupID, topic,outputs);
        example.run(threads);
        try {

            while(true)
            {
                Thread.sleep(10000);
                WriteMonitorFile()  ;
            }

        }
        catch (InterruptedException ie)
        {
        }
        example.shutdown();
    }
}
