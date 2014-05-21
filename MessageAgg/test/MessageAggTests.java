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
        String mySQL_Driver = "com.mysql.jdbc.Driver";
        String mySQL_URL = "jdbc:mysql://localhost:3306/messagestore";
        String SQLServer_URL = "jdbc:sqlserver://SCOTLOV-T3600\\MSSQLSERVER_12;DatabaseName=DraftDB;IntegratedSecurity=true";
        String SQLServer_Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String username ="test";
        String password = "test"   ;
        String mySQL_SelectQuery ="select * from MessageStore.words where word = '%s'";
        String mySQL_UpdateQuery="UPDATE MessageStore.words SET count=%s WHERE word = '%s'";
        String mySQL_InsertQuery="Insert into MessageStore.words (word,count) VALUES('%s',%s)";
        String SQLServer_SelectQuery ="select * from MessageStore.dbo.words where word = '%s'";
        String SQLServer_UpdateQuery="UPDATE MessageStore.dbo.words SET count=%s WHERE word = '%s'";
        String SQLServer_InsertQuery="Insert into MessageStore.dbo.words (word,count) VALUES('%s',%s)";


        Properties p = new Properties();
        p.setProperty("environment","_local");


        try{
            String host = InetAddress.getLocalHost().getHostName();


            if(host.contains("scotlov"))
            {
                //doing this to allow test on my machine that has sqlserver.

                p.setProperty("DBUrl_local",SQLServer_URL )     ;
                p.setProperty("DBDriver_local",SQLServer_Driver)  ;
                p.setProperty("SelectQuery_local",SQLServer_SelectQuery);
                p.setProperty("InsertQuery_local",SQLServer_InsertQuery);
                p.setProperty("UpdateQuery_local",SQLServer_UpdateQuery);

            }
            else
            {
                //running test on normal machine with mySQL
                p.setProperty("DBUrl_local",mySQL_URL )     ;
                p.setProperty("DBDriver_local",mySQL_Driver)  ;
                p.setProperty("SelectQuery_local",mySQL_SelectQuery);
                p.setProperty("InsertQuery_local",mySQL_InsertQuery);
                p.setProperty("UpdateQuery_local",mySQL_UpdateQuery);

            }


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

        DBOutputter d = new DBOutputter(p);

        d.writeString("test:1");

        Connection connection = DBFactory.connect() ;

        Statement stmt = connection.createStatement();

        String sql = "select * from MessageStore.words where word = 'test'";
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
