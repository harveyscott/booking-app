package main.java;

import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.LinkedHashMap;

@RestController
public class Controller {

    @RequestMapping(method = RequestMethod.POST, value = "/getTables")
    public @ResponseBody
    String getTables(@RequestBody LinkedHashMap input) throws ParseException {
        service.getTables(input);
        service.getTableBookingsByDate(input);

        // TODO a service that filters the table by the seat
        return null;
    }



    private Service service;
}
