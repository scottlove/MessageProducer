import java.net.InetAddress;
import java.sql.Connection;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by scotlov on 5/19/14.
 */
public class DBFactoryTest {

    @org.junit.Test
    public void testInitializeProperties() throws Exception {

        String mySQL_URL = "com.mysql.jdbc.Driver";
        String mySQL_Driver = "jdbc:mysql://localhost:3306/messagestore";
        String SQLServer_URL = "jdbc:sqlserver://SCOTLOV-T3600\\MSSQLSERVER_12;DatabaseName=DraftDB;IntegratedSecurity=true";
        String SQLServer_Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String username ="test_user";
        String password = "test_password"   ;


        Properties p = new Properties();
        p.setProperty("environment","_local");
        String url;
        String driver;


        String host = InetAddress.getLocalHost().getHostName(); ;
        if(host.contains("scotlov"))
        {
            //doing this to allow test on my machine that has sqlserver.
            driver =   SQLServer_Driver;
            url =     SQLServer_URL   ;

        }
        else
        {
            //running test on normal machine with mySQL
            driver =   mySQL_Driver;
            url =     mySQL_Driver   ;

        }

        p.setProperty("DBUrl_local",url )     ;
        p.setProperty("DBDriver_local",driver)  ;
        p.setProperty("db_username_local",username)   ;
        p.setProperty("db_password_local",password)   ;

        DBFactory.initialize(p);
        assertEquals(driver,DBFactory.getDriver());
        assertEquals(url,DBFactory.getDBurl());
        assertEquals(DBFactory.getDBusername(),username);
        assertEquals(DBFactory.getDBpassword(),password);

        Connection cn = DBFactory.connect() ;
        assertNotNull(cn);
        DBFactory.CloseConnection(cn);


    }
}
