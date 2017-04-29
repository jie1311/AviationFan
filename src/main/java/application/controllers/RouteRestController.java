package application.controllers;
//todo: String iteration; List reorder

import application.Calculator;
import application.JsonBuilder;
import application.Output;
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
        Output output = new Output();
        try {
            Airport orgA = airportRepository.findByIataCode(orgS);
            Airport desA = airportRepository.findByIataCode(desS);
            int rangeInt = Integer.valueOf(rangeS);
            if (Calculator.reachable(orgA, desA, rangeInt)) {
                ArrayList<Airport> route = new ArrayList<>();
                route.add(orgA);
                route.add(desA);
                output.setSuccess(true);
                output.setData(JsonBuilder.buildRouteJsonObject(orgA, desA, true, route));
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
                    output.setSuccess(true);
                    output.setData(JsonBuilder.buildRouteJsonObject(orgA, desA, false, route));
                } else {
                    output.setSuccess(true);
                    output.setData(JsonBuilder.buildRouteJsonObject(orgA, desA, true, route));
                }
            }
        } catch (Exception e) {
            output.setSuccess(false);
            output.setMessage("Unexpected error happened.");
            output.setData("{}");
        }

        return output.toString();
    }
}
