package application.controllers;

import application.JsonBuilder;
import entities.Aircraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repositories.AircraftRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@RestController
@RequestMapping("/getAircraft")
public class AircraftRestController {

    @Autowired
    private AircraftRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String getAircraft(@RequestParam(value="manufacturer", required=false) String manufacturer,
                                            @RequestParam(value="model", required=false) String model,
                                            @RequestParam(value="submodel", required=false) String submodel) {
        ArrayList<Aircraft> aircrafts = new ArrayList<>();
        String jsonString;
        try {
            if (manufacturer == null) {
                aircrafts.addAll(repository.findAll());
            } else if (model == null) {
                aircrafts.addAll(repository.findByManufacturer(manufacturer));
            } else if (submodel == null) {
                aircrafts.addAll(repository.findByManufacturerAndModel(manufacturer, model));
            } else {
                aircrafts.addAll(repository.findByManufacturerAndModelAndSubModel(manufacturer, model, submodel));
            }

            this.sortTypes(aircrafts);
            jsonString = JsonBuilder.buildAircraftsJsonArray(aircrafts);
        } catch (Exception e) {
            jsonString = "[]";
        }

        return jsonString;
    }

    private void sortTypes (ArrayList<Aircraft> aircrafts) {
        Collections.sort(aircrafts, new Comparator<Aircraft>() {
            @Override
            public int compare(Aircraft o1, Aircraft o2) {
                String m1 = o1.getModel();
                String m2 = o2.getModel();
                return m1.compareTo(m2);
            }
        });

        Collections.sort(aircrafts, new Comparator<Aircraft>() {
            @Override
            public int compare(Aircraft o1, Aircraft o2) {
                String m1 = o1.getManufacturer();
                String m2 = o2.getManufacturer();
                return m1.compareTo(m2);
            }
        });
    }
}
