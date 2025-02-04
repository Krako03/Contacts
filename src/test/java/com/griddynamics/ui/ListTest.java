package com.griddynamics.ui;

import com.griddynamics.database.PhoneBook;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class ListTest {
    Input input;
    PhoneBook phoneBook = mock(PhoneBook.class);
    Scanner scanner;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(outContent);

    @Test
    void setListWrongCommand() {
        // when
        String simulatedInput = """
                list
                frty
                back
                exit
                """;
        scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        input = new Input(scanner, phoneBook);
        String expectedString = """
               [menu] Enter action (add, list, search, count, exit):\s
               [list] Enter action ([number], back): Wrong command!
               
               [list] Enter action ([number], back):\s
              
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
    void setListRecordCommand() {
        // when
        String simulatedInput = """
                list
                2
                menu
                exit
                """;
        scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        input = new Input(scanner, phoneBook);
        String expectedString = """
               [menu] Enter action (add, list, search, count, exit):\s
               [list] Enter action ([number], back):\s
               [record] Enter action (edit, delete, menu):\s
               [menu] Enter action (add, list, search, count, exit):\s""";
        doNothing().when(phoneBook).printList(anyBoolean());
        doNothing().when(phoneBook).printDetails(anyInt(), anyBoolean());
        System.setOut(printStream);

        // set
        input.takeInput();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }
}
