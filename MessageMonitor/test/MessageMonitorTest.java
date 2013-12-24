import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertTrue;


public class MessageMonitorTest {
    @Test
    public void testParseLine() throws Exception {


        String trace =         "1cdbc70b-7c76-4994-95ee-93974d311610:2ec06df5-7cfa-42e1-8d93-0017a3ccbd1a:scotlov-T3600:10.123.19.187:MessageHandler:2013-12-23 11:04:37.435";

        MonitorOutputter m = new MonitorOutputter();
        traceObject t = m.parseLine(trace)   ;

        assertTrue(t.getID().equals("2ec06df5-7cfa-42e1-8d93-0017a3ccbd1a"))  ;
        assertTrue(t.getStage().equals("MessageHandler"))  ;
        String time  = t.getTime().toString() ;

        assertTrue(time.equals("2013-12-23 11:04:37.435"));

        String IP = t.getIP()   ;
        assertTrue(IP.equals("10.123.19.187"))  ;





    }
}
