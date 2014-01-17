import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AggOutputter implements IOutputter {
    @Override
    public void writeString(String data) {
        Logger logger = LogManager.getLogger(AggOutputter.class.getName());
        logger.info(data.toString());

    }
}
