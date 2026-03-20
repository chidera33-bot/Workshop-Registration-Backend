package com.example.workshopsystem.dto;

import java.time.LocalDateTime;

public class RegistrationResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long workshopId;
    private String workshopTitle;
    private LocalDateTime registrationDate;

    public RegistrationResponse() {
    }

    public RegistrationResponse(Long id, Long userId, String userName, Long workshopId, String workshopTitle, LocalDateTime registrationDate) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.workshopId = workshopId;
        this.workshopTitle = workshopTitle;
        this.registrationDate = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getWorkshopId() {
        return workshopId;
    }

    public String getWorkshopTitle() {
        return workshopTitle;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setWorkshopId(Long workshopId) {
        this.workshopId = workshopId;
    }

    public void setWorkshopTitle(String workshopTitle) {
        this.workshopTitle = workshopTitle;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}