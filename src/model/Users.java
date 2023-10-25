package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Users {
    public int userId;
    public String userName;
    public String password;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    public Users(int userId, String userName, String password, LocalDateTime  createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy)
    {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
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
}