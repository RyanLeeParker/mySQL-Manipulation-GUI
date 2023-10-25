package dao;

import helper.JDBC;
import javafx.beans.binding.ObjectExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class Appointments_Access
{
    public static ObservableList<Appointments> getAppointments() throws SQLException
    {
        ObservableList<Appointments> ObservableList_Appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (rs.next())
        {
            int Appointment_ID = rs.getInt("Appointment_ID");
            String Title = rs.getString("Title");
            String Description = rs.getString("Description");
            String Location = rs.getString("Location");
            String Type = rs.getString("Type");
            LocalDateTime Start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime End = rs.getTimestamp("End").toLocalDateTime();
            LocalDateTime Create_Date = LocalDateTime.parse(rs.getString("Create_Date"), formatter);
            String Created_By = rs.getString("Created_By");
            Timestamp Last_Update = rs.getTimestamp("Last_Update");
            String Last_Updated_By = rs.getString("Last_Updated_By");
            int Customer_ID = rs.getInt("Customer_ID");
            int User_ID = rs.getInt("User_ID");
            int Contact_ID = rs.getInt("Contact_ID");

            Appointments Appointment = new Appointments(Appointment_ID,  Title,  Description,  Location,  Type,  Start,
                 End, Create_Date, Created_By, Last_Update, Last_Updated_By,  Customer_ID,  User_ID,  Contact_ID);

            ObservableList_Appointments.add(Appointment);
        }

        return ObservableList_Appointments;
    }
    public static int removeAppointment(int Customer, Connection connection) throws SQLException
    {
        String query = "DELETE FROM appointments WHERE Appointment_ID=?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ps.setInt(1, Customer);
        int outcome = ps.executeUpdate();
        ps.close();

        return outcome;
    }

}
