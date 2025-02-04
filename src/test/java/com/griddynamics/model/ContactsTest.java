package com.griddynamics.model;

import com.griddynamics.model.Organization;
import com.griddynamics.model.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactsTest {
    @Test
    void testEditValuePersonName() {
        // when
        Person person = new Person("Juan", "Perez", "10/02/2000", "M", "123456789");

        // set
        person.editValue("name", "Pedro");
        String expextedValue = "Pedro";
        String actualValue = person.getName();

        // then
        assertEquals(expextedValue, actualValue);
    }

    @Test
    void testEditValuePersonGender() {
        // when
        Person person = new Person("Juan", "Perez", "10/02/2000", "M", "123456789");

        // set
        person.editValue("gender", "");
        String expextedValue = "[no data]";
        String actualValue = person.getGender();

        // then
        assertEquals(expextedValue, actualValue);
    }

    @Test
    void testEditValuePersonBirthDate() {
        // when
        Person person = new Person("Juan", "Perez", "10/02/2000", "M", "123456789");

        // set
        person.editValue("birth", "");
        String expextedValue = "[no data]";
        String actualValue = person.getBirthdate();

        // then
        assertEquals(expextedValue, actualValue);
    }

    @Test
    void testEditValuePersonNumber() {
        // when
        Person person = new Person("Juan", "Perez", "10/02/2000", "M", "123456789");

        // set
        person.editValue("number", "3334564443");
        String expextedValue = "3334564443";
        String actualValue = person.getPhoneNumber();

        // then
        assertEquals(expextedValue, actualValue);
    }

    @Test
    void testEditValueOrganizationName() {
        // when
        Organization organization = new Organization("Pizza Shop", "Av Valle #1242", "123456789");

        // set
        organization.editValue("name", "Pizza Place");
        String expextedValue = "Pizza Place";
        String actualValue = organization.getName();

        // then
        assertEquals(expextedValue, actualValue);
    }

    @Test
    void testEditValueOrganizationNumber() {
        // when
        Organization organization = new Organization("Pizza Shop", "Av Valle #1242", "123456789");

        // set
        organization.editValue("number", "");
        String expextedValue = "[no number]";
        String actualValue = organization.getPhoneNumber();

        // then
        assertEquals(expextedValue, actualValue);
    }
}