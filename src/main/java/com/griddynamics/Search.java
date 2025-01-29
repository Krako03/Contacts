package com.griddynamics;

import java.util.Scanner;

public class Search {
    private final Scanner scanner;
    private final PhoneBook phoneBook;
    private boolean flag = true;
    private boolean recFlag = false;
    private int recNum;

    public Search(PhoneBook phoneBook, Scanner scanner) {
        this.phoneBook = phoneBook;
        this.scanner = scanner;
    }

    public void getSearchInput() {
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();
        phoneBook.searchContact(query);
    }

    public void menuSearch() {
        flag = true;
        recFlag = false;
        while (flag) {
            System.out.print("[search] Enter action ([number], back, again): ");
            String command = scanner.nextLine();
            executeCommand(command);
            if (!command.equals("back")) {
                System.out.println();
            }
        }
        if (recFlag) {
            Record record = new Record(phoneBook, scanner, recNum, false);
            record.menuRecord();
        }
    }

    private void executeCommand(String command) {
        switch (command) {
            case "back" -> getBack();
            case "again" -> getSearchInput();
            default -> {
                if (command.chars().allMatch(Character::isDigit)) {
                    getRecord(command);
                } else {
                    System.out.println("Wrong command!");
                }
            }
        }
    }

    private void getBack() {
        flag = false;
    }

    private void getRecord(String command) {
        flag = false;
        recFlag = true;
        recNum = Integer.parseInt(command);
        phoneBook.printDetails(recNum, false);
    }
}
