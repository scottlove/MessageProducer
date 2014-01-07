
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class ConsumerSampleTest {

    @org.junit.Test
    public void testInitializeProperties() throws Exception {
        Properties p = new Properties();
        p.setProperty("topic_azure","ptTest");
        p.setProperty("topic_local","ptTest");
        p.setProperty("zooKeeper_azure","kafkaserver.cloudapp.net:2181");
        p.setProperty("zooKeeper_local","localhost:2181");
        p.setProperty("groupID_azure","2");
        p.setProperty("groupID_local","1");
        p.setProperty("threads_azure","3");
        p.setProperty("threads_local","1");
        p.setProperty("filename_azure","./consumer.html");
        p.setProperty("filename_local","./consumer1.html");
        p.setProperty("consumer.broker.list_local","localBrokers:2121,localBrokers2:2121,");
        p.setProperty("consumer.broker.list_azure","azureBrokers:2121,azureBrokers2:2121,");


        p.setProperty("environment","_local");
        ConsumerSample.initializeApp(p);
        assertTrue(p.getProperty("topic_local") == ConsumerSample.getTopic());
        assertTrue(p.getProperty("zooKeeper_local")==ConsumerSample.getZooKeeper());
        assertTrue(p.getProperty("groupID_local")==ConsumerSample.getGroupID());
        assertTrue(Integer.parseInt(p.getProperty("threads_local"))== ConsumerSample.getThreads());
        assertTrue(p.getProperty("filename_local")==ConsumerSample.getFilename());
        assertTrue(p.getProperty("consumer.broker.list_local")==ConsumerSample.getBroker());

        p.setProperty("environment","_azure");
        ConsumerSample.initializeApp(p);
        assertTrue(p.getProperty("topic_azure") == ConsumerSample.getTopic());
        assertTrue(p.getProperty("zooKeeper_azure")==ConsumerSample.getZooKeeper());
        assertTrue(p.getProperty("groupID_azure")==ConsumerSample.getGroupID());
        assertTrue(Integer.parseInt(p.getProperty("threads_azure"))== ConsumerSample.getThreads());
        assertTrue(p.getProperty("filename_azure")==ConsumerSample.getFilename());
        assertTrue(p.getProperty("consumer.broker.list_azure")==ConsumerSample.getBroker());



    }


}
