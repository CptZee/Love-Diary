package com.github.cptzee.lovediary.Data.Post;

public class Post {
    private String ID;
    private long datePosted;
    private String message;
    private String owner;
    private String parent;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public long getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(long datePosted) {
        this.datePosted = datePosted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

}
