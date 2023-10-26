package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/** This is the class for the MySQL connector, it hosts all methods of connector interaction.*/
public class JDBC
{
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static String password = "Passw0rd!";
    public static Connection connect = null;
    public static PreparedStatement preparedStatement;

    /** This method opens the connection to MySQL to allow database manipulation.
     * @return connect which is the database connection object required by some SQL functions.*/
    public static Connection openConnection()
    {
        try
        {
            Class.forName(driver);
            connect = (Connection) DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
        return connect;
    }
    /** Similar to the above, this method closes the MySQL database connection when the user is finished.*/
    public static void closeConnection()
    {
        try {
            connect.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
    /** This is a getter method for the connection object.
     * @return connect is the connection object of the class.*/
    public static Connection getConnection()
    {
        return connect;
    }

    /** This method is the main way of interacting with MySQL, by translating Java into SQL compatible code.
     * @throws Exception
     * .*/
    public static void setPreparedStatement(Connection link, String sqlCommand) throws Exception
    {
        preparedStatement = link.prepareStatement(sqlCommand);
    }

    /** This method is the getter for the preparedStatement.
     * @return the prepared statement, which is the SQL compatible code*/
    public static PreparedStatement getPreparedStatement()
    {
        return preparedStatement;
    }

}
