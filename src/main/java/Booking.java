package main.java;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking{
    private String name;
    private int numOfGuests;
    private Date date;
    private String email;
    private String phoneNumber;

    public String getEmail() {
        return email;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getNumOfGuests() {
        return numOfGuests;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNumOfGuests(int numOfGuests) {
        this.numOfGuests = numOfGuests;
    }
}
