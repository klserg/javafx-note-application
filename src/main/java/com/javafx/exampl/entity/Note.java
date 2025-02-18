package com.javafx.exampl.entity;

import java.time.LocalDateTime;

public class Note {

    private Integer id;
    private String description;

    private LocalDateTime createdTime;

    public Note() {}

    public Note(Integer id, String description, LocalDateTime createdTime) {
        this.id = id;
        this.description = description;
        this.createdTime = createdTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
