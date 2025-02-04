package com.griddynamics.ui;

import com.griddynamics.database.PhoneBook;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class SearchTest {
    Input input;
    PhoneBook phoneBook = mock(PhoneBook.class);
    Scanner scanner;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(outContent);

    @Test
    void setSearchWrongCommand() {
        // when
        String simulatedInput = """
                search
                con
                fdf
                back
                exit
                """;
        scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        input = new Input(scanner, phoneBook);
        String expectedString = """
               [menu] Enter action (add, list, search, count, exit): Enter search query:\s
               [search] Enter action ([number], back, again): Wrong command!
               
               [search] Enter action ([number], back, again):\s
               [menu] Enter action (add, list, search, count, exit):\s""";
        doNothing().when(phoneBook).printList(anyBoolean());
        System.setOut(printStream);

        // set
        input.takeInput();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }

    @Test
    void setSearchCommand() {
        // when
        String simulatedInput = """
                search
                con
                2
                menu
                exit
                """;
        scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        input = new Input(scanner, phoneBook);
        String expectedString = """
               [menu] Enter action (add, list, search, count, exit): Enter search query:\s
               [search] Enter action ([number], back, again):\s
               [record] Enter action (edit, delete, menu):\s
               [menu] Enter action (add, list, search, count, exit):\s""";
        doNothing().when(phoneBook).printList(anyBoolean());
        System.setOut(printStream);

        // set
        input.takeInput();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }
}
