package com.maestro.examples.app.azure.filestorage.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping(value = "/")
    public String welcome () {
        return "Welcome from Spring Boot app";
    }
}
