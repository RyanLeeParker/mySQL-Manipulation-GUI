package model;

public class Report_Type
{
    public String appointmentType;
    public int appointmentTotal;

    /**
     * @param appointmentTotal
     * @param appointmentType
     */
    public Report_Type(String appointmentType, int appointmentTotal)
    {
        this.appointmentType = appointmentType;
        this.appointmentTotal = appointmentTotal;
    }

    /**
     * getters
     */
    public String getAppointmentType() {

        return appointmentType;
    }

    public int getAppointmentTotal() {

        return appointmentTotal;
    }

}

