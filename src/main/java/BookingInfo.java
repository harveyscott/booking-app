package main.java;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class BookingInfo {
    private Integer tableID;
    private Integer bookingID;
    private String hours;
    private String date;
    private boolean canceled;

    public void setDate(String date) {
        this.date = date;
    }

    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setTableID(Integer tableID) {
        this.tableID = tableID;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public Integer getBookingID() {
        return bookingID;
    }

    public Integer getTableID() {
        return tableID;
    }

    public String getHours() {
        return hours;
    }

    public Pair<String, LocalDate> getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd", Locale.ENGLISH);
        LocalDate localDate = LocalDate.parse(date, formatter);
        return Pair.of(date, localDate);
    }

}
