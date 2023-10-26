package model;

public class Report_Month
{
    public String appointmentMonth;
    public int appointmentTotal;

    public Report_Month(String appointmentMonth, int appointmentTotal)
    {
        this.appointmentMonth = appointmentMonth;
        this.appointmentTotal = appointmentTotal;
    }

    public String getAppointmentMonth() {

        return appointmentMonth;
    }

    public int getAppointmentTotal() {

        return appointmentTotal;
    }
}
