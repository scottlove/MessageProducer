import org.junit.Test;

import java.util.ArrayList;
import java.util.Properties;


import static org.junit.Assert.*;

public class ProcessingMonitorTest {
    @Test
         public void testProcessingMonitor() throws Exception {
        String trace = "1cdbc70b-7c76-4994-95ee-93974d311610:2ec06df5-7cfa-42e1-8d93-0017a3ccbd1a:scotlov-T3600:10.123.19.187:MessageHandler:2013-12-23 11:04:37.435";

        MonitorOutputter m = new MonitorOutputter();
        traceObject t = m.parseLine(trace)   ;

        ProcessingMonitor pm  = new ProcessingMonitor();

        pm.addData(t);

        int count = pm.getCount(t.getStage(),t.getIP())  ;

        assertEquals(count,1) ;

        pm.addData(t);

        count = pm.getCount(t.getStage(),t.getIP())  ;

        assertEquals(count,2) ;

    }

    @Test
    public void testGetStatsr() throws Exception {
        String trace0 = "1cdbc70b-7c76-4994-95ee-93974d311610:2ec06df5-7cfa-42e1-8d93-0017a3ccbd1a:scotlov-T3600:10.123.19.187:MessageHandler:2013-12-23 11:04:37.435";
        String trace1 = "1cdbc70b-7c76-4994-95ee-93974d311610:2ec06df5-7cfa-42e1-8d93-0017a3ccbd1a:scotlov-T3600:10.123.19.188:MessageHandler1:2013-12-23 11:04:37.435";
        String trace2 = "1cdbc70b-7c76-4994-95ee-93974d311610:2ec06df5-7cfa-42e1-8d93-0017a3ccbd1a:scotlov-T3600:10.123.19.189:MessageHandler2:2013-12-23 11:04:37.435";

        MonitorOutputter m = new MonitorOutputter();
        traceObject t0 = m.parseLine(trace0)   ;
        traceObject t1 = m.parseLine(trace1)   ;
        traceObject t2 = m.parseLine(trace2)   ;
        int count0 = 100;
        int count1 = 50;
        int count2 = 10;

        ProcessingMonitor pm  = new ProcessingMonitor();


        for (int i =0;i<count0;i++)
        {
            pm.addData(t0);
        }

        for (int i =0;i<count1;i++)
        {
            pm.addData(t1);
        }

        for (int i =0;i<count2;i++)
        {
            pm.addData(t2);
        }


        int count = pm.getCount(t0.getStage(),t0.getIP())  ;
        assertEquals(count,count0) ;


        count = pm.getCount(t1.getStage(),t1.getIP())  ;
        assertEquals(count,count1) ;


        count = pm.getCount(t2.getStage(),t2.getIP())  ;
        assertEquals(count,count2) ;

        ArrayList<String> s = pm.getStats();

        assertEquals(s.size(),3) ;

        for (String k : s)
        {
            if (k.equals("MessageHandler:10.123.19.187:100") ||k.equals("MessageHandler1:10.123.19.188:50") || k.equals("MessageHandler2:10.123.19.189:10"))
            {
                 assertTrue(true);
            }
            else
            {
                 assertTrue(false) ;
            }
        }



    }



}
