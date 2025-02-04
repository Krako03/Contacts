package com.griddynamics.ui;

import com.griddynamics.database.PhoneBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InputTest {
    Input input;
    PhoneBook phoneBook = mock(PhoneBook.class);
    Scanner scanner;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(outContent);

    @Test
    void setInputWrongCommand() {
        // when
        String simulatedInput = """
                exitt
                exit
                """;
        scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        input = new Input(scanner, phoneBook);
        String expectedString = """
               [menu] Enter action (add, list, search, count, exit): Wrong command
               
               [menu] Enter action (add, list, search, count, exit):\s""";
        System.setOut(printStream);

        // set
        input.takeInput();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }

    @Test
    void setInputCountCommand() {
        // when
        String simulatedInput = """
                count
                exit
                """;
        scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        input = new Input(scanner, phoneBook);
        doAnswer(invocationOnMock -> {
            System.out.println("The Phone Book has 0 records.");
            return null;
        }).when(phoneBook).count();
        String expectedString = """
               [menu] Enter action (add, list, search, count, exit): The Phone Book has 0 records.
               
               [menu] Enter action (add, list, search, count, exit):\s""";
        System.setOut(printStream);

        // set
        input.takeInput();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }

    @Test
    void setInputSearchCommand() {
        // when
        String simulatedInput = """
                search
                con
                back
                exit
                """;
        scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        input = new Input(scanner, phoneBook);

        doAnswer(invocationOnMock -> {
            System.out.println("1. Contador Publico");
            return null;
        }).when(phoneBook).searchContact(anyString());

        String expectedString = """
               [menu] Enter action (add, list, search, count, exit): Enter search query: 1. Contador Publico
               
               [search] Enter action ([number], back, again):\s
               [menu] Enter action (add, list, search, count, exit):\s""";
        System.setOut(printStream);

        // set
        input.takeInput();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }

    @Test
    void setInputEditCommand() {
        // when
        String simulatedInput = """
                list
                back
                exit
                """;
        scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        input = new Input(scanner, phoneBook);

        doAnswer(invocationOnMock -> {
            System.out.println("1. Contador Publico");
            System.out.println("2. Juan Perez");
            return null;
        }).when(phoneBook).printList(anyBoolean());

        String expectedString = """
               [menu] Enter action (add, list, search, count, exit): 1. Contador Publico
               2. Juan Perez
               
               [list] Enter action ([number], back):\s
               
               [menu] Enter action (add, list, search, count, exit):\s""";
        System.setOut(printStream);

        // set
        input.takeInput();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }

    @Test
    void setInputAddPerson() {
        // when
        String simulatedInput = """
                add
                person
                Juan
                Perez
                10/12/01
                M
                333456789
                exit
                """;
        scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        input = new Input(scanner, phoneBook);

        doNothing().when(phoneBook).addContact(any());

        String expectedString = """
               [menu] Enter action (add, list, search, count, exit): Enter the type (person, organization): Enter the name: Enter the surname: Enter the birth date: Enter the gender (M, F): Enter the number: The record added.
               
               [menu] Enter action (add, list, search, count, exit):\s""";
        System.setOut(printStream);

        // set
        input.takeInput();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }

    @Test
    void setInputAddWrongPerson() {
        // when
        String simulatedInput = """
                add
                person
                Juan
                Perez
                
                
                ()()
                exit
                """;
        scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        input = new Input(scanner, phoneBook);

        doNothing().when(phoneBook).addContact(any());

        String expectedString = """
               [menu] Enter action (add, list, search, count, exit): Enter the type (person, organization): Enter the name: Enter the surname: Enter the birth date: Bad birth date!
               Enter the gender (M, F): Bad gender!
               Enter the number: Wrong number format!
               The record added.
               
               [menu] Enter action (add, list, search, count, exit):\s""";
        System.setOut(printStream);

        // set
        input.takeInput();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }

    @Test
    void setInputAddOrganization() {
        // when
        String simulatedInput = """
                add
                organization
                Pizza Shop
                Av Valle #102
                333456789
                exit
                """;
        scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        input = new Input(scanner, phoneBook);

        doNothing().when(phoneBook).addContact(any());

        String expectedString = """
               [menu] Enter action (add, list, search, count, exit): Enter the type (person, organization): Enter the organization name: Enter the address: Enter the number: The record added.
               
               [menu] Enter action (add, list, search, count, exit):\s""";
        System.setOut(printStream);

        // set
        input.takeInput();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }

    @Test
    void setInputAddWrongOrganization() {
        // when
        String simulatedInput = """
                add
                organization
                Pizza Shop
                Av Valle #102
                ()()
                exit
                """;
        scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        input = new Input(scanner, phoneBook);

        doNothing().when(phoneBook).addContact(any());

        String expectedString = """
               [menu] Enter action (add, list, search, count, exit): Enter the type (person, organization): Enter the organization name: Enter the address: Enter the number: Wrong number format!
               The record added.
               
               [menu] Enter action (add, list, search, count, exit):\s""";
        System.setOut(printStream);

        // set
        input.takeInput();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }
}