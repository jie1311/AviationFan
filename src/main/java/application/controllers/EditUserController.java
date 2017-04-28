package application.controllers;
//todo: List iteration

import application.Calculator;
import application.formValidators.EditAirportForm;
import application.formValidators.EditUserForm;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import repositories.UserRepository;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class EditUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/editUser")
    public String showUsers(Model model) {
        initialPage(model);
        return "editUser";
    }

    @PostMapping("/editUser")
    public String editUsers(@Valid EditUserForm editUserForm, BindingResult bindingResult, Model model) {
        if (editUserForm.getDeletedId() == null) {
            if (editUserForm.getPassword().equals(editUserForm.getPassword2())) {
                String psd = Calculator.base64Encode(editUserForm.getPassword());
                userRepository.save(new User(editUserForm.getUsername(), psd));
                model.addAttribute("added", String.format("User: %s added.", editUserForm.getUsername()));
            } else {
                model.addAttribute("added", "Password and Confirmed password must be the same.");
            }
        } else {
            try {
                userRepository.findById(editUserForm.getDeletedId());
                userRepository.delete(editUserForm.getDeletedId());
                model.addAttribute("deleted", "User deleted.");
            } catch (Exception e) {
                model.addAttribute("deleted", "User does not exist.");
            }
        }
        initialPage(model);
        return "editUser";
    }

    private void initialPage(Model model) {
        ArrayList users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            users.add(user);
        }

        model.addAttribute("users", users);
        model.addAttribute("editUserForm", new EditUserForm());
    }
}
