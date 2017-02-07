package application;

import entities.Aircraft;
import formValidators.AircraftForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repositories.AircraftRepository;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class AircraftController {

    @Autowired
    private AircraftRepository repository;

    @GetMapping("/aircraft")
    public String aircraft(@RequestParam(value="manufacturer", required=false, defaultValue="all") String manufacturer, Model model) {
        initialPage(manufacturer, model);
        return "aircraft";
    }

    @PostMapping("/aircraft")
    public String newAircraft(@RequestParam(value="manufacturer", required=false, defaultValue="all") String manufacturer,
                              @Valid AircraftForm aircraftForm, BindingResult bindingResult, Model model) {
        if (repository.findByManufacturerAndModelAndSubModel(
                aircraftForm.getManufacturer(),
                aircraftForm.getModel(),
                aircraftForm.getSubModel()).isEmpty()) {

            repository.save(new Aircraft(aircraftForm.getManufacturer().trim(),
                                         aircraftForm.getModel().trim(),
                                         aircraftForm.getSubModel().trim(),
                                         Integer.valueOf(aircraftForm.getRange()),
                                         Integer.valueOf(aircraftForm.getCapacity())));
            model.addAttribute("added", "Aircraft added.");
        } else {
            model.addAttribute("added", "Aircraft not added.");
        }
        initialPage(manufacturer, model);
        return "aircraft";
    }

    private void initialPage(String manufacturer, Model model) {
        ArrayList airs = new ArrayList<>();
        if (manufacturer.equals("all")) {
            for (Aircraft aircraft : repository.findAll()) {
                airs.add(aircraft);
            }
        } else {
            for (Aircraft aircraft : repository.findByManufacturer(manufacturer)) {
                airs.add(aircraft);
            }
        }

        model.addAttribute("airs", airs);
        model.addAttribute("aircraftForm", new AircraftForm());
    }
}
