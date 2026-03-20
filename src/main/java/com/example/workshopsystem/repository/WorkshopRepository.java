package com.example.workshopsystem.repository;

import com.example.workshopsystem.entity.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
}