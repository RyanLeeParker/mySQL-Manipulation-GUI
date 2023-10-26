package helper;

import dao.Appointments_Access;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;


public class Time
{
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
    public static String convertTimeDateUTC(String dateTime) {                                                          // lambda 3, Converts Local time into UTC.
        return Timestamp.valueOf(dateTime)
                .toLocalDateTime()
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("UTC"))
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
    }

}
