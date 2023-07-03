package dao;

import javafx.collections.ObservableList;
import model.Appointments;
import model.Reports;

import java.time.LocalDateTime;

public class Report_Access extends Appointments
{
    public Report_Access(int Appointment_ID, String Title, String Description, String Location, String Type, LocalDateTime Start, LocalDateTime End, int Customer_ID, int User_ID, int Contact_ID) {
        super(Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);
    }

    //public ObservableList<Reports>;
}
