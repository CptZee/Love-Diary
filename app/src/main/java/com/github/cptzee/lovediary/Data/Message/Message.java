package com.github.cptzee.lovediary.Data.Message;

public class Message {
    private String ID;
    private String sender;
    private String message;
    private String partnerCode;
    private long sentDate;

    public long getSentDate() {
        return sentDate;
    }

    public void setSentDate(long sentDate) {
        this.sentDate = sentDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
}
