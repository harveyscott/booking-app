package main.java;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@org.springframework.stereotype.Service
public class Service {

    public ArrayList<BookingInfo> getTableBookingsByDate(LinkedHashMap input){
        return repository.getTableBookingsByDate((String) input.get("date"));
    }

    public ArrayList<RestaurantTables> getTables(int guests) {
        return repository.getTables(guests);
    }

    public Map filterTables(ArrayList<RestaurantTables> tables, String date) {
        // You have the list of tables that the user can choose from, from the get table method
        // Use the get bookings method to retrieve all the bookings on that date
        // Look through each table list the times that it is available
        // Show what times are available
        Map <Integer, ArrayList<String>> finalTables = new HashMap<>();

        // Get a list of all the bookings on that date.
        ArrayList<BookingInfo> tableBookings = repository.getTableBookingsByDate(date);

        for (RestaurantTables restaurantTables: tables) {
            ArrayList<String> hours = new ArrayList();
            finalTables.put(restaurantTables.getTableID(), hours);
        }

        if (tableBookings.isEmpty())
        {
            return finalTables;
        }

        for (BookingInfo tableBooking : tableBookings) {
            int tableID = tableBooking.getTableID();
            String hours = tableBooking.getHours();

            assert finalTables != null;
            if (finalTables.containsKey(tableID)) {
                ArrayList hoursList = finalTables.get(tableID);
                hoursList.add(hours);
                finalTables.put(tableID, hoursList);
            }
        }
        return finalTables;
    }

    private Map<String, ArrayList<Integer>> openingHours() {
        ArrayList<Integer> wednesdayToFriday = new ArrayList<>(Arrays.asList(12,13,14,15,18,19,20,21));
        ArrayList<Integer> saturdaySunday = new ArrayList<>(Arrays.asList(12,13,14,15,16,17,18,19,20,21));
        Map<String, ArrayList<Integer>> openingHours = null;
        openingHours.put("Wednesday", wednesdayToFriday);
        openingHours.put("Thursday", wednesdayToFriday);
        openingHours.put("Friday", wednesdayToFriday);
        openingHours.put("Saturday", saturdaySunday);
        openingHours.put("Sunday", saturdaySunday);
        return openingHours;
    }

    public ArrayList<String> daysList() {
        ArrayList<String> days = new ArrayList<>();
        // TODO get possible special events and add them to the days

        // Get the next day as it is the first possible day you can make a booking
        // Loop will have to check if the day is not an event
        DateTime tomorrow = new DateTime().plusDays(1);
        if (!tomorrow.dayOfWeek().getAsText().equals("Monday") && !tomorrow.dayOfWeek().getAsText().equals("Tuesday")) {
            days.add(tomorrow.dayOfMonth().getAsText() + "/" + tomorrow.monthOfYear().getAsString() + "/" + tomorrow.year().getAsText());
        }
        // create a loop that will run the next three weeks of dates and add them to an array

        for (int i=1; i<22; i++) {
            DateTime dateCount = tomorrow.plusDays(i);
            if (!dateCount.dayOfWeek().getAsText().equals("Monday") && !dateCount.dayOfWeek().getAsText().equals("Tuesday")) {
                days.add(dateCount.dayOfMonth().getAsText() + "/" + dateCount.monthOfYear().getAsString() + "/" + dateCount.year().getAsText());
            }
        }

        return days;
    }

    public void addBooking(Booking booking, BookingInfo bookingInfo) {
        repository.addBooking(booking);
        repository.addBookingInfo(bookingInfo);
    }


    @Autowired
    private Repository repository;
}
