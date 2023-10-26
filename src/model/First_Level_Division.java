package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
/**This is the First_Level_Division object class. */
public class First_Level_Division
{
    private int division_ID;
    private String division_name;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    public int country_ID;
    /**This is the constructor for the First_Level_Division class. It sets all variables for new First_Level_Division objects. */
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
    /**@return division_ID */
    public int getDivision_ID()
    {
        return division_ID;
    }
    /**@return division_name */
    public String getDivision_name()
    {
        return division_name;
    }
    /**@return createDate */
    public LocalDateTime getCreateDate()
    {
        return createDate;
    }
    /**@return createdBy */
    public String getCreatedBy()
    {
        return createdBy;
    }
    /**@return lastUpdate */
    public Timestamp getLastUpdate()
    {
        return lastUpdate;
    }
    /**@return lastUpdatedBy */
    public String getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }
    /**@return country_ID */
    public int getCountry_ID()
    {
        return country_ID;
    }
}
