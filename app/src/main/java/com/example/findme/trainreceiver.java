package com.example.findme;

import java.util.ArrayList;

public class trainreceiver {
    private String status;
    ArrayList < Object > created = new ArrayList < Object > ();
    Usage UsageObject;


    // Getter Methods

    public String getStatus() {
        return status;
    }

    public Usage getUsage() {
        return UsageObject;
    }

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUsage(Usage usageObject) {
        this.UsageObject = usageObject;
    }
}
 class Usage {
    private float used;
    private float remaining;
    private float limit;
    private String reset_time_text;
    private float reset_time;


    // Getter Methods

    public float getUsed() {
        return used;
    }

    public float getRemaining() {
        return remaining;
    }

    public float getLimit() {
        return limit;
    }

    public String getReset_time_text() {
        return reset_time_text;
    }

    public float getReset_time() {
        return reset_time;
    }

    // Setter Methods

    public void setUsed(float used) {
        this.used = used;
    }

    public void setRemaining(float remaining) {
        this.remaining = remaining;
    }

    public void setLimit(float limit) {
        this.limit = limit;
    }

    public void setReset_time_text(String reset_time_text) {
        this.reset_time_text = reset_time_text;
    }

    public void setReset_time(float reset_time) {
        this.reset_time = reset_time;
    }
}