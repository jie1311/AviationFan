package application.controllers;

import entities.Aircraft;
import application.formValidators.EditAircraftForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import repositories.AircraftRepository;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class EditAircraftController {

    @Autowired
    private AircraftRepository repository;

    @GetMapping("/editAircraft")
    public String showAircraft(Model model) {
        initialPage(model);
        return "editAircraft";
    }

    @PostMapping("/editAircraft")
    public String editAircraft(@Valid EditAircraftForm editAircraftForm, BindingResult bindingResult, Model model) {
        if (editAircraftForm.getDeletedId() == null && editAircraftForm.getEditId() == null) {
            //Add
            if (repository.findByManufacturerAndModelAndSubModel(
                    editAircraftForm.getManufacturer(),
                    editAircraftForm.getModel(),
                    editAircraftForm.getSubModel()).isEmpty()) {
                repository.save(new Aircraft(editAircraftForm.getManufacturer().trim(),
                        editAircraftForm.getModel().trim(),
                        editAircraftForm.getSubModel().trim(),
                        Integer.valueOf(editAircraftForm.getRange()),
                        Integer.valueOf(editAircraftForm.getCapacity())));
                model.addAttribute("added", "Aircraft type added.");
            } else {
                model.addAttribute("added", "Aircraft type already exists.");
            }
        } else  if (editAircraftForm.getDeletedId() != null && editAircraftForm.getEditId() == null) {
            //Delete
            try {
                repository.findById(editAircraftForm.getDeletedId());
                repository.delete(editAircraftForm.getDeletedId());
                model.addAttribute("deleted", "Aircraft type deleted.");
            } catch (Exception e) {
                model.addAttribute("deleted", "Aircraft type not found in the database. Please refresh the page.");
            }
        } else {
            //Edit
            try {
                repository.findById(editAircraftForm.getEditId());
                repository.delete(editAircraftForm.getEditId());
                repository.save(new Aircraft(editAircraftForm.getManufacturer().trim(),
                        editAircraftForm.getModel().trim(),
                        editAircraftForm.getSubModel().trim(),
                        Integer.valueOf(editAircraftForm.getRange()),
                        Integer.valueOf(editAircraftForm.getCapacity())));
                model.addAttribute("edited", "Aircraft type edited.");
            } catch (Exception e) {
                model.addAttribute("edited", "Aircraft type not found in the database. Please refresh the page.");
            }
        }
        initialPage(model);
        return "editAircraft";
    }

    private void initialPage(Model model) {
        ArrayList airs = new ArrayList<>();
        for (Aircraft aircraft : repository.findAll()) {
            airs.add(aircraft);
        }

        model.addAttribute("airs", airs);
        model.addAttribute("editAircraftForm", new EditAircraftForm());
    }

}
