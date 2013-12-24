

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class ConsumerSample {

    public static void main(String[] args) throws Exception
    {



        ApplicationProperties ap = new ApplicationProperties()  ;
        Properties p = ap.getProperties() ;
        String zooKeeper = p.getProperty("zooKeeper")   ;
        String groupID =  p.getProperty("groupID")   ;
        String topic = p.getProperty("topic")   ;
        int threads = Integer.parseInt(p.getProperty("threads"))  ;
        String filename =   p.getProperty("filename") ;
        String broker = p.getProperty("metadata.broker.list")  ;




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
