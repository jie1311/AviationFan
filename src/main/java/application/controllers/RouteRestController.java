package application.controllers;

import application.Calculator;
import entities.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repositories.AirportRepository;

import java.util.ArrayList;

@RestController
@RequestMapping("/getRoute")
public class RouteRestController {

    @Autowired
    private AirportRepository airportRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String getRoute (@RequestParam(value = "org", required = false) String orgS,
                                          @RequestParam(value = "des", required = false) String desS,
                                          @RequestParam(value = "range", required = false) String rangeS,
                                          @RequestParam(value = "way", defaultValue = "minr", required = false) String way) {
        String routeJson = "{";
        try {
            Airport orgA = airportRepository.findByIataCode(orgS);
            Airport desA = airportRepository.findByIataCode(desS);
            routeJson += String.format("\"distance\":%d, ", Calculator.distance(orgA, desA));
            int rangeInt = Integer.valueOf(rangeS);
            if (Calculator.reachable(orgA, desA, rangeInt)) {
                routeJson += String.format("\"reachable\":true, \"route\":[%s, %s]", orgA.toString(), desA.toString());
            } else {
                ArrayList<Airport> route;
                if (way.equals("minr")) {
                    route = Calculator.lessReroute(orgA, desA, rangeInt, airportRepository);
                } else if (way.equals("mint")) {
                    route = Calculator.lessTravel(orgA, desA, rangeInt, airportRepository);
                } else {
                    route = Calculator.lessReroute(orgA, desA, rangeInt, airportRepository);
                }
                if (route.isEmpty()) {
                    routeJson += "\"reachable\":false, \"route\":[]";
                } else {
                    routeJson += "\"reachable\":true, \"route\":[";
                    for (int i = 0; i <= route.size() - 2; i++) {
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
}
