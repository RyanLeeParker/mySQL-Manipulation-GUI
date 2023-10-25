package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.First_Level_Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FirstLevelDivision_Access extends First_Level_Division
{
    public FirstLevelDivision_Access(int division_ID, String division_name, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int country_ID)
    {
        super( division_ID, division_name,  createDate, createdBy, lastUpdate, lastUpdatedBy, country_ID);
    }

    public static ObservableList<FirstLevelDivision_Access> getFirst_Level_Division() throws SQLException
    {
        ObservableList<FirstLevelDivision_Access> ObservableList_First_Level_Divisions = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while(rs.next())
        {
            int division_ID = rs.getInt("Division_ID");
            String division_name = rs.getString("Division");
            int country_ID = rs.getInt("Country_ID");
            LocalDateTime createDate = LocalDateTime.parse(rs.getString("Create_Date"), formatter);
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            FirstLevelDivision_Access First_Level_Division = new FirstLevelDivision_Access(division_ID, division_name, createDate, createdBy,  lastUpdate,  lastUpdatedBy, country_ID);         // wonky
            ObservableList_First_Level_Divisions.add(First_Level_Division);

        }
        return ObservableList_First_Level_Divisions;
    }

}
