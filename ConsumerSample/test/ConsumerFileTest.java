import ConsumerSample.ConsumerFile;

import java.io.File;
import java.io.FileInputStream;
import static org.junit.Assert.assertTrue;

/**
 * Created by scotlov on 12/16/13.
 */
public class ConsumerFileTest {
    @org.junit.Test
    public void testWriteString() throws Exception {

        String test = "test string" ;
        String fileName = "test.htm";
        ConsumerFile cf = new ConsumerFile(fileName)   ;

        cf.writeString(test);


        FileInputStream f0 = new FileInputStream(fileName);

        byte[] readData = new byte[1024];
        int i = f0.read(readData) ;


        assertTrue(i == test.length() + 8);


        f0.close();
        File f1  = new File(fileName)    ;
        boolean b = f1.delete()   ;


    }
}
