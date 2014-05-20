import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by scotlov on 5/19/14.
 */
public class MessageAggTests {

    private Properties initProperties()
    {
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

        try{
            String host = InetAddress.getLocalHost().getHostName();


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


        }
        catch(Exception e){
               assertTrue(false);
        }
        return p;
    }

    @org.junit.Test
    public void testDBFactory() throws Exception {


        Properties p = initProperties();
        DBFactory.initialize(p);
        assertEquals(p.getProperty("DBDriver_local")  ,DBFactory.getDriver());
        assertEquals(p.getProperty("DBUrl_local")  ,DBFactory.getDBurl());
        assertEquals(p.getProperty("db_username_local"),DBFactory.getDBusername());
        assertEquals(p.getProperty("db_password_local"),DBFactory.getDBpassword());

        Connection cn = DBFactory.connect() ;
        assertNotNull(cn);
        DBFactory.CloseConnection(cn);

    }

    @org.junit.Test
    public void testDBOutputter() throws Exception {


        Properties p = initProperties();
        DBFactory.initialize(p);

        DBOutputter d = new DBOutputter();

        d.writeString("test:1");

        Connection connection = DBFactory.connect() ;

        Statement stmt = connection.createStatement();

        String sql = "select * from MessageStore.dbo.words where word = 'test'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
        {
            assertTrue(true);
        }
        else
            assertTrue(false) ;


        DBFactory.CloseConnection(connection);

    }
}
