package main.java;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Repository
public class Repository {
    private Map<String, String> connectionCred = new HashMap();

    public Repository(){
        connectionCred.put("url", "jdbc:mysql://localhost:3306/booking-app");
        connectionCred.put("username", "root");
        connectionCred.put("password", "");
    }

    public void connectToDatabase(){
        try (Connection connection = DriverManager.getConnection(connectionCred.get("url"), connectionCred.get("username"), connectionCred.get("password"))) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public void addBooking(Booking booking, BookingInfo bookingInfo) {
        Statement stmt;
        String query = "INSERT INTO `booking` (`ID`, `Name`, `numOfGuest`, `Email`, `phone`) VALUES (NULL, '" + booking.getName() + "', '" + booking.getNumOfGuests() + "', '" + booking.getEmail() +"', '" + booking.getPhoneNumber() +"')";
        try {
            Connection connection = DriverManager.getConnection(connectionCred.get("url"), connectionCred.get("username"), connectionCred.get("password"));
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database!", e);
        }
    }

    public int retrieveBookingID(Booking booking) {
        int bookingID = 0;
        Statement stmt;
        String query = "SELECT `ID` FROM `booking` WHERE `Date`= '" + booking.getDateString() + "' AND `Email` = '" + booking.getEmail() + "'";
        try {
            Connection connection = DriverManager.getConnection(connectionCred.get("url"), connectionCred.get("username"), connectionCred.get("password"));
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                bookingID = rs.getInt("bookingID");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database", e);
        }

        return bookingID;
    }

    public void addBookingInfo(BookingInfo bookingInfo) {
        Statement stmt;
        String query = "INSERT INTO `bookingrestauranttables` (`bookingtableID`, `tableID`, `bookingID`, `hours`, `date`, `canceled`) VALUES (NULL, '" + bookingInfo.getTableID() + "', '" + bookingInfo.getBookingID() + "', '" + bookingInfo.getHours() +"', '" + bookingInfo.getDate() +"', '0')";
        try {
            Connection connection = DriverManager.getConnection(connectionCred.get("URL"), connectionCred.get("username"), connectionCred.get("password"));
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database", e);
        }

    }

    public ArrayList<BookingInfo> getTableBookingsByDate(String date){
        ArrayList<BookingInfo> bookings = new ArrayList<>();
        Statement stmt;
        String query = "SELECT * FROM `bookingrestauranttables` WHERE `date` = \" "+ date + "\"";
        try {
            Connection connection = DriverManager.getConnection(connectionCred.get("url"), connectionCred.get("username"), connectionCred.get("password"));
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                BookingInfo newBookingInfo = new BookingInfo();
                newBookingInfo.setBookingID(rs.getInt("bookingID"));
                newBookingInfo.setTableID(rs.getInt("tableID"));
                newBookingInfo.setDate(rs.getString("date"));
                newBookingInfo.setHours(rs.getString("hours"));
                newBookingInfo.setCanceled(rs.getBoolean("canceled"));
                bookings.add(newBookingInfo);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return bookings;
    }

    public ArrayList<RestaurantTables> getTables(Integer numberOfSeats){

        String query = "SELECT * FROM `restauranttables` WHERE `seats` = " + numberOfSeats;
        ArrayList<RestaurantTables> tables = new ArrayList<>();
        try {

            Connection connection = DriverManager.getConnection(connectionCred.get("url"), connectionCred.get("username"), connectionCred.get("password"));
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                RestaurantTables newTable = new RestaurantTables();
                newTable.setSeats(rs.getInt("seats"));
                tables.add(newTable);
                newTable.setTableID(rs.getInt("tableID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tables;
    }

    public ArrayList<TableLayout> getTableLayout(String date) {
        return null;
    }

    public boolean modifyBooking(BookingInfo bookingInfo) {

        return true;
    }

    public BookingInfo findBookingInfo(int bookingId) {
        // Create a bookingInfo object
        BookingInfo bookingInfo = new BookingInfo();
        Statement statement;
        String query = "SELECT * FROM bookingrestauranttables WHERE bookingtableID = " + bookingId;
        try {
            Connection connection = DriverManager.getConnection(connectionCred.get("url"), connectionCred.get("username"), connectionCred.get("password"));
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                bookingInfo.setBookingID(rs.getInt("bookingID"));
                bookingInfo.setTableID(rs.getInt("tableID"));
                bookingInfo.setHours(rs.getString("hours"));
                bookingInfo.setDate(rs.getString("date"));
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return bookingInfo;
    }


    public Booking findBooking(String email) {
        // Create a bookingInfo object
        Booking booking = new Booking();
        Statement statement;
        String query = "SELECT * FROM booking WHERE Email =" + email;
        try {
            Connection connection = DriverManager.getConnection(connectionCred.get("url"), connectionCred.get("username"), connectionCred.get("password"));
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                booking.setName(rs.getString("Name"));
                booking.setNumOfGuests(rs.getInt("numOfGuest"));
                booking.setEmail(rs.getString("Email"));
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return booking;
    }

    public ArrayList<BookingWrapper> findBookings(String date) {
        return null;
    }
}
