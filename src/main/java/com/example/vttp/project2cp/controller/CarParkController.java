package com.example.vttp.project2cp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CarParkController {
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    // @PreAuthorize
    public String getEmployees() {
        return "Favorites!";
    }
}
