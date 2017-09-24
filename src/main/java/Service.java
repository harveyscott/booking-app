package main.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Service {

    public Booking Validate(Booking booking) {
        // Validate Booking and that Time is free

        // Create a validation exception
        return booking;
    }

    public Booking ParseBooking(LinkedHashMap input) throws ParseException {
        Booking newBook = new Booking();
        newBook.setName((String) input.get("name"));
        newBook.setEmail((String) input.get("email"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        newBook.setDate(sdf.parse((String) input.get("date")));
        return newBook;
    }

    public ArrayList<BookingInfo> getTableBookingsByDate(LinkedHashMap input){
        return repository.getTableBookingsByDate((String) input.get("date"));
    }

    public ArrayList<RestaurantTables> getTables(LinkedHashMap input) {
        return repository.getTables((Integer) input.get("seats"));
    }

    public ArrayList filterTables(ArrayList<RestaurantTables> tables, ArrayList<BookingInfo> bookings) {
        for (RestaurantTables table: tables) {

        }
        return null;
    }

    private Repository repository;
}
