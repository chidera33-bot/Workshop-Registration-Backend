package com.example.workshopsystem.service;

import com.example.workshopsystem.dto.RegistrationRequest;
import com.example.workshopsystem.dto.RegistrationResponse;
import com.example.workshopsystem.entity.Registration;
import com.example.workshopsystem.entity.User;
import com.example.workshopsystem.entity.Workshop;
import com.example.workshopsystem.exception.DuplicateRegistrationException;
import com.example.workshopsystem.exception.WorkshopFullException;
import com.example.workshopsystem.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UserService userService;
    private final WorkshopService workshopService;

    public RegistrationService(RegistrationRepository registrationRepository,
                               UserService userService,
                               WorkshopService workshopService) {
        this.registrationRepository = registrationRepository;
        this.userService = userService;
        this.workshopService = workshopService;
    }

    public RegistrationResponse registerUser(RegistrationRequest request) {
        User user = userService.getUserEntityById(request.getUserId());
        Workshop workshop = workshopService.getWorkshopEntityById(request.getWorkshopId());

        boolean alreadyRegistered = registrationRepository
                .existsByUserIdAndWorkshopId(user.getId(), workshop.getId());

        if (alreadyRegistered) {
            throw new DuplicateRegistrationException("User is already registered for this workshop");
        }

        long currentRegistrations = registrationRepository.countByWorkshopId(workshop.getId());
        if (currentRegistrations >= workshop.getCapacity()) {
            throw new WorkshopFullException("Workshop is already full");
        }

        Registration registration = new Registration();
        registration.setUser(user);
        registration.setWorkshop(workshop);
        registration.setRegistrationDate(LocalDateTime.now());

        Registration savedRegistration = registrationRepository.save(registration);
        return mapToResponse(savedRegistration);
    }

    public List<RegistrationResponse> getAllRegistrations() {
        return registrationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private RegistrationResponse mapToResponse(Registration registration) {
        return new RegistrationResponse(
                registration.getId(),
                registration.getUser().getId(),
                registration.getUser().getName(),
                registration.getWorkshop().getId(),
                registration.getWorkshop().getTitle(),
                registration.getRegistrationDate()
        );
    }
}