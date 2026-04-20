package com.example.workshopsystem.controller;

import com.example.workshopsystem.dto.WorkshopRequest;
import com.example.workshopsystem.dto.WorkshopResponse;
import com.example.workshopsystem.dto.RegistrationResponse;
import com.example.workshopsystem.service.WorkshopService;
import com.example.workshopsystem.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkshopController {

    private final WorkshopService workshopService;
    private final RegistrationService registrationService;

    public WorkshopController(WorkshopService workshopService, RegistrationService registrationService) {
        this.workshopService = workshopService;
        this.registrationService = registrationService;
    }

    // Public endpoints
    @GetMapping("/workshops")
    public List<WorkshopResponse> getAllWorkshops() {
        return workshopService.getAllWorkshops();
    }

    @GetMapping("/workshops/{id}")
    public WorkshopResponse getWorkshopById(@PathVariable Long id) {
        return workshopService.getWorkshopById(id);
    }

    // Admin endpoints
    @PostMapping("/admin/workshops")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkshopResponse createWorkshop(@Valid @RequestBody WorkshopRequest request) {
        return workshopService.createWorkshop(request);
    }

    @PutMapping("/admin/workshops/{id}")
    public WorkshopResponse updateWorkshop(@PathVariable Long id, @Valid @RequestBody WorkshopRequest request) {
        return workshopService.updateWorkshop(id, request);
    }

    @PatchMapping("/admin/workshops/{id}/cancel")
    public WorkshopResponse cancelWorkshop(@PathVariable Long id) {
        return workshopService.cancelWorkshop(id);
    }

    @GetMapping("/admin/workshops/{id}/registrations")
    public List<RegistrationResponse> getWorkshopRegistrations(@PathVariable Long id) {
        return registrationService.getRegistrationsByWorkshop(id);
    }
}