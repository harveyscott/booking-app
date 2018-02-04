package main.java;

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

    @RequestMapping(value = "/getDates", produces = "application/json", method = RequestMethod.GET)
    public String getDates() {
        ArrayList<String> dl = service.daysList();
        String json = new Gson().toJson(dl);
        return json;
    }

    @RequestMapping(value = "/postBooking", produces = "application/json", method = RequestMethod.POST)
    public String postBooking (
            @RequestParam("table") String tableID,
            @RequestParam("date") String date,
            @RequestParam("time") String time
    ) {
        return null;
    }

    @Autowired
    private Service service;
}
