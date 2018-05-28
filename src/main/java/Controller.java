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
    public String postBooking (
            @RequestBody BookingWrapper body
    ) {
        BookingWrapper bookingWrapper = body.getBookingWrapper();
        Booking booking = bookingWrapper.getBooking();
        BookingInfo bookingInfo = bookingWrapper.getBookingInfo();
        return service.addBooking(booking, bookingInfo);
    }

    @RequestMapping(value = "/getBooking", method = RequestMethod.GET)
    public BookingInfo getBooking(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "id") int id
    ) {
        BookingInfo bookingInfo = new BookingInfo();
        bookingInfo.setBookingID(id);
        return service.findBooking(email, bookingInfo);
    }

    // Requires Authentication
    @RequestMapping(value = "/modifyBooking", method = RequestMethod.GET)
    public ResponseEntity modifyBooking (
            @RequestBody BookingWrapper request
    ) {
       // Validate Booking Info
       StringBuilder message = new StringBuilder();
       BookingInfo bookingInfo = request.getBookingInfo();
       Booking booking = request.getBookingWrapper().getBooking();

//       BookingWrapper currentBooking = service.findBooking(bookingInfo.getBookingID(),booking.getEmail());

       // Validate Booking info
//       if(currentBooking != null)  {
//            service.modifyBooking(bookingInfo);
//       } else {
//           message.append("No Booking returned");
//       }

       return null;
    }

    @RequestMapping(value = "/cancelBooking", method = RequestMethod.GET)
    public boolean cancelBooking (
            @RequestParam(value = "id") int id
            ) {
        return service.cancelBooking(id);
    }

    @RequestMapping(value = "/getBookingsByDate", method = RequestMethod.GET)
    public ArrayList<BookingWrapper> getBookingByDate( @RequestParam String date) {
        return new ArrayList<>(service.getBookingsByDate(date));
    }

    @RequestMapping(value = "/getTableLayout", method = RequestMethod.GET)
    public ArrayList<Integer> getTableLayout () {
        return service.getTableLayout();
    }

    @RequestMapping(value = "/postTableLayout", method = RequestMethod.POST)
    public Boolean postTableLayout(
            @RequestBody TableLayout tableLayout
    ) {
        return service.postTableLayout(tableLayout);
    }

    @Autowired
    private Service service;
}
