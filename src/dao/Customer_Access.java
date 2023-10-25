package dao;


import java.sql.*;
import helper.JDBC;
import model.Customers;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.format.DateTimeFormatter;

public class Customer_Access
{
    public static ObservableList<Customers> getCustomers(Connection connection) throws SQLException
    {
        ObservableList<Customers> CustomersList = FXCollections.observableArrayList();

        String query = "SELECT * from customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";

        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (resultSet.next())
        {
            int Customer_ID = resultSet.getInt("Customer_ID");
            String Customer_Name = resultSet.getString("Customer_Name");
            String Address = resultSet.getString("Address");
            String Postal_Code = resultSet.getString("Postal_Code");
            String Phone = resultSet.getString("Phone");
            LocalDateTime createDate = LocalDateTime.parse(resultSet.getString("Create_Date"), formatter);
            String createdBy = resultSet.getString("Created_By");
            Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int Division_ID = resultSet.getInt("Division_ID");
            Customers Customer = new Customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, createDate, createdBy, lastUpdate, lastUpdatedBy, Division_ID);
            CustomersList.add(Customer);
        }

        return CustomersList;
    }
}
