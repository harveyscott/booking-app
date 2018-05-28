package main.java;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
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

        // we take final tables and fill each table with free bookings spaces
        for (Integer key: finalTables.keySet()) {
            if (finalTables.get(key).isEmpty()) {
                // If the table has no bookings add all the possible times a user can have.
                finalTables.get(key).addAll(bookingHours(null));
            } else {
                ArrayList<Double> bookingtimes = new ArrayList<>();

                for (String time: finalTables.get(key)) {
                    Double timeToAdd = Double.parseDouble(time);
                    bookingtimes.add(timeToAdd);
                }
                finalTables.get(key).clear();
                finalTables.get(key).addAll(bookingHours(bookingtimes));
            }
        }

        return finalTables;
    }

    private ArrayList<String> bookingHours(ArrayList<Double> times) {
        ArrayList<String> hours = new ArrayList<String>(Arrays.asList("4.0", "4.3", "5.0", "5.3", "6.0", "6.3", "7.0", "7.3", "8.0", "8.3", "9.0", "9.3"));
        if (times == null) {
           return hours;
        }


        // Add the hours to a set
        Set<Double> hoursSet = new HashSet<>();
        for (String stringTimeSlot: hours) {
            hoursSet.add(Double.parseDouble(stringTimeSlot));
        }

        // Want to not allow a user to book for a time too close
        // Create a set with all the times a user cannot choose
        Set<Double> takenTimes = new HashSet<>(times);
        // Add a half hour offset
        for (Double doubleTimeSlot: times) {
            if ((doubleTimeSlot % 1) == 0) {
                double halfHourOffsetForward = doubleTimeSlot + .3;
                if (halfHourOffsetForward < 9.3) {
                    takenTimes.add(halfHourOffsetForward);
                }

                double halfHourOffsetBack = doubleTimeSlot - .7;
                if (halfHourOffsetBack > 4) {
                    takenTimes.add(halfHourOffsetBack);
                }
            } else {
                double halfHourOffsetForward = doubleTimeSlot + .7;
                if (halfHourOffsetForward < 9.3) {
                    takenTimes.add(halfHourOffsetForward);
                }

                double halfHourOffsetBack = doubleTimeSlot - .3;
                if (halfHourOffsetBack > 4) {
                    takenTimes.add(halfHourOffsetBack);
                }
            }

        }

        for (Double timeTaken: takenTimes) {
            hoursSet.remove(timeTaken);
        }

        List<Double> sortedHours = new ArrayList(hoursSet);
        Collections.sort(sortedHours);

        ArrayList<String> modifiedHours = new ArrayList<>();
        // Parse back into a string
        for (Double finalTime: sortedHours) {
            modifiedHours.add(String.valueOf(finalTime));
        }

        return modifiedHours;

    }

    public ArrayList<String> daysList() {
        ArrayList<String> days = new ArrayList<>();

        // Get the next day as it is the first possible day you can make a booking
        // Loop will have to check if the day is not an event
        DateTime tomorrow = new DateTime().plusDays(1);
        if (!tomorrow.dayOfWeek().getAsText().equals("Monday") && !tomorrow.dayOfWeek().getAsText().equals("Tuesday")) {
            days.add(tomorrow.year().getAsText() + "-" + tomorrow.monthOfYear().getAsString() + "-" + tomorrow.dayOfMonth().getAsText());
        }
        // create a loop that will run the next three weeks of dates and add them to an array

        for (int i=1; i<22; i++) {
            DateTime dateCount = tomorrow.plusDays(i);
            if (!dateCount.dayOfWeek().getAsText().equals("Monday") && !dateCount.dayOfWeek().getAsText().equals("Tuesday")) {
                days.add(dateCount.year().getAsText() + "-" + dateCount.monthOfYear().getAsString() + "-" + dateCount.dayOfMonth().getAsText());
            }
        }

        return days;
    }

    public String addBooking(Booking booking, BookingInfo bookingInfo) {

        repository.addBooking(booking, bookingInfo);
        // Add the date to the booking object
        // Retrieve the ID for the booking object to put into this table in the database
        int bookingID = repository.retrieveBookingID(booking);
        bookingInfo.setBookingID(bookingID);
        assert bookingInfo.getBookingID() > 0;

        // Add booking Info to database
        repository.addBookingInfo(bookingInfo);

        // Retrieve the Booking ID for the User and Present the Booking Info to the user
        int bookingTableID;
        bookingTableID = repository.retrieveBookingTablesID(bookingInfo);

        StringBuilder messageToUser = new StringBuilder();
        if (bookingTableID > 0) {
            messageToUser.append("Your Booking Has Successfully been made ");
            messageToUser.append("You have made you booking for ");
            messageToUser.append(bookingInfo.getHours() + "pm");
            messageToUser.append(" on the date: " + bookingInfo.getDate());
            messageToUser.append(" Your booking number is " + bookingTableID);
        } else {
            messageToUser.append("There was a problem making your booking please try again");
        }

        return messageToUser.toString();
    }

    public BookingInfo findBooking(String email, BookingInfo bookingInfo) {
       BookingInfo bkInf = repository.findBookingInfo(bookingInfo.getBookingID());
       int bookingID = repository.verifyBooking(email);
       if (bookingID == bkInf.getBookingID()) {
           return bkInf;
       } else {
           return null;
       }
    }

    public void modifyBooking(BookingInfo bookingInfo) {
        repository.modifyBooking(bookingInfo);
    }


    @Autowired
    private Repository repository;


    public ArrayList<BookingWrapper> getBookingsByDate(String date) {
        // Finds all the bookings based on the date
        ArrayList<BookingWrapper> bookingWrappers = new ArrayList<>();
        ArrayList<BookingInfo> bookingInfos = repository.findBookings(date);

        // for each booking find the customer details
        for (BookingInfo info: bookingInfos) {
            BookingWrapper bookingWrapper = new BookingWrapper();
            bookingWrapper.setBookingInfo(info);
            bookingWrapper.setBooking(repository.findCustomerDetails(info));
            bookingWrappers.add(bookingWrapper);
        }
        return bookingWrappers;
    }

    public ArrayList<Integer> getTableLayout() {
        String layout = repository.getTableLayout();
        String[] nums = layout.split(",");
        ArrayList<String> layoutRaw = new ArrayList<>(Arrays.asList(nums));
        // Parse the values
        for (int i = 0; i < layoutRaw.size() - 1; i++) {
            layoutRaw.set(i, layoutRaw.get(i).substring(1));
        }
        String lastItem = layoutRaw.get(layoutRaw.size() - 1);
        String lastItemFormatted = lastItem.substring(0, lastItem.length() - 1);
        layoutRaw.set(layoutRaw.size() - 1, lastItemFormatted.substring(1));

        ArrayList<Integer> layoutInt = new ArrayList<>();
        for (String number: layoutRaw) {
            layoutInt.add(Integer.parseInt(number));
        }
        return layoutInt;
    }

    public boolean cancelBooking(int id) {
        return repository.cancelBooking(id);
    }

    public Boolean postTableLayout(TableLayout tableLayout) {
        return repository.postTableLayout(tableLayout);
    }
}
