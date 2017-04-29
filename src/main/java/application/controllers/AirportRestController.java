package application.controllers;

import application.JsonBuilder;
import application.Output;
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
        ArrayList<Airport> airports = new ArrayList<>();
        Output output = new Output();
        try {
            if (iataCode == null && city == null) {
                airports.addAll(repository.findAll());
            } else if (iataCode != null) {
                airports.add(repository.findByIataCode(iataCode));
            } else {
                airports.addAll(repository.findByCity(city));
            }
            this.sortByIata(airports);
            output.setSuccess(true);
            output.setData(JsonBuilder.buildAirportsJsonArray(airports));
        } catch (Exception e) {
            output.setSuccess(false);
            output.setMessage("Unexpected error happened.");
            output.setData("[]");
        }

        return output.toString();
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
