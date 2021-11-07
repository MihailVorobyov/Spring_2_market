package com.geekbrains.geekmarketwinter.controllers;

import com.geekbrains.geekmarketwinter.entities.SystemUser;
import com.geekbrains.geekmarketwinter.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.geekbrains.geekmarketwinter.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {
        theModel.addAttribute("systemUser", new SystemUser());
        return "registration-form";
    }

    // Binding Result после @ValidModel !!!
    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("systemUser") SystemUser SystemUser, BindingResult theBindingResult, Model model) {
        String userName = SystemUser.getUserName();
        logger.debug("Processing registration form for: " + userName);
        if (theBindingResult.hasErrors()) {
            return "registration-form";
        }
        User existing = userService.findByUserName(userName);
        if (existing != null) {
            // SystemUser.setUserName(null);
            model.addAttribute("systemUser", SystemUser);
            model.addAttribute("registrationError", "User with current username already exists");
            logger.debug("User name already exists.");
            return "registration-form";
        }
        userService.save(SystemUser);
        logger.debug("Successfully created user: " + userName);
        return "registration-confirmation";
    }
}
