package model;

public class Contacts
{
    public int Contact_ID;
    public String Contact_Name;
    public String Email;

    public Contacts(int Contact_ID, String Contact_Name, String Email)
    {
        this.Contact_ID = Contact_ID;
        this.Contact_Name = Contact_Name;
        this.Email = Email;
    }

    public int getContact_ID()
    {
        return Contact_ID;
    }

    public String getContact_Name()
    {
        return Contact_Name;
    }

    public String getEmail()
    {
        return Email;
    }
}
