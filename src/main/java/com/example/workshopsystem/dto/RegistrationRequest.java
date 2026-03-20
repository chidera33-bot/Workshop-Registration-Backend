package com.example.workshopsystem.dto;

import jakarta.validation.constraints.NotNull;

public class RegistrationRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Workshop ID is required")
    private Long workshopId;

    public RegistrationRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public Long getWorkshopId() {
        return workshopId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setWorkshopId(Long workshopId) {
        this.workshopId = workshopId;
    }
}