package application.controllers;

import application.Calculator;
import entities.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repositories.AirportRepository;

import java.util.ArrayList;
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
            int rangeInt = Integer.valueOf(rangeS);
            if (Calculator.reachable(orgA, desA, rangeInt)) {
                routeJson += String.format("\"reachable\":true, \"route\":[%s. %s]", orgA.toString(), desA.toString());
            } else {
                List searched = new ArrayList<>();
                searched.add(orgA);
                List<Airport> route = searchResult(orgA, desA, rangeInt, searched, new ArrayList<>());
                while (!(route.isEmpty() || Calculator.reachable(route.get(route.size() - 1), orgA, rangeInt))) {
                    searched.clear();
                    searched.add(orgA);
                    route.addAll(searchResult(orgA, route.get(route.size() - 1), rangeInt, searched, new ArrayList<>()));
                }
                if (route.isEmpty()) {
                    routeJson += "\"reachable\":false";
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

    private List<Airport> searchResult(Airport org, Airport des, int range, List searched, List result) {
        List<Airport> available = reachableAirports(org, range);
        boolean found = false;
        for (Airport via : available) {
            if (via.getId().equals(des.getId())) {
                found = true;
                result.add(org);
                break;
            }
        }
        if (!found) {
            out: for (Airport via : available) {
                boolean origin = false;
                in: for (Airport srd : (ArrayList<Airport>) searched) {
                    if (srd.getId().equals(via.getId())) {
                        origin = true;
                        break in;
                    }
                }
                if (!origin) {
                    searched.add(via);
                    searchResult(via, des, range, searched, result);
                    break out;
                }
            }
        }
        return result;
    }
}
