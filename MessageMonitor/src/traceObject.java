import java.sql.Timestamp;
import java.util.Date;

public class traceObject {
    private String ID;
    private String Stage;
    private Timestamp time;
    private String IP;

    public traceObject(String id, String Stage, Timestamp time, String IP)
    {
        this.setID(id);
        this.setStage(Stage);
        this.setTimeStamp(time);
        this.setIP(IP);

    }

    public String getID() {
        return ID;
    }

    private void setID(String ID) {
        this.ID = ID;
    }

    public String getIP() {
        return IP;
    }

    private void setIP(String IP) {
        this.IP = IP;
    }

    public String getStage() {
        return Stage;
    }

    private void setStage(String stage) {
        Stage = stage;
    }

    public Timestamp getTime() {
        return time;
    }

    private void setTimeStamp(Timestamp t) {
        this.time= t;
    }
}
