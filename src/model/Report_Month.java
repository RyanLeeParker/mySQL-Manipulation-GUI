package model;
/**This is the Report_Month object class. */
public class Report_Month
{
    public String appointmentMonth;
    public int appointmentTotal;
    /**This is the constructor for the Report_Month class. It sets all variables for new Report_Month objects. */
    public Report_Month(String appointmentMonth, int appointmentTotal)
    {
        this.appointmentMonth = appointmentMonth;
        this.appointmentTotal = appointmentTotal;
    }
    /**@return appointmentMonth */
    public String getAppointmentMonth() {

        return appointmentMonth;
    }
    /**@return appointmentTotal */
    public int getAppointmentTotal() {

        return appointmentTotal;
    }
}
