package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Customer_Access
{
    public static ObservableList<Customers> getCustomers(Connection connection) throws SQLException                //dbl chk query text
    {
        ObservableList<Customers> ObservableList_Customers = FXCollections.observableArrayList();

        String query = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, " +
                "customers.Phone, customers.Division_ID, first_level_divisions.Division from customers INNER JOIN  " +
                "first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);          //might need to use setpreparedStatement()
        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            int Customer_ID = rs.getInt("Customer_ID");
            String Customer_Name = rs.getString("Customer_Name");
            String Address = rs.getString("Address");
            String Postal_Code = rs.getString("Postal_Code");
            String Phone = rs.getString("Phone");
            int Division_ID = rs.getInt("Division_ID");
            String division_name = rs.getString("Division");
            Customers Customer = new Customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID);
            ObservableList_Customers.add(Customer);
        }

        return ObservableList_Customers;
    }
}
