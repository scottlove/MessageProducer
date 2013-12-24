import java.sql.Timestamp;
import java.util.*;

public class ProcessingMonitor {


    private HashMap<String,Integer> processHM;

    public ProcessingMonitor()
    {
          processHM = new HashMap<String, Integer>();
    }

    public void addData(traceObject t)
    {

        String key = t.getStage() + ":" + t.getIP() ;
        int count = 1;
        if (processHM.containsKey(key))
        {
            count =  processHM.get(key) + 1;

        }
        processHM.put(key, count) ;

    }

    public int getCount(String Stage, String IP)
    {
        String key = Stage +":" + IP  ;
        int count = 0;
        if (processHM.containsKey(key))
        {
            count =  processHM.get(key) ;

        }

        return count;
    }

    public ArrayList<String> getStats()
    {
        ArrayList<String>  stats  = new ArrayList<String>()  ;
        Iterator i = processHM.entrySet().iterator() ;

        while(i.hasNext()){
            Map.Entry me = (Map.Entry)i.next();
            String stat = me.getKey() +":" + me.getValue()    ;
            stats.add(stat)   ;
        }

        return stats;
    }
}
