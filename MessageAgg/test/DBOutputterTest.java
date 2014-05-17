import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;
import static org.junit.Assert.assertTrue;






public class DBOutputterTest {


    @org.junit.Test
    public void testInitializeProperties() throws Exception {



    assertTrue(queryDB());
    }

    public  boolean queryDB () throws Exception {
        Connection conn = getConnection();
        //Statement st = conn.createStatement();
        // st.executeUpdate("drop table survey;");
//        st.executeUpdate("create table survey (id int,name varchar(30));");
//        st.executeUpdate("insert into survey (id,name ) values (1,'nameValue')");

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM dbo.Hitters2012");

        boolean b = rs.next();
        String name = rs.getString(2)  ;

        st.close();
        conn.close();
        return b;
    }

    private static Connection getConnection() throws Exception {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url ="jdbc:sqlserver://SCOTLOV-T3600\\MSSQLSERVER_12;DatabaseName=DraftDB;IntegratedSecurity=true";
        String username = "";
        String password = "";
        Class.forName(driver);
        return DriverManager.getConnection(url);
    }
}
