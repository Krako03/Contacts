package com.griddynamics;

import java.time.LocalDateTime;

public class Organization extends Contact {
    private String address;

    public Organization(String name, String address, String phoneNumber) {
        super();
        super.name = name;
        this.address = address;
        super.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String[] getFieldValues() {
        return new String[] {getName(), getAddress(), getPhoneNumber()};
    }

    @Override
    public String[] getFieldNames() {
        return new String[] {"Name", "Address", "Number", "Time created", "Time last edit"};
    }

    @Override
    public String[] getEditFieldNames() {
        return new String[] {"name", "address", "number"};
    }

    @Override
    public void editValue(String field, String value) {
        switch (field) {
            case "name" -> setName(value);
            case "address" -> setAddress(value);
            case "number" -> setPhoneNumber(value);
            default -> System.out.println("Wrong field!");
        }
        setTimeModified(LocalDateTime.now());
    }

    @Override
    public String getFullName() {
        return getName();
    }
}
