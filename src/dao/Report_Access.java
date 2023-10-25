package dao;


import helper.JDBC;
import model.Reports;
import model.Appointments;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Report_Access extends Appointments
{
    public Report_Access(int Appointment_ID, String Title, String Description, String Location, String Type,
                         LocalDateTime Start, LocalDateTime End, LocalDateTime Create_Date, String Created_By,
                         Timestamp Last_Update, String Last_Updated_By, int Customer_ID, int User_ID, int Contact_ID)
    {
        super(Appointment_ID,  Title,  Description,  Location,  Type,  Start, End, Create_Date, Created_By, Last_Update,
                Last_Updated_By,  Customer_ID,  User_ID,  Contact_ID);
    }

    public static ObservableList<Reports> getFirst_Level_Division() throws SQLException
    {
        ObservableList<Reports> FirstLevelDivisionList = FXCollections.observableArrayList();
        String sql = "select first_level_divisions.Division, count(*) as FirstLevelDivisionCount from customers inner join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID where  customers.Division_ID = first_level_divisions.Division_ID group by first_level_divisions.Division_ID order by count(*) desc";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
        {
            String division_name = resultSet.getString("Division");
            int FirstLevelDivisionCount = resultSet.getInt("FirstLevelDivisionCount");
            Reports report = new Reports(division_name, FirstLevelDivisionCount);
            FirstLevelDivisionList.add(report);
        }
        return FirstLevelDivisionList;
    }
}
