package dao;

import helper.JDBC;
import javafx.beans.binding.ObjectExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.*;
import java.time.LocalDateTime;

public class Appointments_Access
{
    public static ObservableList<Appointments> getAppointments() throws SQLException
    {
        ObservableList<Appointments> ObservableList_Appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            int Appointment_ID = rs.getInt("Appointment_ID");
            String Title = rs.getString("Title");
            String Description = rs.getString("Description");
            String Location = rs.getString("Location");
            String Type = rs.getString("Type");

            LocalDateTime Start = rs.getTimestamp("Start").toLocalDateTime();

            LocalDateTime End = rs.getTimestamp("End").toLocalDateTime();
            int Customer_ID = rs.getInt("Customer_ID");
            int User_ID = rs.getInt("User_ID");
            int Contact_ID = rs.getInt("Contact_ID");

            Appointments Appointment = new Appointments(Appointment_ID,Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);

            ObservableList_Appointments.add(Appointment);
        }

        return ObservableList_Appointments;
    }

    //CRUD imp

    public static int removeAppointment(int Customer, Connection connection) throws SQLException
    {
        String query = "DELETE FROM appointments WHERE Appointment_ID=?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        //ResultSet rs = ps.executeQuery();
        ps.setInt(1, Customer);
        int outcome = ps.executeUpdate();
        ps.close();

        return outcome;
    }

}
