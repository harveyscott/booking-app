package main.java;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class Controller {

    @RequestMapping(value = "/getTables", produces = "application/json", method = RequestMethod.GET)
    public String getTables(
            @RequestParam(value = "guests", required = true) String guestsInput,
            @RequestParam(value = "date", required = true) String dateInput
    ) {
        int guests = Integer.parseInt(guestsInput);
        ArrayList<RestaurantTables> rt = service.getTables(guests);
        Map restaurantMap = service.filterTables(rt, dateInput);
        String json = new Gson().toJson(restaurantMap);
        return json;
    }

    @RequestMapping(value = "/getDates", produces = "application/json", method = RequestMethod.GET)
    public ArrayList<String> getDates() {
        ArrayList<String> dl = service.daysList();
        String json = new Gson().toJson(dl);
        return dl;
    }

    @RequestMapping(value = "/postBooking", method = RequestMethod.POST)
    public ResponseEntity postBooking (
            @RequestBody BookingWrapper body
    ) {
        BookingWrapper bookingWrapper = body.getBookingWrapper();
        Booking booking = bookingWrapper.getBooking();
        BookingInfo bookingInfo = bookingWrapper.getBookingInfo();
        service.addBooking(booking, bookingInfo);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // Requires Authentication
    @RequestMapping(value = "/modifyBooking", method = RequestMethod.POST)
    public ResponseEntity modifyBooking (
            @RequestBody BookingWrapper request
    ) {
       // Validate Booking Info
       StringBuilder message = new StringBuilder();
       BookingInfo bookingInfo = request.getBookingInfo();
       Booking booking = request.getBookingWrapper().getBooking();

       BookingWrapper currentBooking = service.findBooking(bookingInfo.getBookingID(),booking.getEmail());

       // Validate Booking info
       if(currentBooking != null)  {
            service.modifyBooking(bookingInfo);
       } else {
           message.append("No Booking returned");
       }

       return null;
    }

    @RequestMapping(value = "/getBookingsByDate", method = RequestMethod.GET)
    public ArrayList<BookingWrapper> getBookingByDate( @RequestParam String date) {
        return new ArrayList<>(service.getBookingsByDate(date));
    }

    @RequestMapping(value = "/getTableLayout", method = RequestMethod.GET)
    public ResponseEntity getTableLayout ( @RequestParam String date) {
        TableLayout tableLayout = service.getTableLayout(date);
        tableLayout.getLayout();
        return null;
    }

    @RequestMapping(value = "/postTableLayout", method = RequestMethod.POST)
    public ResponseEntity postTableLayout(
            @RequestBody TableLayout tableLayout
    ) {
        return null;
    }

    @Autowired
    private Service service;
}
