package helper;

import dao.Appointments_Access;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/** This is the main time conversion class, it hosts 2 functions to convert appointments to and from UTC and Local times.*/
public class Time
{
    /**
     * This is one of the main time conversion methods used throughout the program, to convert UTC time objects to local time.
     * @throws SQLException
     * @return an observablelist which is converted UTC time appointments into local time appointments*/
    public static ObservableList<Appointments> convertTimeDateLocal() throws SQLException                               //Convert UTC SQL into local time
    {
        ObservableList<Appointments> allAppointmentsList = Appointments_Access.getAppointments();
        ObservableList<Appointments> LocalAppointmentsList = FXCollections.observableArrayList();
        ZoneId systemZone = ZoneId.systemDefault();

        for (Appointments appointment : allAppointmentsList)
        {
            LocalDateTime temp_Start = appointment.getStart();
            ZonedDateTime ZDT_start = temp_Start.atZone(ZoneId.of("UTC"));
            ZonedDateTime ZDT_final_start = ZDT_start.withZoneSameInstant(systemZone);
            LocalDateTime Start = ZDT_final_start.toLocalDateTime();

            LocalDateTime temp_End = appointment.getEnd();
            ZonedDateTime ZDT_end = temp_End.atZone(ZoneId.of("UTC"));
            ZonedDateTime ZDT_final_end = ZDT_end.withZoneSameInstant(systemZone);
            LocalDateTime End = ZDT_final_end.toLocalDateTime();

            Integer Appointment_ID = appointment.getAppointment_ID();
            String Title = appointment.getTitle();
            String Description = appointment.getDescription();
            String Location = appointment.getLocation();
            String Type = appointment.getType();
            LocalDateTime Create_Date = appointment.getCreateDate();
            String Created_By = appointment.getCreatedBy();
            Timestamp Last_Update = appointment.getLastUpdate();
            String Last_Updated_By = appointment.getLastUpdatedBy();
            Integer Customer_ID = appointment.getCustomer_ID();
            Integer User_ID = appointment.getUser_ID();
            Integer Contact_ID = appointment.getContact_ID();

            Appointments appointments = new Appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID);
            LocalAppointmentsList.add(appointments);
        }
        return LocalAppointmentsList;
    }
    /**
     * This is one of the main time conversion methods used throughout the program, to convert local time objects to UTC time.
     * @throws SQLException
     * @return an observablelist which is converted local time appointments into UTC time appointments
     * A lambda expression was used due to the sheer size difference of the first expression, which performs a similar task at 5x the lines.*/
    public static String convertTimeDateUTC(String dateTime) {
        return Timestamp.valueOf(dateTime)
                .toLocalDateTime()
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("UTC"))
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
    }

}
