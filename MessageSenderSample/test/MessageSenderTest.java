import java.util.Properties;
import static org.junit.Assert.assertTrue;

public class MessageSenderTest {

    @org.junit.Test
    public void testInitializeProperties() throws Exception {
        Properties p = new Properties();

        p.setProperty("messageSenderTopic_azure","ptTest");
        p.setProperty("messageSenderTopic_local","ptTest2");
        p.setProperty("nettyhost_azure","localhost");
        p.setProperty("nettyhost_local","kafkaserver.cloudapp.net");
        p.setProperty("nettyport_local","8080");
        p.setProperty("nettyport_azure","8080");

        p.setProperty("environment","_local");
        MessageSender.initializeApp(p);
        assertTrue(p.getProperty("messageSenderTopic_local") == MessageSender.getTopic());
        assertTrue(p.getProperty("nettyhost_local")==MessageSender.getHost());
        assertTrue(Integer.parseInt(p.getProperty("nettyport_local"))== MessageSender.getPort());

        p.setProperty("environment","_azure");
        MessageSender.initializeApp(p);
        assertTrue(p.getProperty("messageSenderTopic_azure") == MessageSender.getTopic());
        assertTrue(p.getProperty("nettyhost_azure")==MessageSender.getHost());
        assertTrue(Integer.parseInt(p.getProperty("nettyport_azure"))== MessageSender.getPort());

        //test the negative
        assertTrue(p.getProperty("messageSenderTopic_local") != MessageSender.getTopic());
    }
}
