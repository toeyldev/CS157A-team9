package com.example.osintme;
import java.sql.Timestamp;

public class ScanSummary {
    private final int scanId;
    private final Timestamp scanTime;
    private final String status;

    public ScanSummary(int scanId, Timestamp scanTime, String status) {
        this.scanId   = scanId;
        this.scanTime = scanTime;
        this.status   = status;
    }

    public int getScanId()   { return scanId; }

    public Timestamp getScanTime() { return scanTime; }

    public String getStatus(){ return status; }
}