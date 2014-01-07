import org.junit.Test;
import java.util.Properties;
import static org.junit.Assert.assertTrue;

/**
 * Created by scotlov on 1/1/14.
 */
public class MessageServerAppTest {
    @Test
    public void testInitializeProperties() throws Exception {
        Properties p = new Properties();


        p.setProperty("metadata.broker.list_local","localhost:9092,localhost:9093");
        p.setProperty("port_local","1");
        p.setProperty("metadata.broker.list_azure","azurehost:9092,azurehost:9093");
        p.setProperty("port_azure","8080");

        p.setProperty("environment","_local");
        MessageServerApp.initializeApp(p);
        assertTrue(p.getProperty("metadata.broker.list_local") == MessageServerApp.getBrokerList());
        assertTrue(Integer.parseInt(p.getProperty("port_local"))==MessageServerApp.getPort());

        p.setProperty("environment","_azure");
        MessageServerApp.initializeApp(p);
        assertTrue(p.getProperty("metadata.broker.list_azure") == MessageServerApp.getBrokerList());
        assertTrue(Integer.parseInt(p.getProperty("port_azure"))==MessageServerApp.getPort());

        //test the negative
        assertTrue(Integer.parseInt(p.getProperty("port_local"))!=MessageServerApp.getPort());
    }

}
