package com.example.workshopsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workshops")
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(name = "start_datetime", nullable = false)
    private LocalDateTime startDatetime;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer seatsRemaining;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkshopStatus status = WorkshopStatus.ACTIVE;

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Registration> registrations = new ArrayList<>();

    public Workshop() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public Integer getSeatsRemaining() {
        return seatsRemaining;
    }

    public WorkshopStatus getStatus() {
        return status;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public void setSeatsRemaining(Integer seatsRemaining) {
        this.seatsRemaining = seatsRemaining;
    }

    public void setStatus(WorkshopStatus status) {
        this.status = status;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }
}