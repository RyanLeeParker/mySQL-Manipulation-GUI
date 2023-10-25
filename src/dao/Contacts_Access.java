package dao;

import helper.JDBC;
import model.Contacts;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contacts_Access
{
    public static ObservableList<Contacts> getContacts() throws SQLException
    {
        ObservableList<Contacts> ContactsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next())
        {
             int Contact_ID = resultSet.getInt("Contact_ID");
             String Contact_Name = resultSet.getString("Contact_Name");
             String Email = resultSet.getString("Email");
             Contacts Contact = new Contacts(Contact_ID, Contact_Name, Email);
            ContactsList.add(Contact);
        }

        return ContactsList;
    }

    public static String getContactID(String contactID) throws SQLException
    {
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("SELECT * FROM contacts WHERE Contact_Name = ?");
        preparedStatement.setString(1, contactID);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
        {
            contactID = resultSet.getString("Contact_ID");
        }
        return contactID;
    }

}
