package main.java;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import main.java.RestaurantTables;
import org.springframework.beans.factory.annotation.Autowired;

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

    public void addBooking() {

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
//              I don't know why this works the way it does help me Jesus
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
}
