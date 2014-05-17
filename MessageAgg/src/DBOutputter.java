import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Console;


public class DBOutputter implements IOutputter  {

    @Override
    public void writeString(String data) {
        Logger logger = LogManager.getLogger(AggOutputter.class.getName());
        logger.info(data.toString());

        System.out.println("ran test")      ;

    }
}


