package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**This is the Country object class. */
public class Country
{
    private int Country_ID;
    private String Country_Name;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    /**This is the constructor for the  class. It sets all variables for new objects. */
    public Country(int Country_ID, String Country_Name, LocalDateTime  createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy)
    {
        this.Country_ID = Country_ID;
        this.Country_Name = Country_Name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /**@return Country_ID*/
    public int getCountry_ID()
    {
        return Country_ID;
    }
    /**@return Country_Name */
    public String getCountry_Name()
    {
        return Country_Name;
    }
    /**@return createDate */
    public LocalDateTime  getCreateDate()
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
}
