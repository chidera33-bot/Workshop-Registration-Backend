package com.example.workshopsystem.controller;

import com.example.workshopsystem.dto.RegistrationRequest;
import com.example.workshopsystem.dto.RegistrationResponse;
import com.example.workshopsystem.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse registerUser(@Valid @RequestBody RegistrationRequest request) {
        return registrationService.registerUser(request);
    }

    @GetMapping
    public List<RegistrationResponse> getAllRegistrations() {
        return registrationService.getAllRegistrations();
    }
}