package main.java;

// This class combines the booking and bookinginfo class for being received by the controller
public class BookingWrapper {

    private BookingWrapper bookingWrapper;
    private Booking booking;
    private BookingInfo bookingInfo;

    public Booking getBooking() {
        return booking;
    }

    public BookingInfo getBookingInfo() {
        return bookingInfo;
    }

    public BookingWrapper getBookingWrapper() {
        return bookingWrapper;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public void setBookingInfo(BookingInfo bookingInfo) {
        this.bookingInfo = bookingInfo;
    }

    public void setBookingWrapper(BookingWrapper bookingWrapper) {
        this.bookingWrapper = bookingWrapper;
    }
}
