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
import java.util.List;

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
            List searched = new ArrayList<>();
            searched.add(org);
            List<Airport> route = searchResult(org, des, aircraft, searched, new ArrayList<>());
            while (!(route.isEmpty() || Calculator.reachable(route.get(route.size() - 1), org, aircraft))) {
                searched.clear();
                searched.add(org);
                route.addAll(searchResult(org, route.get(route.size() - 1), aircraft, searched, new ArrayList<>()));
            }
            for (int i = route.size() - 1; i >= 0; i--) {
                Airport via = route.get(i);
                routeString += String.format("-%s", via.getIataCode());
            }
            if (route.isEmpty()){
                routeString = "No.";
            } else {
                routeString = String.format("No. However, %s%s-%s is available.",org.getIataCode(), routeString, des.getIataCode());
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

    private List reachableAirports(Airport org, Aircraft aircraft) {
        List airports = new ArrayList<>();
        for (Airport des : airportRepository.findAll()) {
            if (Calculator.reachable(org, des, aircraft) && !org.getId().equals(des.getId())) {
                airports.add(des);
            }
        }
        return airports;
    }

    private List<Airport> searchResult(Airport org, Airport des, Aircraft aircraft, List searched, List result) {
        List<Airport> available = reachableAirports(org, aircraft);
        boolean found = false;
        for (Airport via : available) {
            if (via.getId().equals(des.getId())) {
                found = true;
                result.add(org);
                break;
            }
        }
        if (!found) {
            out: for (Airport via : available) {
                boolean origin = false;
                in: for (Airport srd : (ArrayList<Airport>) searched) {
                    if (srd.getId().equals(via.getId())) {
                        origin = true;
                        break in;
                    }
                }
                if (!origin) {
                    searched.add(via);
                    searchResult(via, des, aircraft, searched, result);
                    break out;
                }
            }
        }
        return result;
    }
}
