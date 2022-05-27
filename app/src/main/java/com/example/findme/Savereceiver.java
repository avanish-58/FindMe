package com.example.findme;

import java.util.ArrayList;

public class Savereceiver {
    private String status;
    ArrayList< Object > saved_tags = new ArrayList < Object > ();
    private String message;


    // Getter Methods

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
