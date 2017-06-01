package com.rj.astro.androidRecyclerView;

public class Message {

    protected int id;
    protected String message;
    protected String userOrAdmin;



    protected String time;

    public Message(int id, String message,String time ,String userOrAdmin) {
        this.id = id;
        this.message = message;
        this.userOrAdmin = userOrAdmin;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getUserOrAdmin() {
        return userOrAdmin;
    }

    public void setUserOrAdmin(String userOrAdmin) {
        this.userOrAdmin = userOrAdmin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
