package com.example.workshopsystem.dto;

import com.example.workshopsystem.entity.RegistrationStatus;
import java.time.LocalDateTime;

public class RegistrationResponse {

    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private Long workshopId;
    private String workshopTitle;
    private LocalDateTime createdAt;
    private RegistrationStatus status;

    public RegistrationResponse() {}

    public RegistrationResponse(Long id, Long userId, String userName, String userEmail, Long workshopId,
                                String workshopTitle, LocalDateTime createdAt, RegistrationStatus status) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.workshopId = workshopId;
        this.workshopTitle = workshopTitle;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public Long getWorkshopId() { return workshopId; }
    public String getWorkshopTitle() { return workshopTitle; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public RegistrationStatus getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public void setWorkshopId(Long workshopId) { this.workshopId = workshopId; }
    public void setWorkshopTitle(String workshopTitle) { this.workshopTitle = workshopTitle; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setStatus(RegistrationStatus status) { this.status = status; }
}

