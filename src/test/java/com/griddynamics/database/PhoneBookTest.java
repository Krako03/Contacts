package com.griddynamics.database;

import com.griddynamics.model.Contact;
import com.griddynamics.model.Organization;
import com.griddynamics.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class PhoneBookTest {
    private PhoneBook phoneBook;
    private String[] file;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(outContent);

    @BeforeEach
    void setUp() {
        file = new String[] {"json.db"};
        phoneBook = new PhoneBook(file);
        phoneBook.setOnMemory(true);
    }

    @Test
    void testPrintPersonList() {
        //set
        phoneBook.addContact(new Person("Juan", "Perez", "10/02/2000", "M", "123456789"));
        phoneBook.addContact(new Person("Saul", "Bejar", "04/12/2003", "M", "987654321"));
        phoneBook.addContact(new Person("Oscar", "Vega", "19/10/1992", "M", "123987456"));
        String expectedString = "1. Juan Perez\n" +
                "2. Saul Bejar\n" +
                "3. Oscar Vega\n";
        System.setOut(printStream);

        //when
        phoneBook.printList(true);
        String actualString = outContent.toString();

        //then
        assertEquals(expectedString, actualString);
    }

    @Test
    void testPrintOrganizationList() {
        //set
        phoneBook.addContact(new Organization("Pizza Shop", "Av Valle #1242", "123456789"));
        phoneBook.addContact(new Organization("Mechanic", "Av Real #306", "987654321"));
        phoneBook.addContact(new Organization("Barber Shop", "Av Jardin #45", "123890456"));
        String expectedString = "1. Pizza Shop\n" +
                "2. Mechanic\n" +
                "3. Barber Shop\n";
        System.setOut(printStream);

        //when
        phoneBook.printList(true);
        String actualString = outContent.toString();

        //then
        assertEquals(expectedString, actualString);
    }

    @Test
    void testFilterSearch() {
        //set
        phoneBook.addContact(new Person("Saul", "Bejar", "04/12/2003", "M", "987654321"));
        phoneBook.addContact(new Person("Juan", "Perez", "10/02/2000", "M", "123456789"));
        phoneBook.addContact(new Organization("Mechanic", "Av Real #306", "987654321"));
        phoneBook.addContact(new Organization("Barber Shop", "Av Jardin #45", "123890456"));
        phoneBook.addContact(new Person("Oscar", "Vega", "19/10/1992", "M", "123987456"));
        String expectedString = "1. Saul Bejar\n" +
                "2. Barber Shop\n" +
                "3. Oscar Vega\n";
        System.setOut(printStream);

        //when
        phoneBook.searchContact("ar");
        String actualString = outContent.toString();

        //then
        assertEquals(expectedString, actualString);
    }

    @Test
    void testPrintPersonDetails() {
        //set
        phoneBook.addContact(new Person("Saul", "Bejar", "04/12/2003", "M", "987654321"));
        phoneBook.addContact(new Person("Juan", "Perez", "10/02/2000", "M", "123456789"));
        LocalDateTime cr = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        phoneBook.addContact(new Organization("Mechanic", "Av Real #306", "987654321"));
        phoneBook.addContact(new Organization("Barber Shop", "Av Jardin #45", "123890456"));
        phoneBook.addContact(new Person("Oscar", "Vega", "19/10/1992", "M", "123987456"));
        String expectedString = "Name: Juan\n" +
                "Surname: Perez\n" +
                "Birth date: 10/02/2000\n" +
                "Gender: M\n" +
                "Number: 123456789\n" +
                "Time created: " + cr + "\n" +
                "Time last edit: " + cr + "\n";
        System.setOut(printStream);

        //when
        phoneBook.printDetails(2, true);
        String actualString = outContent.toString();

        //then
        assertEquals(expectedString, actualString);
    }

    @Test
    void testPrintOrganizationDetails() {
        //set
        phoneBook.addContact(new Person("Saul", "Bejar", "04/12/2003", "M", "987654321"));
        phoneBook.addContact(new Person("Juan", "Perez", "10/02/2000", "M", "123456789"));
        phoneBook.addContact(new Organization("Mechanic", "Av Real #306", "987654321"));
        LocalDateTime cr = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        phoneBook.addContact(new Organization("Barber Shop", "Av Jardin #45", "123890456"));
        phoneBook.addContact(new Person("Oscar", "Vega", "19/10/1992", "M", "123987456"));
        String expectedString = "Name: Mechanic\n" +
                "Address: Av Real #306\n" +
                "Number: 987654321\n" +
                "Time created: " + cr + "\n" +
                "Time last edit: " + cr + "\n";
        System.setOut(printStream);

        //when
        phoneBook.printDetails(3, true);
        String actualString = outContent.toString();

        //then
        assertEquals(expectedString, actualString);
    }

    @Test
    void testDelete() {
        //set
        phoneBook.addContact(new Person("Juan", "Perez", "10/02/2000", "M", "123456789"));
        phoneBook.addContact(new Person("Saul", "Bejar", "04/12/2003", "M", "987654321"));
        phoneBook.addContact(new Person("Oscar", "Vega", "19/10/1992", "M", "123987456"));
        String expectedString = "1. Juan Perez\n" +
                "2. Oscar Vega\n";
        System.setOut(printStream);

        //when
        phoneBook.deleteContact(2, true);
        phoneBook.printList(true);
        String actualString = outContent.toString();

        //then
        assertEquals(expectedString, actualString);
    }

    @Test
    void testEditPerson() {
        //set
        phoneBook.addContact(new Person("Juan", "Perez", "10/02/2000", "M", "123456789"));
        String expectedString = "1. Juan Arteaga\n";
        System.setOut(printStream);

        //when
        phoneBook.editContact(1, "surname", "Arteaga", true);
        phoneBook.printList(true);
        String actualString = outContent.toString();

        //then
        assertEquals(expectedString, actualString);
    }

    @Test
    void testEditOrganization() {
        //set
        phoneBook.addContact(new Organization("Barber Shop", "Av Jardin #45", "123789456"));
        LocalDateTime cr = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        String expectedString = "Name: Barber Shop\n" +
                "Address: Av Real #306\n" +
                "Number: 123789456\n" +
                "Time created: " + cr + "\n" +
                "Time last edit: " + cr + "\n";
        System.setOut(printStream);

        //when
        phoneBook.editContact(1, "address", "Av Real #306", true);
        phoneBook.printDetails(1, true);
        String actualString = outContent.toString();

        //then
        assertEquals(expectedString, actualString);
    }

    @Test
    void testIsEmpty() {
        assertTrue(phoneBook.isEmpty());
    }

    @Test
    void testIsNotEmpty() {
        phoneBook.addContact(new Organization("Barber Shop", "Av Jardin #45", "123789456"));
        assertFalse(phoneBook.isEmpty());
    }

    @Test
    void testCount() {
        //set
        phoneBook.addContact(new Person("Saul", "Bejar", "04/12/2003", "M", "987654321"));
        phoneBook.addContact(new Organization("Barber Shop", "Av Jardin #45", "123789456"));
        String expectedString = "The Phone Book has 2 records.\n";
        System.setOut(printStream);

        // when
        phoneBook.count();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }
}