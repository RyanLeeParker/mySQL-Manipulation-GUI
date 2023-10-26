package dao;

import java.sql.*;
import helper.JDBC;
import model.Country;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.format.DateTimeFormatter;

/** This class controls access and manipulation to the Country class.*/
public class Country_Access extends Country
{
    /** This method uses the Country class as a parent to manipulate said class and objects. */
    public Country_Access(int Country_ID, String Country_Name, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy)
    {
        super(Country_ID, Country_Name, createDate, createdBy,  lastUpdate,  lastUpdatedBy);
    }

    /** This method creates and Observable list for the Countries in the MySQL database.
     * @return CountriesList*/
    public static ObservableList<Country_Access> getCountries() throws SQLException
    {
        ObservableList<Country_Access> CountriesList = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country, Create_Date, Created_By, Last_Update, Last_Updated_By FROM countries";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (resultSet.next())
        {
            int Country_ID = resultSet.getInt("Country_ID");
            String Country_Name = resultSet.getString("Country");
            LocalDateTime createDate = LocalDateTime.parse(resultSet.getString("Create_Date"), formatter);
            String createdBy = resultSet.getString("Created_By");
            Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            Country_Access country = new Country_Access(Country_ID, Country_Name, createDate, createdBy,  lastUpdate,  lastUpdatedBy);
            CountriesList.add(country);
        }
        return CountriesList;
    }
}
