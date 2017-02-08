package application.controllers;

import entities.Airport;
import application.formValidators.AirportForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import repositories.AirportRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;

@Controller
public class AirportController {

    @Autowired
    private AirportRepository repository;

    @GetMapping("/airport")
    public String aircraft(Model model) {
        initialPage(model);
        return "airport";
    }

    @PostMapping("/airport")
    public String addAirport(@Valid AirportForm airportForm, BindingResult bindingResult, Model model) {
        try {
            repository.findByIataCode(airportForm.getIataCode()).getId();
            model.addAttribute("added", "Airport already exists.");
        } catch (Exception e1) {
            try {
                double[] newCoord = {Double.valueOf(airportForm.getLongitude().trim()), Double.valueOf(airportForm.getLatitude().trim())};
                repository.save(new Airport(airportForm.getIataCode().trim(), airportForm.getCity().trim(), newCoord));
                model.addAttribute("added", "Airport added.");
            } catch (Exception e2) {
                model.addAttribute("added", "Plz enter long and lat.");
            }
        }
        initialPage(model);
        return "airport";
    }

    private void initialPage(Model model) {
        ArrayList airs = new ArrayList<>();
        for (Airport airport : repository.findAll()){
            airs.add(airport.getAirport());
        }
        Collections.sort(airs);
        model.addAttribute("airs", airs);
        model.addAttribute("airportForm",new AirportForm());
    }
}
