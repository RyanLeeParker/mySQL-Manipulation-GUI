package model;

import javax.swing.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
/** This is the main class for the Appointments model.*/
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
    public String Created_By;
    public Timestamp Last_Update;
    public String Last_Updated_By;
    public int Customer_ID;
    public int User_ID;
    public int Contact_ID;

    /** This is the constructor for the Appointments object, it sets all variables for each new Appointment object.*/
    public Appointments(int Appointment_ID, String Title, String Description, String Location, String Type, LocalDateTime Start,
                        LocalDateTime End, LocalDateTime Create_Date, String Created_By, Timestamp Last_Update, String Last_Updated_By, int Customer_ID, int User_ID, int Contact_ID)
    {
        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        this.Start = Start;
        this.End = End;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;
    }
    /** @return Appointment_ID*/
    public int getAppointment_ID()
    {
        return Appointment_ID;
    }
    /**@return Title */
    public String getTitle()
    {
        return Title;
    }
    /**@return Description */
    public String getDescription()
    {
        return Description;
    }
    /**@return Location */
    public String getLocation()
    {
        return Location;
    }
    /**@return Type */
    public String getType()
    {
        return Type;
    }
    /**@return Start */
    public LocalDateTime getStart()
    {
        return Start;
    }
    /**@return End */
    public LocalDateTime getEnd()
    {
        return End;
    }
    /**@return Create_Date */
    public LocalDateTime getCreateDate()
    {
        return Create_Date;
    }
    /**@return Created_By */
    public String getCreatedBy()
    {
        return Created_By;
    }
    /**@return Last_Update */
    public Timestamp getLastUpdate()
    {
        return Last_Update;
    }
    /**@return Last_Updated_By */
    public String getLastUpdatedBy()
    {
        return Last_Updated_By;
    }
    /**@return Custom_ID */
    public int getCustomer_ID()
    {
        return Customer_ID;
    }
    /**@return User_ID */
    public int getUser_ID()
    {
        return User_ID;
    }
    /**@return Contact_ID */
    public int getContact_ID()
    {
        return Contact_ID;
    }
}
