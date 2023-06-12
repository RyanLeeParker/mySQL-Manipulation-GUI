package dao;

import javax.swing.*;

public class Appointments
{
    public int Appointment_ID;
    public String Title;
    public String Description;
    public String Location;
    public String Type;
    public String Start;
    public String End;
    public String Create_Date;
    public String Created_By;
    public String Last_Update;
    public String Last_Updated_By;
    public int Customer_ID;
    public int User_ID;
    public int Contact_ID;

    public Appointments(int Appointment_ID, String Title, String Description, String Location, String Type, String Start, String End, String Create_Date, String Created_By,
                        String Last_Update, String Last_Updated_By, int Customer_ID, int User_ID, int Contact_ID)
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

}
