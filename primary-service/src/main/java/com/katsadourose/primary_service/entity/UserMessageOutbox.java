package com.katsadourose.primary_service.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MessageOutbox", schema = "dbo")
public class UserMessageOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "EventType")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "Payload")
    private String payload;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "IsPublished")
    private Boolean isPublished;

    public UserMessageOutbox() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    @Override
    public String toString() {
        return "UserMessageOutbox{" +
                "id=" + id +
                ", eventType=" + eventType +
                ", payload='" + payload + '\'' +
                ", createdAt=" + createdAt +
                ", isPublished=" + isPublished +
                '}';
    }
}
