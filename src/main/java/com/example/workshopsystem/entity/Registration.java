package com.example.workshopsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "registrations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "workshop_id"})
)
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workshop_id", nullable = false)
    private Workshop workshop;

    public Registration() {
    }

    public Registration(LocalDateTime registrationDate, User user, Workshop workshop) {
        this.registrationDate = registrationDate;
        this.user = user;
        this.workshop = workshop;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public User getUser() {
        return user;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }
}