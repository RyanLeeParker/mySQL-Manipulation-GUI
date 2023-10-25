package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Users_Access extends Users
{
    public static String CurrentUser;
    public Users_Access(int userId, String userName, String password, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy)
    {
        super(userId, userName, password,  createDate, createdBy, lastUpdate, lastUpdatedBy);
    }

    public static int validation(String userName, String password) throws SQLException        //compare allUsersObsList to user input
    {
        try
        {
            String query = "SELECT * FROM users WHERE user_name = '" + userName + "' AND password = '" + password + "'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();

            if (rs.getString("User_Name").equals(userName))
            {
                if (rs.getString("Password").equals(password))
                {
                    CurrentUser = userName;
                    return rs.getInt("User_ID");
                }
            }
        }
        catch (SQLException d)
        {
            System.out.println("Validation exception reached");
            //d.printStackTrace();
        }

        return -1;
    }

    public static ObservableList<Users_Access> getUsersList() throws SQLException
    {
        try
        {
            ObservableList<Users_Access> allUsersObsList = FXCollections.observableArrayList();
            String sql = "SELECT * FROM users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (result.next())
            {
                int userId = result.getInt("User_ID");
                String userName = result.getString("User_Name");
                String password = result.getString("Password");
                LocalDateTime createDate = LocalDateTime.parse(result.getString("Create_Date"), formatter);
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdatedBy = result.getString("Last_Updated_By");
                Users_Access user = new Users_Access(userId, userName, password,  createDate, createdBy, lastUpdate, lastUpdatedBy);
                allUsersObsList.add(user);
            }
            return allUsersObsList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentUser()
    {
        return CurrentUser;
    }

}
