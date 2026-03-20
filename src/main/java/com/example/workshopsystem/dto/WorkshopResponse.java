package com.example.workshopsystem.dto;

import java.time.LocalDate;

public class WorkshopResponse {

    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDate date;
    private Integer capacity;

    public WorkshopResponse() {
    }

    public WorkshopResponse(Long id, String title, String description, String location, LocalDate date, Integer capacity) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.capacity = capacity;
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

    public LocalDate getDate() {
        return date;
    }

    public Integer getCapacity() {
        return capacity;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}