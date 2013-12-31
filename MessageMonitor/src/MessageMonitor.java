
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;


public class MessageMonitor {

    static Logger logger;
    static MonitorOutputter mo;


    public String rollMonitorFile(String filename)
    {
        String newFileName = "";
        File f = new File(filename);


        // if file exists
        if(f.exists())
        {

            Date d = new Date();
            Timestamp t =new Timestamp(d.getTime());

            newFileName = filename+t.getTime();
            f.renameTo(new File(newFileName) );

        }

        return newFileName;
    }

    private static void WriteMonitorFile(String filename)
    {

        ProcessingMonitor pm = mo.getProcessingMonitor()    ;
        try
        {
          String par = "<p>"  ;
          FileOutputStream f0 = new FileOutputStream(filename,true)    ;
          ArrayList<String> s = pm.getStats();

          for (String k : s)
          {
              f0.write(par.getBytes());
              f0.write(k.getBytes());
              f0.write(par.getBytes());
              f0.write(System.getProperty("line.separator").getBytes());
          }
          f0.close();
          f0=null;
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
        }


        ArrayList<String> s = pm.getStats();

        for (String k : s)
        {
            logger.info(k);
        }

    }


    public static void main(String[] args) throws Exception
    {
        try
        {



        ApplicationProperties ap = new ApplicationProperties()  ;
        Properties p = ap.getProperties() ;
        String zooKeeper = p.getProperty("zooKeeper")   ;
        String groupID =  p.getProperty("traceGroupID")   ;
        String topic = p.getProperty("traceTopic")   ;
        int threads = Integer.parseInt(p.getProperty("threads"))  ;
        String filename =   p.getProperty("traceFilename") ;
        String broker = p.getProperty("metadata.broker.list")  ;

        System.out.println(zooKeeper)   ;

        String name = MessageMonitor.class.getName();
        logger = LogManager.getLogger(name);


        mo = new MonitorOutputter();

        List<IOutputter> outputs = new ArrayList<IOutputter>();
        outputs.add(mo ) ;





        ConsumerGroup example = new ConsumerGroup(zooKeeper, groupID, topic,outputs);
        example.run(threads);
        try {

            while(true)
            {
                Thread.sleep(10000);
                WriteMonitorFile(filename)  ;
            }

        }
        catch (InterruptedException ie)
        {
        }
        example.shutdown();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage())     ;

        }
    }
}
