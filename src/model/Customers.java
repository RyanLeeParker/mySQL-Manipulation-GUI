package model;

import helper.JDBC;
import javafx.geometry.Pos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Customers
{

    public int Customer_ID;             // PK
    public String Customer_Name;
    public String Address;
    public String Postal_Code;
    public String Phone;
    public LocalDateTime Create_Date;
    public String Created_By;
    public LocalDateTime Last_Update;
    public String Last_Updated_By;
    public int Division_ID;             // FK

    public Customers(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID) // LocalDateTime Create_Date, String Created_By, LocalDateTime Last_Update,
    // String Last_Updated_By,
    {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
//        this.Create_Date = Create_Date;
//        this.Created_By = Created_By;
//        this.Last_Update = Last_Update;
//        this.Last_Updated_By = Last_Updated_By;
        this.Division_ID = Division_ID;
    }

    public int getCustomer_ID()
    {
        return Customer_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getPostal_Code() {
        return Postal_Code;
    }

    public String getPhone() {
        return Phone;
    }

    public int getDivision_ID() {
        return Division_ID;
    }
}
