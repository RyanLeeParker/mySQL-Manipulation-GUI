package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Contacts_Access
{
    public static ObservableList<Contacts> getContacts() throws SQLException
    {
        ObservableList<Contacts> ObservableList_Contacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);          //might need to use setpreparedStatement()
        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {
             int Contact_ID = rs.getInt("Contact_ID");
             String Contact_Name = rs.getString("Contact_Name");
             String Email = rs.getString("Email");
             Contacts Contact = new Contacts(Contact_ID, Contact_Name, Email);
             ObservableList_Contacts.add(Contact);
        }

        return ObservableList_Contacts;
    }

    public static String findContactID(String contactID) throws SQLException {
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT * FROM contacts WHERE Contact_Name = ?");
        ps.setString(1, contactID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            contactID = rs.getString("Contact_ID");
        }
        return contactID;
    }

}
