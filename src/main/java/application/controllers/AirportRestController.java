package application.controllers;


import entities.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repositories.AirportRepository;

@RestController
@RequestMapping("/getAirport")
public class AirportRestController {

    @Autowired
    private AirportRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String getAirport (@RequestParam(value="iata", required=false) String iataCode,
                                            @RequestParam(value="city", required=false) String city) {
        String airportsJson = "[";
        try {
            if (iataCode == null && city == null) {
                for (Airport airport : repository.findAll()) {
                    airportsJson += String.format("%s, ", airport.toString());
                }
            } else if (iataCode != null) {
                airportsJson += String.format("%s, ", repository.findByIataCode(iataCode).toString());
            } else {
                airportsJson += String.format("%s, ", repository.findByCity(city).toString());
            }
            airportsJson = airportsJson.substring(0, airportsJson.length() - 2);
        } catch (Exception e) {

        }

        airportsJson += "]";
        return airportsJson;
    }
}
