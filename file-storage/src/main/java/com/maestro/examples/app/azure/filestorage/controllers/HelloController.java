package com.maestro.examples.app.azure.filestorage.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping(value = "/welcome")
    public String welcome () {
        return "Welcome to Spring Boot";
    }
}
