package main.java;

import com.google.gson.Gson;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class Controller {

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String getTest() {
        return "This works";
    }

    @RequestMapping(value = "/getTables", produces = "application/json", method = RequestMethod.GET)
    public String getTables(
            @RequestParam(value = "guests", required = true) String guestsInput,
            @RequestParam(value = "date", required = false) String dateInput
    ) {
        int guests = Integer.parseInt(guestsInput);
        ArrayList<RestaurantTables> rt = service.getTables(guests);
        Map restaurantMap = service.filterTables(rt, dateInput);
        String json = new Gson().toJson(restaurantMap);
        return json;
    }

    @RequestMapping(value = "/getDates", produces = "application/json", method = RequestMethod.GET)
    public String getDates() {
        ArrayList<String> dl = service.daysList();
        String json = new Gson().toJson(dl);
        return json;
    }

    @RequestMapping(value = "/postBooking", method = RequestMethod.POST)
    public ResponseEntity postBooking (
            @RequestBody BookingWrapper request
    ) {
        Booking booking = request.getBooking();
        BookingInfo bookingInfo = request.getBookingInfo();
        // Service requests to validate here. Probably...most likely...maybe not
        service.addBooking(booking, bookingInfo);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // Requires Authentication
    @RequestMapping(value = "/modifyBooking", method = RequestMethod.POST)
    public ResponseEntity modifyBooking (BookingInfo request) {
        return null;
    }

    // Requires Authentication
    @RequestMapping(value = "/deleteBooking", method = RequestMethod.DELETE)
    public ResponseEntity deleteBooking (BookingWrapper request) {
        return null;
    }

    @RequestMapping(value = "/getBookingsByDate", method = RequestMethod.GET)
    public ResponseEntity getBookingByDate( @RequestParam String Date) {
        return null;
    }

    @RequestMapping(value = "/getTableLayout", method = RequestMethod.GET)
    public ResponseEntity getTableLayout ( @RequestParam String Date) {
        return null;
    }

    @RequestMapping(value = "/postTableLayout", method = RequestMethod.POST)
    public ResponseEntity postTableLayout(@RequestParam Object obj) {
        return null;
    }

    @Autowired
    private Service service;
}
