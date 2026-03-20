package com.example.workshopsystem.controller;

import com.example.workshopsystem.dto.WorkshopRequest;
import com.example.workshopsystem.dto.WorkshopResponse;
import com.example.workshopsystem.service.WorkshopService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workshops")
public class WorkshopController {

    private final WorkshopService workshopService;

    public WorkshopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkshopResponse createWorkshop(@Valid @RequestBody WorkshopRequest request) {
        return workshopService.createWorkshop(request);
    }

    @GetMapping
    public List<WorkshopResponse> getAllWorkshops() {
        return workshopService.getAllWorkshops();
    }

    @GetMapping("/{id}")
    public WorkshopResponse getWorkshopById(@PathVariable Long id) {
        return workshopService.getWorkshopById(id);
    }
}