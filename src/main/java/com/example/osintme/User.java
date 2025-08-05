package com.example.osintme;


public class User {
    private final int userId;
    private final String email;
    private final String status;
    private String privilege;

    public User(int userId, String email, String status) {
        this.userId = userId;
        this.email = email;
        this.status = status;
    }

    // Getters for all fields
    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}

