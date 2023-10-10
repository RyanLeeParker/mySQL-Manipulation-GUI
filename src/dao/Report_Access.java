package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Reports;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Report_Access extends Appointments
{
    public Report_Access(int Appointment_ID, String Title, String Description, String Location, String Type, LocalDateTime Start, LocalDateTime End, int Customer_ID, int User_ID, int Contact_ID)
    {
        super(Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);
    }

    public static ObservableList<Reports> getFirst_Level_Division() throws SQLException
    {
        ObservableList<Reports> FirstLevelDivisionObservableList = FXCollections.observableArrayList();
        //String sql = "select countries.Country, count(*) as countryCount from customers inner join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID inner join countries on countries.Country_ID = first_level_divisions.Country_ID where  customers.Division_ID = first_level_divisions.Division_ID group by first_level_divisions.Country_ID order by count(*) desc";
        String sql = "select first_level_divisions.Division, count(*) as FirstLevelDivisionCount from customers inner join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID where  customers.Division_ID = first_level_divisions.Division_ID group by first_level_divisions.Division_ID order by count(*) desc";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            String division_name = rs.getString("Division");
            int FirstLevelDivisionCount = rs.getInt("FirstLevelDivisionCount");
            Reports report = new Reports(division_name, FirstLevelDivisionCount);
            FirstLevelDivisionObservableList.add(report);
            //System.out.println(report.getDivision_Name());
        }
        return FirstLevelDivisionObservableList;
    }
}
