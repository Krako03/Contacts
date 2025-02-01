package com.griddynamics.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public final class Person extends Contact {
    private String surname;
    private String birthdate;
    private String gender;

    public Person(String name, String surname, String birthdate, String gender, String phoneNumber) {
        super();
        super.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.gender = gender;
        super.phoneNumber = phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthdate() {
        String gen;
        if (birthdate.isEmpty()) {
            gen = "[no data]";
        } else {
            gen = birthdate;
        }
        return gen;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        String gen;
        if (gender.isEmpty()) {
            gen = "[no data]";
        } else {
            gen = gender;
        }
        return gen;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String[] getFieldValues() {
        return new String[] {getName(), getSurname(), getBirthdate(),
                getGender(), getPhoneNumber(), getTimeCreated().toString(),
                getTimeModified().toString()};
    }

    @Override
    public String[] getFieldNames() {
        return new String[] {"Name", "Surname", "Birth date",
                "Gender", "Number", "Time created", "Time last edit"};
    }

    @Override
    public String[] getEditFieldNames() {
        return new String[] {"name", "surname", "birth",
                "gender", "number"};
    }

    @Override
    public void editValue(String field, String value) {
        switch (field) {
            case "name" -> setName(value);
            case "surname" -> setSurname(value);
            case "birth" -> setBirthdate(value);
            case "gender" -> setGender(value);
            case "number" -> setPhoneNumber(value);
            default -> System.out.println("Wrong field!");
        }
        setTimeModified(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    @Override
    public String getFullName() {
        return getName() + " " + getSurname();
    }
}
