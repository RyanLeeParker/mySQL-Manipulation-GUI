package dao;

import javafx.geometry.Pos;

public class Customers
{
    public int Customer_ID;             // PK
    public String Customer_Name;
    public String Address;
    public String Postal_Code;
    public String Phone;
    public String Create_Date;
    public String Created_By;
    public String Last_Update;
    public String Last_Updated_By;
    public int Division_ID;             // FK

    public Customers(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, String Create_Date, String Created_By, String Last_Update,
                     String Last_Updated_By, int Division_ID)
    {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
        this.Division_ID = Division_ID;


    }
}
