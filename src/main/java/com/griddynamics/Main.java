package com.griddynamics;


import com.griddynamics.database.PhoneBook;
import com.griddynamics.ui.Input;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PhoneBook phoneBook = new PhoneBook(args);
        Input input = new Input(scanner, phoneBook);
        phoneBook.loadFile();
        input.takeInput();
        scanner.close();
    }
}
