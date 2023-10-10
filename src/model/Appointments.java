package model;

import javax.swing.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Appointments
{
    public int Appointment_ID;
    public String Title;
    public String Description;
    public String Location;
    public String Type;
    public LocalDateTime Start;
    public LocalDateTime End;
    public LocalDateTime Create_Date;
    public Timestamp Last_Update;
    public String Last_Updated_By;
    public int Customer_ID;
    public int User_ID;
    public int Contact_ID;

    public Appointments(int Appointment_ID, String Title, String Description, String Location, String Type, LocalDateTime Start,
                        LocalDateTime End, int Customer_ID, int User_ID, int Contact_ID)   // String Created_By, LocalDateTime Create_Date, Timestamp Last_Update, String Last_Updated_By,
    {
        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        this.Start = Start;
        this.End = End;
        //this.Create_Date = Create_Date;
        //this.Created_By = Created_By;
        //this.Last_Update = Last_Update;
        //this.Last_Updated_By = Last_Updated_By;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;
    }

    public int getAppointment_ID()
    {
        return Appointment_ID;
    }

    public String getTitle()
    {
        return Title;
    }

    public String getDescription()
    {
        return Description;
    }

    public String getLocation()
    {
        return Location;
    }

    public String getType()
    {
        return Type;
    }

    public LocalDateTime getStart()
    {
        return Start;
    }

    public LocalDateTime getEnd()
    {
        return End;
    }

    public int getCustomer_ID()
    {
        return Customer_ID;
    }

    public int getUser_ID()
    {
        return User_ID;
    }

    public int getContact_ID()
    {
        return Contact_ID;
    }
}
