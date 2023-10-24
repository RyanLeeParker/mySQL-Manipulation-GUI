package model;

public class Report_Month
{
    public String appointmentMonth;
    public int appointmentTotal;

    /**
     * @param appointmentMonth
     * @param appointmentTotal
     */
    public Report_Month(String appointmentMonth, int appointmentTotal)
    {
        this.appointmentMonth = appointmentMonth;
        this.appointmentTotal = appointmentTotal;
    }

    /**
     * @return appointmentMonth
     */
    public String getAppointmentMonth() {

        return appointmentMonth;
    }

    /**
     * @return appointmentTotal
     */
    public int getAppointmentTotal() {

        return appointmentTotal;
    }
}
