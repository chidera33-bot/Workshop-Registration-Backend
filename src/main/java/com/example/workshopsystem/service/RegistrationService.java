package com.example.workshopsystem.service;

import com.example.workshopsystem.dto.RegistrationRequest;
import com.example.workshopsystem.dto.RegistrationResponse;
import com.example.workshopsystem.entity.Registration;
import com.example.workshopsystem.entity.RegistrationStatus;
import com.example.workshopsystem.entity.User;
import com.example.workshopsystem.entity.Workshop;
import com.example.workshopsystem.exception.*;
import com.example.workshopsystem.repository.RegistrationRepository;
import com.example.workshopsystem.repository.WorkshopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final WorkshopRepository workshopRepository;
    private final UserService userService;
    private final WorkshopService workshopService;

    public RegistrationService(RegistrationRepository registrationRepository,
                               WorkshopRepository workshopRepository,
                               UserService userService,
                               WorkshopService workshopService) {
        this.registrationRepository = registrationRepository;
        this.workshopRepository = workshopRepository;
        this.userService = userService;
        this.workshopService = workshopService;
    }

    @Transactional
    public RegistrationResponse registerUser(RegistrationRequest request) {
        User user = userService.getUserEntityById(request.getUserId());
        Workshop workshop = workshopService.getWorkshopEntityById(request.getWorkshopId());

        // Rule C: cannot register for past workshops
        if (workshop.getStartDatetime().isBefore(LocalDateTime.now())) {
            throw new PastWorkshopException("Cannot register for a workshop that has already started or passed");
        }

        // Rule B: no duplicate registration
        if (registrationRepository.existsByUserIdAndWorkshopId(user.getId(), workshop.getId())) {
            throw new DuplicateRegistrationException("User is already registered for this workshop");
        }

        // Rule A: seat limit
        if (workshop.getSeatsRemaining() <= 0) {
            throw new WorkshopFullException("Workshop is already full");
        }

        Registration registration = new Registration();
        registration.setUser(user);
        registration.setWorkshop(workshop);
        registration.setCreatedAt(LocalDateTime.now());
        registration.setStatus(RegistrationStatus.ACTIVE);

        workshop.setSeatsRemaining(workshop.getSeatsRemaining() - 1);
        workshopRepository.save(workshop);

        return mapToResponse(registrationRepository.save(registration));
    }

    @Transactional
    public RegistrationResponse cancelRegistration(Long registrationId, Long userId) {
        Registration registration = registrationRepository.findByIdAndUserId(registrationId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found"));

        if (registration.getStatus() == RegistrationStatus.CANCELLED) {
            throw new CannotCancelException("Registration is already cancelled");
        }

        Workshop workshop = registration.getWorkshop();
        // Rule D: cannot cancel after workshop has started
        if (workshop.getStartDatetime().isBefore(LocalDateTime.now())) {
            throw new CannotCancelException("Cannot cancel registration after the workshop has started");
        }

        registration.setStatus(RegistrationStatus.CANCELLED);
        registration.setCancelledAt(LocalDateTime.now());

        workshop.setSeatsRemaining(workshop.getSeatsRemaining() + 1);
        workshopRepository.save(workshop);

        return mapToResponse(registrationRepository.save(registration));
    }

    public List<RegistrationResponse> getAllRegistrations() {
        return registrationRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public List<RegistrationResponse> getMyRegistrations(Long userId) {
        return registrationRepository.findByUserId(userId).stream().map(this::mapToResponse).toList();
    }

    public List<RegistrationResponse> getRegistrationsByWorkshop(Long workshopId) {
        return registrationRepository.findAll().stream()
                .filter(r -> r.getWorkshop().getId().equals(workshopId))
                .map(this::mapToResponse).toList();
    }

    private RegistrationResponse mapToResponse(Registration r) {
        return new RegistrationResponse(
                r.getId(),
                r.getUser().getId(),
                r.getUser().getName(),
                r.getUser().getEmail(),
                r.getWorkshop().getId(),
                r.getWorkshop().getTitle(),
                r.getCreatedAt(),
                r.getStatus()
        );
    }
}