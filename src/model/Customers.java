package model;

import helper.JDBC;
import javafx.geometry.Pos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Customers
{

    public int Customer_ID;             // PK
    public String Customer_Name;
    public String Address;
    public String Postal_Code;
    public String Phone;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    public int Division_ID;             // FK

    public Customers(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int Division_ID)

    {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
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
    public LocalDateTime getCreateDate()
    {
        return createDate;
    }
    public String getCreatedBy()
    {
        return createdBy;
    }

    public Timestamp getLastUpdate()
    {
        return lastUpdate;
    }

    public String getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }
    public int getDivision_ID() {
        return Division_ID;
    }
}
