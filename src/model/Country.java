package model;

public class Country
{
    private int Country_ID;
    private static String Country_Name;

    public Country(int Country_ID, String Country_Name)
    {
        this.Country_ID = Country_ID;
        this.Country_Name = Country_Name;
    }

    public int getCountry_ID() {
        return Country_ID;
    }

    public static String getCountry_Name() {
        return Country_Name;
    }
}
