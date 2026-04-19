package com.example.workshopsystem.controller;

import com.example.workshopsystem.dto.RegistrationRequest;
import com.example.workshopsystem.dto.RegistrationResponse;
import com.example.workshopsystem.entity.User;
import com.example.workshopsystem.service.RegistrationService;
import com.example.workshopsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UserService userService;

    public RegistrationController(RegistrationService registrationService, UserService userService) {
        this.registrationService = registrationService;
        this.userService = userService;
    }

    @PostMapping("/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse registerUser(@Valid @RequestBody RegistrationRequest request) {
        return registrationService.registerUser(request);
    }

    @DeleteMapping("/registrations/{id}")
    public RegistrationResponse cancelRegistration(@PathVariable Long id,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserEntityByEmail(userDetails.getUsername());
        return registrationService.cancelRegistration(id, user.getId());
    }

    @GetMapping("/me/registrations")
    public List<RegistrationResponse> getMyRegistrations(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserEntityByEmail(userDetails.getUsername());
        return registrationService.getMyRegistrations(user.getId());
    }

    @GetMapping("/registrations")
    public List<RegistrationResponse> getAllRegistrations() {
        return registrationService.getAllRegistrations();
    }
}