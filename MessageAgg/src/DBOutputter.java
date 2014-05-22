import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scala.reflect.generic.Trees;

import java.io.Console;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DBOutputter implements IOutputter  {

    Logger logger;
    String selectQuery;
    String insertQuery;
    String updateQuery;

    public DBOutputter(Properties p)
    {
        logger = LogManager.getLogger(AggOutputter.class.getName());
        String env = p.getProperty("environment");

        selectQuery = p.getProperty("SelectQuery" + env);
        insertQuery = p.getProperty("InsertQuery" + env);
        updateQuery = p.getProperty("UpdateQuery" + env);
    }

    @Override
    public void writeString(String data) {

        String [] splits =data.split(":")      ;
        if (splits.length != 2)
        {
            logger.error("invalid input string") ;
            return;
        }
        Connection connection = DBFactory.connect()  ;
        if (connection != null)
        {
            try {
                Statement stmt = connection.createStatement();
                String sql = String.format( selectQuery,splits[0]) ;
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next())
                {
                    //do update
                    String sql2 = String.format(updateQuery,splits[1],splits[0]);
                    stmt.executeUpdate(sql2);
                }
                else {
                    String sql2 = String.format(insertQuery,splits[0],splits[1]);
                    stmt.executeUpdate(sql2);
                }
            }
            catch(SQLException e)
            {
                 logger.error(e.toString());
            }


        }
        else
        {
            logger.error("DBOutputter unable to connect to db");
        }





        logger.info(data.toString());

        DBFactory.CloseConnection(connection);      ;

    }
}


