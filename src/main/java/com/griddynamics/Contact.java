package com.griddynamics;


import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Contact implements Serializable {
    protected String name;
    protected String phoneNumber;
    private final LocalDateTime timeCreated;
    private LocalDateTime timeModified;

    public Contact() {
        this.timeModified = LocalDateTime.now();
        this.timeCreated = LocalDateTime.now();
    }

    public abstract String[] getFieldValues();

    public abstract String[] getFieldNames();

    public abstract String[] getEditFieldNames();

    public abstract void editValue(String field, String value);

    public abstract String getFullName();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        String number;
        if (phoneNumber.isEmpty()) {
            number = "[no number]";
        } else {
            number = phoneNumber;
        }
        return number;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getTimeModified() {
        return timeModified;
    }

    public void setTimeModified(LocalDateTime timeModified) {
        this.timeModified = timeModified;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }
}
