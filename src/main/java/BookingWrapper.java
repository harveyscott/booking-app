package main.java;

// This class combines the booking and bookinginfo class for being received by the controller
public class BookingWrapper {

    private Booking booking;
    private BookingInfo bookingInfo;

    public Booking getBooking() {
        return booking;
    }

    public BookingInfo getBookingInfo() {
        return bookingInfo;
    }
}
