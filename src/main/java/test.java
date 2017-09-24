package main.java;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class test {
    public static void main(String args[]){
        String url = "jdbc:mysql://localhost:3306/booking-app";
        String username = "root";
        String password = "";
        String stringDate = "2017-09-21";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        ArrayList<BookingInfo> bookings = new ArrayList<>();
        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Connecting database...");

        Statement stmt = null;
        String query = "SELECT * FROM `bookingrestauranttables` WHERE `date` = \" "+ stringDate + "\"";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
