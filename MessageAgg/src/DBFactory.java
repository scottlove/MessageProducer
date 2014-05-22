import java.util.Properties;
import java.sql.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * Created by scotlov on 5/19/14.
 */
public final class DBFactory {

    private static String Driver;
    private static String DBurl;
    private static String username;
    private static String password;
    private static Logger logger;

    public static void initialize(Properties p)
    {

        String env = p.getProperty("environment");
        Driver  = p.getProperty("DBDriver"+env)  ;
        DBurl =   p.getProperty("DBUrl" + env) ;
        username =   p.getProperty("db_username" + env) ;
        password =  p.getProperty("db_password" + env) ;
        logger = LogManager.getLogger(DBFactory.class.getName());

        logger.info("Initializing DBFactory")        ;
        logger.info("url= " + DBurl);
    }

    public static String getDriver() {
        return Driver;
    }

    public static String getDBurl() {
        return DBurl;
    }

    public static String getDBusername() {
        return username;
    }

    public static String getDBpassword() {
        return password;
    }

    private static Connection SQLServerConnect()
    {

        try
        {
            Class.forName(Driver);
        }
        catch (ClassNotFoundException e) {
            logger.error("MySQL JDBC Driver not found !!");
            return null;
        }
        logger.info("SQLServer JDBC Driver Registered!");
        Connection connection = null;
        try {

            connection = DriverManager.getConnection(getDBurl());

        }
        catch (SQLException e) {
            logger.error("Connection Failed! Check output console:SQLServerConnect");
            logger.error(getDBurl());
            logger.error(e.getMessage())      ;
            return null;
        }
        return connection;

    }

    private static Connection MYSqlConnect()
    {
        try
        {
            Class.forName(Driver);
        }
        catch (ClassNotFoundException e) {
            logger.error("MySQL JDBC Driver not found !!");
            return null;
        }
        logger.info("MySQL JDBC Driver Registered!");
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(getDBurl(),getDBusername(), getDBpassword());

        }
        catch (SQLException e) {
            logger.error("Connection Failed! Check output console:MYSqlConnect");
            logger.error(getDBurl());
            logger.error(e.getMessage())  ;
            return null;
        }
        return connection;
    }

    public static Connection  connect()
    {
        Connection connection ;
        if(Driver.contains("SQLServer") )
        {
            connection = SQLServerConnect()    ;
        }
        else
        {
             connection = MYSqlConnect()       ;
        }

        return connection;
    }

    public static void CloseConnection(Connection conn)
    {
        try
        {
            if(conn!= null)
                conn.close();
            logger.info("Connection closed !!");
        } catch (SQLException e) {
            logger.error("exception on closing connection");
        }

    }



}
