package main.java;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class Controller {

//    @RequestMapping(method = RequestMethod.POST, value = "/getTables")
//    public @ResponseBody
//    String getTables(@RequestBody LinkedHashMap input) throws ParseException {
//        service.getTables(input);
//        service.getTableBookingsByDate(input);
//
//        // TODO a service that filters the table by the seat
//        return null;
//    }

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
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date date;
        try {
            date = df.parse(dateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayList<RestaurantTables> rt = service.getTables(guests);


        String json = new Gson().toJson(rt);
        return json;
    }

    @Autowired
    private Service service;
}
