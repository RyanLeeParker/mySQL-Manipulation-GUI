package model;
/**This is the Report_Type object class. */
public class Report_Type
{
    public String appointmentType;
    public int appointmentTotal;
    /**This is the constructor for the Report_Type class. It sets all variables for new Report_Type objects. */
    public Report_Type(String appointmentType, int appointmentTotal)
    {
        this.appointmentType = appointmentType;
        this.appointmentTotal = appointmentTotal;
    }
    /**@return appointmentType */
    public String getAppointmentType()
    {

        return appointmentType;
    }
    /**@return appointmentTotal */
    public int getAppointmentTotal()
    {

        return appointmentTotal;
    }

}

