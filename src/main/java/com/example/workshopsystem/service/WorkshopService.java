package com.example.workshopsystem.service;

import com.example.workshopsystem.dto.WorkshopRequest;
import com.example.workshopsystem.dto.WorkshopResponse;
import com.example.workshopsystem.entity.Workshop;
import com.example.workshopsystem.entity.WorkshopStatus;
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
        workshop.setStartDatetime(request.getStartDatetime());
        workshop.setTotalSeats(request.getTotalSeats());
        workshop.setSeatsRemaining(request.getTotalSeats());
        workshop.setStatus(WorkshopStatus.ACTIVE);

        return mapToResponse(workshopRepository.save(workshop));
    }

    public WorkshopResponse updateWorkshop(Long id, WorkshopRequest request) {
        Workshop workshop = getWorkshopEntityById(id);
        workshop.setTitle(request.getTitle());
        workshop.setDescription(request.getDescription());
        workshop.setLocation(request.getLocation());
        workshop.setStartDatetime(request.getStartDatetime());
        workshop.setTotalSeats(request.getTotalSeats());

        return mapToResponse(workshopRepository.save(workshop));
    }

    public WorkshopResponse cancelWorkshop(Long id) {
        Workshop workshop = getWorkshopEntityById(id);
        workshop.setStatus(WorkshopStatus.CANCELLED);
        return mapToResponse(workshopRepository.save(workshop));
    }

    public List<WorkshopResponse> getAllWorkshops() {
        return workshopRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public WorkshopResponse getWorkshopById(Long id) {
        return mapToResponse(getWorkshopEntityById(id));
    }

    public Workshop getWorkshopEntityById(Long id) {
        return workshopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workshop not found with id: " + id));
    }

    private WorkshopResponse mapToResponse(Workshop w) {
        return new WorkshopResponse(w.getId(), w.getTitle(), w.getDescription(),
                w.getLocation(), w.getStartDatetime(), w.getTotalSeats(),
                w.getSeatsRemaining(), w.getStatus());
    }
}