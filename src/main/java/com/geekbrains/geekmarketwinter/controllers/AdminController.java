package com.geekbrains.geekmarketwinter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String showAdminDashboard() {
        return "admin-panel";
    }
    
}
