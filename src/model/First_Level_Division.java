package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class First_Level_Division
{
    private int division_ID;
    private String division_name;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    public int country_ID;

    public First_Level_Division(int division_ID, String division_name, LocalDateTime  createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int country_ID)
    {
        this.division_ID = division_ID;
        this.division_name = division_name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
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

    public LocalDateTime getCreateDate()
    {
        return createDate;
    }
    public String getCreatedBy()
    {
        return createdBy;
    }

    public Timestamp getLastUpdate()
    {
        return lastUpdate;
    }

    public String getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }
    public int getCountry_ID()
    {
        return country_ID;
    }
}
