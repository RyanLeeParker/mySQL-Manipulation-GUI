package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Users_Access extends Users
{
    public Users_Access(int userId, String userName, String password)
    {
        super(userId, userName, password);
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
            while (result.next())
            {
                int userId = result.getInt("User_ID");
                String userName = result.getString("User_Name");
                String password = result.getString("Password");
                Users_Access user = new Users_Access(userId, userName, password);
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

}
