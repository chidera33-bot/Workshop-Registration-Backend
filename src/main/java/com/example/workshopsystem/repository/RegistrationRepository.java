package com.example.workshopsystem.repository;

import com.example.workshopsystem.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByUserIdAndWorkshopId(Long userId, Long workshopId);
    long countByWorkshopId(Long workshopId);
}