package uk.ac.cam.jk810.yhack20.model;

import java.util.Date;

public class Message {
    private String senderid;
    private String message;
    private Date timestamp;

    public Message(String sender, String message, Date timestamp) {
        this.senderid = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return senderid;
    }

    public void setSender(String sender) {
        this.senderid = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    
}
