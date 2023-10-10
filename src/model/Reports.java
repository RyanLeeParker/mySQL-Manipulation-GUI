package model;

public class Reports
{

    public int divisionCount;
    public String division_name;
    public String appointmentMonth;
    public int appointmentTotal;

    /**
     * @param division_name
     * @param divisionCount
     */
    public Reports(String division_name, int divisionCount)
    {
        this.divisionCount = divisionCount;
        this.division_name = division_name;
    }


    /**
     * Returns division name for custom report.
     * @return division_name
     */
    public String getDivision_name()                                                                        // don't change caps here
    {
        return this.division_name;
    }

    /**
     * Total for each state/FLD.
     * @return divisionCount
     */
    public int getDivisionCount()
    {
        return divisionCount;
    }

}

