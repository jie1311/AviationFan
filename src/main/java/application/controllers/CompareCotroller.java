package application.controllers;


import application.Calculator;
import entities.Aircraft;
import entities.Airport;
import application.formValidators.CompareForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import repositories.AircraftRepository;
import repositories.AirportRepository;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class CompareCotroller {
    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirportRepository airportRepository;

    @GetMapping("/compare")
    public String compare(CompareForm compareForm, Model model) throws Exception {
        initialPage(model);
        return "compare";
    }

    @PostMapping("/compare")
    public String compareRange(@Valid CompareForm compareForm, BindingResult bindingResult , Model model){
        Aircraft aircraft = aircraftRepository.findById(compareForm.getAircraftId());
        Airport org = airportRepository.findById(compareForm.getOrgAirportId());
        Airport des = airportRepository.findById(compareForm.getDesAirportId());
        String routeString = "";
        if (Calculator.reachable(org, des, aircraft)) {
            routeString = String.format("Yes. %s-%s is available.",org.getIataCode(), des.getIataCode());
            model.addAttribute("reachable", routeString);
        } else {
            ArrayList<Airport> route = Calculator.lessReroute(org, des, aircraft.getRange(), airportRepository);
            for (int i = 1; i <= route.size() - 1; i++) {
                Airport via = route.get(i);
                routeString += String.format("-%s", via.getIataCode());
            }
            if (route.isEmpty()){
                routeString = "No.";
            } else {
                routeString = String.format("No. However, %s%s is available.",org.getIataCode(), routeString);
            }
            model.addAttribute("reachable", routeString);
        }
        initialPage(model);
        return "compare";
    }

    private void initialPage(Model model) {
        ArrayList aircrafts = new ArrayList<>();
        for (Aircraft aircraft : aircraftRepository.findAll()) {
            aircrafts.add(aircraft);
        }
        model.addAttribute("aircrafts", aircrafts);

        ArrayList airports = new ArrayList<>();
        for (Airport airport : airportRepository.findAll()) {
            airports.add(airport);
        }
        model.addAttribute("airports", airports);
    }
}
