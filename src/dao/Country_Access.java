package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Country_Access extends Country
{
    public Country_Access(int Country_ID, String Country_Name, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy)
    {
        super(Country_ID, Country_Name, createDate, createdBy,  lastUpdate,  lastUpdatedBy);
    }

    public static ObservableList<Country_Access> getCountries() throws SQLException
    {
        ObservableList<Country_Access> ObservableList_Countries = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country, Create_Date, Created_By, Last_Update, Last_Updated_By FROM countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (rs.next())
        {
            int Country_ID = rs.getInt("Country_ID");
            String Country_Name = rs.getString("Country");
            LocalDateTime createDate = LocalDateTime.parse(rs.getString("Create_Date"), formatter);
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            Country_Access country = new Country_Access(Country_ID, Country_Name, createDate, createdBy,  lastUpdate,  lastUpdatedBy);
            ObservableList_Countries.add(country);
        }
        return ObservableList_Countries;
    }
}
