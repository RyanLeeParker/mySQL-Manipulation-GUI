package model;

/**This is the class for the Contacts object.*/
public class Contacts
{
    public int Contact_ID;
    public String Contact_Name;
    public String Email;
    /**This is the contacts constructor, it sets all variables for new Contacts objects. */
    public Contacts(int Contact_ID, String Contact_Name, String Email)
    {
        this.Contact_ID = Contact_ID;
        this.Contact_Name = Contact_Name;
        this.Email = Email;
    }
    /**@return Contact_ID */
    public int getContact_ID()
    {
        return Contact_ID;
    }
    /**@return Contact_Name */
    public String getContact_Name()
    {
        return Contact_Name;
    }
    /**@return Email */
    public String getEmail()
    {
        return Email;
    }
}
