package application.controllers;

import entities.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repositories.AirportRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@RestController
@RequestMapping("/getAirport")
public class AirportRestController {

    @Autowired
    private AirportRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String getAirport (@RequestParam(value="iata", required=false) String iataCode,
                                            @RequestParam(value="city", required=false) String city) {
        StringBuilder result = new StringBuilder("[");
        try {
            if (iataCode == null && city == null) {
                ArrayList<Airport> airports = (ArrayList<Airport>) repository.findAll();
                this.sortByIata(airports);
                for (Airport airport : airports) {
                    result.append(String.format("%s, ", airport.toString()));
                }
            } else if (iataCode != null) {
                result.append(String.format("%s, ", repository.findByIataCode(iataCode).toString()));
            } else {
                for (Airport airport : repository.findByCity(city)) {
                    result.append(String.format("%s, ", airport.toString()));
                }
            }
            result = new StringBuilder(result.substring(0, result.length() - 2));
        } catch (Exception e) {

        }

        result.append("]");
        return result.toString();
    }

    private void sortByIata(ArrayList<Airport> airports) {
        Collections.sort(airports, new Comparator<Airport>() {
            @Override
            public int compare(Airport a1, Airport a2) {
                String iataCode1 = a1.getIataCode();
                String iataCode2 = a2.getIataCode();
                return iataCode1.compareTo(iataCode2);
            }
        });
    }
}
