package com.example.workshopsystem.dto;

import com.example.workshopsystem.entity.WorkshopStatus;
import java.time.LocalDateTime;

public class WorkshopResponse {

    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime startDatetime;
    private Integer totalSeats;
    private Integer seatsRemaining;
    private WorkshopStatus status;

    public WorkshopResponse() {}

    public WorkshopResponse(Long id, String title, String description, String location,
                            LocalDateTime startDatetime, Integer totalSeats,
                            Integer seatsRemaining, WorkshopStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.startDatetime = startDatetime;
        this.totalSeats = totalSeats;
        this.seatsRemaining = seatsRemaining;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public LocalDateTime getStartDatetime() { return startDatetime; }
    public Integer getTotalSeats() { return totalSeats; }
    public Integer getSeatsRemaining() { return seatsRemaining; }
    public WorkshopStatus getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setStartDatetime(LocalDateTime startDatetime) { this.startDatetime = startDatetime; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
    public void setSeatsRemaining(Integer seatsRemaining) { this.seatsRemaining = seatsRemaining; }
    public void setStatus(WorkshopStatus status) { this.status = status; }
}