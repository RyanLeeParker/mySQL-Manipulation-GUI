package model;
/**This is the Reports object class. */
public class Reports
{

    public int divisionCount;
    public String division_name;
    public String appointmentMonth;
    public int appointmentTotal;

    /**This is the constructor for the Reports class. It sets all variables for new Reports objects. */
    public Reports(String division_name, int divisionCount)
    {
        this.divisionCount = divisionCount;
        this.division_name = division_name;
    }
    /**@return division_name */
    public String getDivision_name()                                                                        // don't change caps here
    {
        return this.division_name;
    }
    /**@return divisionCount */
    public int getDivisionCount()
    {
        return divisionCount;
    }

}

