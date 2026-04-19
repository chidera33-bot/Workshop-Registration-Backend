package com.example.workshopsystem.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class WorkshopRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Start date/time is required")
    @Future(message = "Start date/time must be in the future")
    private LocalDateTime startDatetime;

    @NotNull(message = "Total seats is required")
    @Min(value = 1, message = "Total seats must be at least 1")
    private Integer totalSeats;

    public WorkshopRequest() {}

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public LocalDateTime getStartDatetime() { return startDatetime; }
    public Integer getTotalSeats() { return totalSeats; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setStartDatetime(LocalDateTime startDatetime) { this.startDatetime = startDatetime; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
}