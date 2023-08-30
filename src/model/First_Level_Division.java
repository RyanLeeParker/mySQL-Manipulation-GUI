package model;

public class First_Level_Division
{
    private int division_ID;
    private String division_name;
    public int country_ID;

    public First_Level_Division(int division_ID, String division_name, int country_ID)
    {
        this.division_ID = division_ID;
        this.division_name = division_name;
        this.country_ID = country_ID;
    }

    public int getDivision_ID()
    {
        return division_ID;
    }

    public String getDivision_name()
    {
        return division_name;
    }

    public int getCountry_ID()
    {
        return country_ID;
    }
}
