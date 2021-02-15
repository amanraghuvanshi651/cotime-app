package com.example.cotime_app.ModelClasses;

public class Routines {
    String title;
    String description;
    String countDown;
    String createdAt;
    String color;

    public Routines() {
    }

    public Routines(String title, String description, String countDown, String createdAt, String color) {
        this.title = title;
        this.description = description;
        this.countDown = countDown;
        this.createdAt = createdAt;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountDown() {
        return countDown;
    }

    public void setCountDown(String countDown) {
        this.countDown = countDown;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
