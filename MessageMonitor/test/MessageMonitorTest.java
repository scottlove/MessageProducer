import org.junit.Test;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
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

    @Test
    public void testRollMonitorFile() throws Exception {

        String filename = "c:/tmp/test.htlm";
        FileOutputStream f0 = new FileOutputStream(filename,true)    ;
        String t = "test123";
        f0.write(t.getBytes());
        f0.close();
        long l =(new File(filename).length());

        MessageMonitor m = new MessageMonitor();
        String rolledFilename = m.rollMonitorFile(filename);

        File f = new File(filename);

        //make sure file was rolled
        assertFalse(f.exists());

        File f2 = new File(rolledFilename);
        assertTrue(f2.length() == l);

        f2.deleteOnExit();


    }


    @Test
    public void testInitializeProperties() throws Exception {
        Properties p = new Properties();


        p.setProperty("traceZooKeeper_local","localhost:2181");
        p.setProperty("traceConsumerThreads_local","1");
        p.setProperty("traceTopic_local","TRACE");
        p.setProperty("traceFilename_local","trace.html");
        p.setProperty("traceGroupID_local","100");
        p.setProperty("traceInterval_ms_local","10000");
        p.setProperty("traceRollInterval_hr_local","24");
        p.setProperty("traceZooKeeper_azure","kafkaserver.cloudapp.net:2181");
        p.setProperty("traceConsumerThreads_azure","1");
        p.setProperty("traceTopic_azure","TRACE");
        p.setProperty("traceFilename_azure","./trace.html");
        p.setProperty("traceGroupID_azure","100");
        p.setProperty("traceInterval_ms_azure","10000");
        p.setProperty("traceRollInterval_hr_azure","24");



        p.setProperty("environment","_local");
        MessageMonitor.initializeApp(p);
        assertTrue(p.getProperty("traceZooKeeper_local") == MessageMonitor.getZooKeeper());
        assertTrue(Integer.parseInt(p.getProperty("traceConsumerThreads_local"))==MessageMonitor.getThreads());
        assertTrue(p.getProperty("traceTopic_local")==MessageMonitor.getTopic());
        assertTrue(p.getProperty("traceFilename_local")== MessageMonitor.getFilename());
        assertTrue(p.getProperty("traceGroupID_local")==MessageMonitor.getGroupID());
        assertTrue((Integer.parseInt(p.getProperty("traceInterval_ms_local"))==MessageMonitor.getTraceInterval_ms()));
        assertTrue((Integer.parseInt(p.getProperty("traceRollInterval_hr_local"))==MessageMonitor.getTraceRollInterval_hr()));


        p.setProperty("environment","_azure");
        MessageMonitor.initializeApp(p);
        assertTrue(p.getProperty("traceZooKeeper_azure") == MessageMonitor.getZooKeeper());
        assertTrue(Integer.parseInt(p.getProperty("traceConsumerThreads_azure"))==MessageMonitor.getThreads());
        assertTrue(p.getProperty("traceTopic_azure")==MessageMonitor.getTopic());
        assertTrue(p.getProperty("traceFilename_azure")== MessageMonitor.getFilename());
        assertTrue(p.getProperty("traceGroupID_azure")==MessageMonitor.getGroupID());
        assertTrue((Integer.parseInt(p.getProperty("traceInterval_ms_azure"))==MessageMonitor.getTraceInterval_ms()));
        assertTrue((Integer.parseInt(p.getProperty("traceRollInterval_hr_azure"))==MessageMonitor.getTraceRollInterval_hr()));

        //test the negative case
        assertTrue(p.getProperty("traceZooKeeper_local")!=MessageMonitor.getGroupID());

    }


 }
