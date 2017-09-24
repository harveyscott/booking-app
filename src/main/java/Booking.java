package main.java;

import java.util.Date;

public class Booking {
    private String name;
    private Date date;
    private String email;

    public Booking() {

    }

    public String getEmail() {
        return email;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}