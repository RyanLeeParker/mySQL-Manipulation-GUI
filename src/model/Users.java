package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
/**This is the Users object class. */
public class Users
{
    public int userId;
    public static String userName;
    public String password;
    public LocalDateTime createDate;
    public String createdBy;
    public Timestamp lastUpdate;
    public String lastUpdatedBy;
    /**This is the constructor for the Users class. It sets all variables for new Users objects. */
    public Users(int userId, String userName, String password, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy)
    {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /**@return userId */
    public int getUserId() {
        return userId;
    }
    /**@return userName */
    public String getUserName()
    {
        return userName;
    }
    /**@return password */
    public String getPassword() {
        return password;
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
}