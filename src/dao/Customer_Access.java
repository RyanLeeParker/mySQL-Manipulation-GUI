package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Customer_Access
{
    public static ObservableList<Customers> getCustomers(Connection connection) throws SQLException
    {
        ObservableList<Customers> ObservableList_Customers = FXCollections.observableArrayList();

//        String query = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, " +
//                "customers.Phone, customers.Division_ID, first_level_divisions.Division from customers INNER JOIN  " +
//                "first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";

        String query = "SELECT * from customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (rs.next())
        {
            int Customer_ID = rs.getInt("Customer_ID");
            String Customer_Name = rs.getString("Customer_Name");
            String Address = rs.getString("Address");
            String Postal_Code = rs.getString("Postal_Code");
            String Phone = rs.getString("Phone");
            LocalDateTime createDate = LocalDateTime.parse(rs.getString("Create_Date"), formatter);
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int Division_ID = rs.getInt("Division_ID");
            Customers Customer = new Customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, createDate, createdBy, lastUpdate, lastUpdatedBy, Division_ID);
            ObservableList_Customers.add(Customer);
        }

        return ObservableList_Customers;
    }
}
