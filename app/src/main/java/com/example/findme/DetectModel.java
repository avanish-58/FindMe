package com.example.findme;

import java.util.ArrayList;

public class DetectModel {

    ArrayList<Photos> photos = new ArrayList<Photos>();

    Usage UsageObject;


    // Getter Methods


    public Usage getUsage() {
        return UsageObject;
    }

    // Setter Methods


    public void setUsage(Usage usageObject) {
        this.UsageObject = usageObject;
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
}
 class Photos {
    private String url;
    private String pid;
    private float width;
    private float height;
    ArrayList < Object > tags = new ArrayList < Object > ();


    // Getter Methods

    public String getUrl() {
        return url;
    }

    public String getPid() {
        return pid;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Setter Methods

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}

