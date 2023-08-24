package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC
{
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connect = null;  // Connection Interface
    public static PreparedStatement preparedStatement;

    public static Connection openConnection()
    {
        try
        {
            Class.forName(driver); // Locate Driver
            connect = (Connection) DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
        return connect;
    }

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

    public static Connection getConnection()
    {
        return connect;
    }

    public static void setPreparedStatement(Connection link, String sqlCommand) throws Exception
    {
        preparedStatement = link.prepareStatement(sqlCommand);
    }

    public static PreparedStatement getPreparedStatement()
    {
        return preparedStatement;
    }

}
