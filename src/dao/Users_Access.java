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

    public void validation()        //compare allUsersObsList to user input
    {

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
