package application.controllers;


import entities.Aircraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repositories.AircraftRepository;

@RestController
@RequestMapping("/getAircraft")
public class AircraftRestController {

    @Autowired
    private AircraftRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String getAircraft(@RequestParam(value="manufacturer", required=false) String manufacturer,
                                            @RequestParam(value="model", required=false) String model,
                                            @RequestParam(value="submodel", required=false) String submodel) {
        String aircraftsJson = "[";
        try {
            if (manufacturer == null) {
                for (Aircraft aircraft : repository.findAll()) {
                    aircraftsJson += String.format("%s, ", aircraft.toString());
                }

            } else if (model == null) {
                for (Aircraft aircraft : repository.findByManufacturer(manufacturer)) {
                    aircraftsJson += String.format("%s, ", aircraft.toString());
                }
            } else if (submodel == null) {
                for (Aircraft aircraft : repository.findByManufacturerAndModel(manufacturer, model)) {
                    aircraftsJson += String.format("%s, ", aircraft.toString());
                }
            } else {
                aircraftsJson += String.format("%s, ",
                        repository.findByManufacturerAndModelAndSubModel(manufacturer, model, submodel).get(0).toString());
            }
            aircraftsJson = aircraftsJson.substring(0, aircraftsJson.length() - 2);
        } catch (Exception e) {

        }

        aircraftsJson += "]";
        return aircraftsJson;
    }
}
