package model;

public class Users {
    private int userId;
    private String userName;
    private String password;

    public Users()                      //int userId, String userName, String password
    {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
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
}