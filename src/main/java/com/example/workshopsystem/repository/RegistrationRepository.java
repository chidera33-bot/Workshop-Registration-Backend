package com.example.workshopsystem.repository;

import com.example.workshopsystem.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByUserIdAndWorkshopId(Long userId, Long workshopId);
    long countByWorkshopId(Long workshopId);
    List<Registration> findByUserId(Long userId);
    Optional<Registration> findByIdAndUserId(Long id, Long userId);
}