package dao;

import java.sql.*;
import helper.JDBC;
import model.Appointments;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.format.DateTimeFormatter;

public class Appointments_Access
{
    public static ObservableList<Appointments> getAppointments() throws SQLException
    {
        ObservableList<Appointments> AppointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (resultSet.next())
        {
            int Appointment_ID = resultSet.getInt("Appointment_ID");
            String Title = resultSet.getString("Title");
            String Description = resultSet.getString("Description");
            String Location = resultSet.getString("Location");
            String Type = resultSet.getString("Type");
            LocalDateTime Start = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime End = resultSet.getTimestamp("End").toLocalDateTime();
            LocalDateTime Create_Date = LocalDateTime.parse(resultSet.getString("Create_Date"), formatter);
            String Created_By = resultSet.getString("Created_By");
            Timestamp Last_Update = resultSet.getTimestamp("Last_Update");
            String Last_Updated_By = resultSet.getString("Last_Updated_By");
            int Customer_ID = resultSet.getInt("Customer_ID");
            int User_ID = resultSet.getInt("User_ID");
            int Contact_ID = resultSet.getInt("Contact_ID");

            Appointments Appointment = new Appointments(Appointment_ID,  Title,  Description,  Location,  Type,  Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By,  Customer_ID,  User_ID,  Contact_ID);

            AppointmentsList.add(Appointment);
        }

        return AppointmentsList;
    }
    public static int removeAppointment(int Customer, Connection connection) throws SQLException
    {
        String query = "DELETE FROM appointments WHERE Appointment_ID=?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, Customer);
        int outcome = preparedStatement.executeUpdate();
        preparedStatement.close();

        return outcome;
    }
}
