import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Created by scotlov on 5/20/14.
 */
public class MessageAggInt {
    private static int port;
    private static String hostname;


    public static Properties getProperties() throws Exception
    {
        Properties defaultProps = new Properties();
        FileInputStream in = new FileInputStream("app.properties")      ;
        defaultProps.load(in);
        in.close();
        return defaultProps;


    }

    private static void sendData(String word, int count)
    {
        try{
            String out = word + ":" + Integer.toString(count)  ;
            TCPSender s = new TCPSender(port,hostname)   ;
            s.sendMessage("message",word + ":" + Integer.toString(count) );
            System.out.println(word +  Integer.toString(count) )        ;
        }
        catch (Exception e)
        {
            System.out.println(e.toString())        ;
        }


    }

    public static void main(String [] args) throws Exception
    {

          Properties p = getProperties()  ;
          String env = p.getProperty("environment");
          port =   Integer.parseInt(p.getProperty("agg_port"+env) ) ;
          hostname = p.getProperty("agg_host"+env) ;

          System.out.println(hostname) ;
          System.out.println(port) ;



            int count = 0;
          String word = "intTest"     ;

          while(true)
          {
              count = count + 1;
              System.out.println(word + Integer.toString(count) ) ;
              sendData(word,count)    ;
              Thread.currentThread().sleep(5000)     ;



          }

    }
}
