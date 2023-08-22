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
        super();
    }

    public static int validation(String userName, String password) throws SQLException        //compare allUsersObsList to user input
    {
        try
        {
            System.out.println("Validation 1 reached");
            String query = "SELECT * FROM users WHERE user_name = '" + userName + "' AND password = '" + password + "'";
            System.out.println("Validation 2 reached");
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);            //might need to use setpreparedStatement()
            System.out.println("Validation 3 reached");
            ResultSet rs = ps.executeQuery();
            System.out.println("Validation 4 reached");                 // reaches here, something above casues SQL exception, prob preparedstatement above

            if ((rs.getString("User_Name").equals(userName)) && (rs.getString("Password").equals(password)))
            {
                System.out.println("Validation 5 reached");
                return rs.getInt("User_ID");
            }
        }
        catch (SQLException d)
        {
            System.out.println("Validation exception reached");
            d.printStackTrace();
        }

        return 0;           // 0 might be a problem, can try -1
    }

    public static ObservableList<Users_Access> getUsersList() throws Exception
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
                Users_Access userResult = new Users_Access(userId, userName, password);
                allUsersObsList.add(userResult);
            }

            return allUsersObsList;
    }
}
