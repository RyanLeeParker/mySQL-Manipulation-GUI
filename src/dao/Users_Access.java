package dao;

import helper.JDBC;
import model.Users;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.format.DateTimeFormatter;

/** This class controls access and manipulation to the Users class.*/
public class Users_Access extends Users
{
    public static String CurrentUser;

    /** This method uses the Report class as a parent to manipulate said class and objects. */
    public Users_Access(int userId, String userName, String password, LocalDateTime createDate, String createdBy,
                        Timestamp lastUpdate, String lastUpdatedBy)
    {
        super(userId, userName, password,  createDate, createdBy, lastUpdate, lastUpdatedBy);
    }

    /** This method is used by the login screen to validate the username and password entered against the MySQL database.
     * Also sets CurrentUser to keep track of current User.
     *
     * @param userName
     * @param password
     * @throws SQLException
     * @return User_ID
     * @return -1 if user is invalid.*/
    public static int validation(String userName, String password) throws SQLException
    {
        try
        {
            String query = "SELECT * FROM users WHERE user_name = '" + userName + "' AND password = '" + password + "'";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            if (resultSet.getString("User_Name").equals(userName))
            {
                if (resultSet.getString("Password").equals(password))
                {
                    CurrentUser = userName;
                    return resultSet.getInt("User_ID");
                }
            }
        }
        catch (SQLException d)
        {
            System.out.println("Validation exception reached");
        }

        return -1;
    }

    /**This method creates and Observable list for the Users in the MySQL database.
     * @throws SQLException
     * @return UsersList*/
    public static ObservableList<Users_Access> getUsersList() throws SQLException
    {
        try
        {
            ObservableList<Users_Access> UsersList = FXCollections.observableArrayList();
            String sql = "SELECT * FROM users";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (resultSet.next())
            {
                int userId = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                LocalDateTime createDate = LocalDateTime.parse(resultSet.getString("Create_Date"), formatter);
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                Users_Access user = new Users_Access(userId, userName, password,  createDate, createdBy, lastUpdate, lastUpdatedBy);
                UsersList.add(user);
            }
            return UsersList;
        }
        catch (SQLException e)
        {
            System.out.println("getUsersList exception reached");
        }
        return null;
    }

    /** This method is to keep track of who is currently logged in, updating objects.
     * @return CurrentUser */
    public static String getCurrentUser()
    {
        return CurrentUser;
    }

}
