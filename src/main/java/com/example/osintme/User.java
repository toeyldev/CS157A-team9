package com.example.osintme;


public class User {
    private final int userId;
    private final String email;
    private final String status;

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
}