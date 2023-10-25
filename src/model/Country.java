package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Country
{
    private int Country_ID;
    private String Country_Name;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    public Country(int Country_ID, String Country_Name, LocalDateTime  createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy)
    {
        this.Country_ID = Country_ID;
        this.Country_Name = Country_Name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountry_ID()
    {
        return Country_ID;
    }

    public String getCountry_Name()
    {
        return Country_Name;
    }
    public LocalDateTime  getCreateDate()
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
}
