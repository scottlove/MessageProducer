
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
    static String  env ;
    static String zooKeeper  ;
    static String groupID ;
    static String topic;
    static int threads  ;
    static String filename  ;
    static int traceInterval_ms ;
    static int traceRollInterval_hr ;

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

    public static int getTraceInterval_ms() {
        return traceInterval_ms;
    }

    public static int getTraceRollInterval_hr() {
        return traceRollInterval_hr;
    }

    public static void initializeApp(Properties p)
    {
        env = p.getProperty("environment");
        zooKeeper = p.getProperty("traceZooKeeper"+env)   ;
        groupID =  p.getProperty("traceGroupID"+env)   ;
        topic = p.getProperty("traceTopic"+env)   ;
        threads = Integer.parseInt(p.getProperty("traceConsumerThreads"+env))  ;
        filename =   p.getProperty("traceFilename"+env) ;
        traceInterval_ms = Integer.parseInt(p.getProperty("traceInterval_ms"+env)) ;
        traceRollInterval_hr = Integer.parseInt(p.getProperty("traceRollInterval_hr"+env));
    }


    public static String rollMonitorFile(String filename)
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
            initializeApp(p);


            //calculate how may trace intervals (writes to monitor file)
            //before rolling monitor file.
            //formula is:  traceRollInterval_hr*(#ms/hr)/raceinterval_ms
            long traceTics = (traceRollInterval_hr*36000000)/traceInterval_ms;
            int ticCount = 0;

            System.out.println(zooKeeper)   ;

            String name = MessageMonitor.class.getName();
            logger = LogManager.getLogger(name);

            //on startup roll logfile, start with clean one
            String rolledFilename = rollMonitorFile(filename);
            if (rolledFilename =="")
                logger.warn("No previous logfile to roll on Startup");
            else
                logger.info("On Startup, rolled logfile to:" + rolledFilename);

            mo = new MonitorOutputter();

            List<IOutputter> outputs = new ArrayList<IOutputter>();
            outputs.add(mo ) ;





            ConsumerGroup example = new ConsumerGroup(zooKeeper, groupID, topic,outputs);
            example.run(threads);
            try {

                while(true)
                {
                    if(ticCount>=traceTics)
                    {
                        rolledFilename = rollMonitorFile(filename);
                        if (rolledFilename =="")
                            logger.warn("No previous logfile to roll on Startup");
                        else
                            logger.info("rolling monitor logfile to:" + rolledFilename);

                        ticCount = 0;
                    }

                    Thread.sleep(traceInterval_ms);
                    ticCount++;
                    WriteMonitorFile(filename)  ;
                }

            }
            catch (InterruptedException ie)
            {
                logger.error(ie.getMessage());
            }
            example.shutdown();
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());

        }
    }
}
