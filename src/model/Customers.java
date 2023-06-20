package model;

import helper.JDBC;
import javafx.geometry.Pos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Customers
{
    public static int insert(int Customer_ID, int Division_ID) throws SQLException      // PK and FK, example was random var, then FK
    {
        String sql = "INSERT INTO CUSTOMERS (Customer_ID, Division_ID) VALUES (?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, Customer_ID);
        ps.setInt(2, Division_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int Fruit_Name, int Fruit_ID) throws SQLException             // obv redo with diff vars, example is random var, then PK
    {
        String sql = "UPDATE CUSTOMERS SET Fruit_Name = ? WHERE Fruit_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, Fruit_Name);
        ps.setInt(2, Fruit_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    public static int delete(int fruitId) throws SQLException
    {
        String sql = "DELETE FROM FRUITS WHERE FRUIT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, fruitId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int execute() // roughly 1hr in
    {
        return 0;
    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM FRUITS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {
            int fruitId = rs.getInt("FRUIT_ID");
            String fruitName = rs.getString("Fruit_Name");
            System.out.println(fruitId + "   ");
            System.out.println(fruitName + "\n");
        }
    }













//    public int Customer_ID;             // PK
//    public String Customer_Name;
//    public String Address;
//    public String Postal_Code;
//    public String Phone;
//    public String Create_Date;
//    public String Created_By;
//    public String Last_Update;
//    public String Last_Updated_By;
//    public int Division_ID;             // FK
//
//    public Customers(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, String Create_Date, String Created_By, String Last_Update,
//                     String Last_Updated_By, int Division_ID)
//    {
//        this.Customer_ID = Customer_ID;
//        this.Customer_Name = Customer_Name;
//        this.Address = Address;
//        this.Postal_Code = Postal_Code;
//        this.Phone = Phone;
//        this.Create_Date = Create_Date;
//        this.Created_By = Created_By;
//        this.Last_Update = Last_Update;
//        this.Last_Updated_By = Last_Updated_By;
//        this.Division_ID = Division_ID;
//    }
}
