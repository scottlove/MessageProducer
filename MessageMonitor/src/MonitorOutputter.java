import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.Timestamp;
import java.text.DateFormat;

public class MonitorOutputter implements IOutputter {

    private ProcessingMonitor pm;
    Logger logger;
    public MonitorOutputter()
    {
           pm = new ProcessingMonitor();
           logger = LogManager.getLogger(MessageMonitor.class.getName());
    }

    @Override
    public void writeString(String s) {


      try
      {

            traceObject t = parseLine(s) ;
            pm.addData(t);
      }
      catch (Exception E)
      {
            logger.error(E.getMessage());
      }



    }


    public traceObject parseLine(String line) throws Exception
    {
        String[] p = line.split(":")  ;

        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.FULL)   ;

        String time = p[5] + ":" + p[6] + ":" + p[7];
        //Date date = format.parse(time);

        Timestamp t =  Timestamp.valueOf(time)   ;

        return new traceObject(p[1],p[4],t,p[3])   ;


    }

    public ProcessingMonitor getProcessingMonitor()
    {
        return pm;
    }


}
