package com.example.osintme;

import java.sql.Timestamp;

// simple class to display activity log entries

public class ActivityLogEntry {
    private int logId;
    private Timestamp timestamp;
    private int userId;
    private int actionId;

    // Constructors
    public ActivityLogEntry() {
    }

    public ActivityLogEntry(int logId, Timestamp timestamp, int userId, int actionId) {
        this.logId = logId;
        this.timestamp = timestamp;
        this.userId = userId;
        this.actionId = actionId;
    }

    // Getters and Setters
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }
}
