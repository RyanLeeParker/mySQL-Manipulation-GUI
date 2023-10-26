package dao;


import helper.JDBC;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.LocalDateTime;
import model.First_Level_Division;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.format.DateTimeFormatter;

/** This class controls access and manipulation to the FirstLevelDivision class.*/
public class FirstLevelDivision_Access extends First_Level_Division
{
    /** This method uses the FirstLevelDivision class as a parent to manipulate said class and objects. */
    public FirstLevelDivision_Access(int division_ID, String division_name, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int country_ID)
    {
        super( division_ID, division_name,  createDate, createdBy, lastUpdate, lastUpdatedBy, country_ID);
    }

    /** This method creates and Observable list for the First Level Divisions in the MySQL database.
     * @return First_Level_DivisionList*/
    public static ObservableList<FirstLevelDivision_Access> getFirst_Level_Division() throws SQLException
    {
        ObservableList<FirstLevelDivision_Access> First_Level_DivisionsList = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while(resultSet.next())
        {
            int division_ID = resultSet.getInt("Division_ID");
            String division_name = resultSet.getString("Division");
            int country_ID = resultSet.getInt("Country_ID");
            LocalDateTime createDate = LocalDateTime.parse(resultSet.getString("Create_Date"), formatter);
            String createdBy = resultSet.getString("Created_By");
            Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");

            FirstLevelDivision_Access First_Level_Division = new FirstLevelDivision_Access(division_ID, division_name, createDate, createdBy,  lastUpdate,  lastUpdatedBy, country_ID);         // wonky
            First_Level_DivisionsList.add(First_Level_Division);
        }
        return First_Level_DivisionsList;
    }
}
