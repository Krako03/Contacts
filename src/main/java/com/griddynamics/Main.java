package com.griddynamics;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook(args);
        Scanner scanner = new Scanner(System.in);
        Input input = new Input(scanner, phoneBook);
        input.takeInput();
        scanner.close();
    }
}