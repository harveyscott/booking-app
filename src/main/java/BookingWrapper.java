package main.java;

import java.util.Date;

// This class combines the booking and bookinginfo class for being received by the controller
public class BookingWrapper {

    private String name;
    private Date date;
    private String email;
    private String phoneNumber;

    private int tableID;
    private String hours;



    private Booking booking;
    private BookingInfo bookingInfo;

    public Booking getBooking(BookingWrapper bookingWrapper) {
        booking.setDate(bookingWrapper.date);
        booking.setEmail(bookingWrapper.email);
        booking.setName(bookingWrapper.name);
        booking.setPhoneNumber(bookingWrapper.phoneNumber);

        return booking;
    }

    public BookingInfo getBookingInfo() {
        bookingInfo.setTableID(tableID);

        return bookingInfo;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public void setBookingInfo(BookingInfo bookingInfo) {
        this.bookingInfo = bookingInfo;
    }

}
