package com.example.workshopsystem.service;

import com.example.workshopsystem.dto.WorkshopRequest;
import com.example.workshopsystem.dto.WorkshopResponse;
import com.example.workshopsystem.entity.Workshop;
import com.example.workshopsystem.exception.ResourceNotFoundException;
import com.example.workshopsystem.repository.WorkshopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkshopService {

    private final WorkshopRepository workshopRepository;

    public WorkshopService(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    public WorkshopResponse createWorkshop(WorkshopRequest request) {
        Workshop workshop = new Workshop();
        workshop.setTitle(request.getTitle());
        workshop.setDescription(request.getDescription());
        workshop.setLocation(request.getLocation());
        workshop.setDate(request.getDate());
        workshop.setCapacity(request.getCapacity());

        Workshop savedWorkshop = workshopRepository.save(workshop);
        return mapToResponse(savedWorkshop);
    }

    public List<WorkshopResponse> getAllWorkshops() {
        return workshopRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public WorkshopResponse getWorkshopById(Long id) {
        Workshop workshop = workshopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workshop not found with id: " + id));
        return mapToResponse(workshop);
    }

    public Workshop getWorkshopEntityById(Long id) {
        return workshopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workshop not found with id: " + id));
    }

    private WorkshopResponse mapToResponse(Workshop workshop) {
        return new WorkshopResponse(
                workshop.getId(),
                workshop.getTitle(),
                workshop.getDescription(),
                workshop.getLocation(),
                workshop.getDate(),
                workshop.getCapacity()
        );
    }
}