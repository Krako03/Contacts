package com.griddynamics.ui;

import com.griddynamics.database.PhoneBook;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class RecordTest {
    Input input;
    PhoneBook phoneBook = mock(PhoneBook.class);
    Scanner scanner;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(outContent);

    @Test
    void setRecordDeleteCommand() {
        // when
        String simulatedInput = """
                list
                2
                delete
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
        doNothing().when(phoneBook).deleteContact(anyInt(), anyBoolean());
        System.setOut(printStream);

        // set
        input.takeInput();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }

    @Test
    void setRecordAddCommand() {
        // when
        String simulatedInput = """
                list
                2
                edit
                number
                ()()
                menu
                exit
                """;
        scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        input = new Input(scanner, phoneBook);
        String expectedString = """
               [menu] Enter action (add, list, search, count, exit):\s
               [list] Enter action ([number], back):\s
               [record] Enter action (edit, delete, menu): Select a field (name, address, number):\s
               Enter number: Wrong number format!
               Saved
               
               [record] Enter action (edit, delete, menu):\s
               [menu] Enter action (add, list, search, count, exit):\s""";
        doNothing().when(phoneBook).printList(anyBoolean());
        doNothing().when(phoneBook).printDetails(anyInt(), anyBoolean());
        doNothing().when(phoneBook).editContact(anyInt(), anyString(), anyString(), anyBoolean());
        when(phoneBook.isEmpty()).thenReturn(false);
        when(phoneBook.getContactFieldNames(anyInt(), anyBoolean())).thenReturn(new String[] {"name, address, number"});
        System.setOut(printStream);

        // set
        input.takeInput();
        String actualString = outContent.toString();

        // then
        assertEquals(expectedString, actualString);
    }
}
