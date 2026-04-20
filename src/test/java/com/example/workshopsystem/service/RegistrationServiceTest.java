package com.example.workshopsystem.service;

import com.example.workshopsystem.dto.RegistrationRequest;
import com.example.workshopsystem.entity.*;
import com.example.workshopsystem.exception.DuplicateRegistrationException;
import com.example.workshopsystem.exception.PastWorkshopException;
import com.example.workshopsystem.exception.WorkshopFullException;
import com.example.workshopsystem.repository.RegistrationRepository;
import com.example.workshopsystem.repository.WorkshopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private WorkshopRepository workshopRepository;

    @Mock
    private UserService userService;

    @Mock
    private WorkshopService workshopService;

    @InjectMocks
    private RegistrationService registrationService;

    private User testUser;
    private Workshop testWorkshop;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@test.com");

        testWorkshop = new Workshop();
        testWorkshop.setId(1L);
        testWorkshop.setTitle("Test Workshop");
        testWorkshop.setSeatsRemaining(10);
        testWorkshop.setTotalSeats(10);
        testWorkshop.setStatus(WorkshopStatus.ACTIVE);
        testWorkshop.setStartDatetime(LocalDateTime.now().plusDays(7));
    }

    @Test
    void shouldThrowExceptionWhenDuplicateRegistration() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUserId(1L);
        request.setWorkshopId(1L);

        when(userService.getUserEntityById(1L)).thenReturn(testUser);
        when(workshopService.getWorkshopEntityById(1L)).thenReturn(testWorkshop);
        when(registrationRepository.existsByUserIdAndWorkshopId(1L, 1L)).thenReturn(true);

        assertThrows(DuplicateRegistrationException.class, () -> registrationService.registerUser(request));
    }

    @Test
    void shouldThrowExceptionWhenWorkshopIsFull() {
        testWorkshop.setSeatsRemaining(0);

        RegistrationRequest request = new RegistrationRequest();
        request.setUserId(1L);
        request.setWorkshopId(1L);

        when(userService.getUserEntityById(1L)).thenReturn(testUser);
        when(workshopService.getWorkshopEntityById(1L)).thenReturn(testWorkshop);
        when(registrationRepository.existsByUserIdAndWorkshopId(1L, 1L)).thenReturn(false);

        assertThrows(WorkshopFullException.class, () -> registrationService.registerUser(request));
    }

    @Test
    void shouldThrowExceptionWhenWorkshopIsInThePast() {
        testWorkshop.setStartDatetime(LocalDateTime.now().minusDays(1));

        RegistrationRequest request = new RegistrationRequest();
        request.setUserId(1L);
        request.setWorkshopId(1L);

        when(userService.getUserEntityById(1L)).thenReturn(testUser);
        when(workshopService.getWorkshopEntityById(1L)).thenReturn(testWorkshop);

        assertThrows(PastWorkshopException.class, () -> registrationService.registerUser(request));
    }
}

