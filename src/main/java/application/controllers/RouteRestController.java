package application.controllers;

import application.Calculator;
import entities.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repositories.AirportRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/getRoute")
public class RouteRestController {

    @Autowired
    private AirportRepository airportRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String getRoute (@RequestParam(value = "org", required = false) String orgS,
                                          @RequestParam(value = "des", required = false) String desS,
                                          @RequestParam(value = "range", required = false) String rangeS) {
        String routeJson = "{";
        try {
            Airport orgA = airportRepository.findByIataCode(orgS);
            Airport desA = airportRepository.findByIataCode(desS);
            routeJson += String.format("\"distance\":%d, ", Calculator.distance(orgA, desA));
            int rangeInt = Integer.valueOf(rangeS);
            if (Calculator.reachable(orgA, desA, rangeInt)) {
                routeJson += String.format("\"reachable\":true, \"route\":[%s, %s]", orgA.toString(), desA.toString());
            } else {
                ArrayList<Airport> route = Calculator.lessReroute(orgA, desA, rangeInt, airportRepository);
                if (route.isEmpty()) {
                    routeJson += "\"reachable\":false, \"route\":[]";
                } else {
                    routeJson += String.format("\"reachable\":true, \"route\":[%s, ", orgA.toString());
                    for (int i = route.size() - 1; i >= 0; i--) {
                        Airport via = route.get(i);
                        routeJson += String.format("%s, ", via.toString());
                    }
                    routeJson += String.format("%s]", desA.toString());
                }
            }
        } catch (Exception e) {

        }
        routeJson += "}";
        return routeJson;
    }

    private List reachableAirports(Airport org, int range) {
        List airports = new ArrayList<>();
        for (Airport des : airportRepository.findAll()) {
            if (Calculator.reachable(org, des, range) && !org.getId().equals(des.getId())) {
                airports.add(des);
            }
        }
        return airports;
    }
}
