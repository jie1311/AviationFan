package application.controllers;

import application.formValidators.EditAirportForm;
import entities.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import repositories.AirportRepository;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class EditAirportController {

    @Autowired
    private AirportRepository airportRepository;

    @GetMapping("/editAirport")
    public String showAirports(Model model) {
        initalPage(model);
        return "editAirport";
    }

    @PostMapping("/editAirport")
    public String editAirports(@Valid EditAirportForm editAirportForm, BindingResult bindingResult, Model model) {
        if (editAirportForm.getDeletedId() == null && editAirportForm.getEditId() == null) {
            //Add
            try {
                airportRepository.findByIataCode(editAirportForm.getIataCode()).getCity();
                model.addAttribute("added", "Airport already exists.");
            } catch (Exception e2) {
                try {
                    double[] newCoord = {Double.valueOf(editAirportForm.getLongitude().trim()), Double.valueOf(editAirportForm.getLatitude().trim())};
                    airportRepository.save(new Airport(editAirportForm.getIataCode().trim(), editAirportForm.getCity().trim(), newCoord));
                    model.addAttribute("added", "Airport added.");
                } catch (Exception e1) {
                    model.addAttribute("added", "Plz enter long and lat as double.");
                }
            }
        } else if (editAirportForm.getDeletedId() != null && editAirportForm.getEditId() == null) {
            //Delete
            try{
                airportRepository.findById(editAirportForm.getDeletedId());
                airportRepository.delete(editAirportForm.getDeletedId());
                model.addAttribute("deleted", "Airport deleted.");
            } catch (Exception e) {
                model.addAttribute("deleted", "Airport not exist in the database. Please refresh the page.");
            }
        } else {
            //Edit
            try{
                airportRepository.findById(editAirportForm.getEditId());
                airportRepository.delete(editAirportForm.getEditId());
                model.addAttribute("edited", "Airport edited.");
                try {
                    double[] newCoord = {Double.valueOf(editAirportForm.getLongitude().trim()), Double.valueOf(editAirportForm.getLatitude().trim())};
                    airportRepository.save(new Airport(editAirportForm.getIataCode().trim(), editAirportForm.getCity().trim(), newCoord));
                } catch (Exception e1) {
                    model.addAttribute("edited", "Plz enter long and lat as double.");
                }
            } catch (Exception e) {
                model.addAttribute("edited", "Airport not exist in the database. Please refresh the page.");
            }
        }

        initalPage(model);
        return "editAirport";
    }

    private void initalPage(Model model) {
        ArrayList airports = new ArrayList<>();
        airports.addAll(airportRepository.findAll());

        model.addAttribute("airports", airports);
        model.addAttribute("editAirportForm", new EditAirportForm());
    }
}
