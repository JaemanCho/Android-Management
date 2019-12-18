package com.example.management;

public class User {

    String userId;
    String userPassword;
    String userName;
    String userAge;

    public User(String userId, String userPassword, String userName, String userAge) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userAge = userAge;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }
}
