package com.griddynamics;

import java.util.Scanner;

public class List {
    private final Scanner scanner;
    private final PhoneBook phoneBook;
    private boolean flag = true;
    private boolean recFlag = false;
    private int recNum;

    public List(PhoneBook phoneBook, Scanner scanner) {
        this.phoneBook = phoneBook;
        this.scanner = scanner;
    }

    public void menuList() {
        while (flag) {
            System.out.print("[list] Enter action ([number], back): ");
            String command = scanner.nextLine();
            executeCommand(command);
            System.out.println();
        }
        if (recFlag) {
            Record record = new Record(phoneBook, scanner, recNum, true);
            record.menuRecord();
        }
    }

    private void executeCommand(String command) {
        if (command.equals("back")) {
            getBack();
        } else {
            if (command.chars().allMatch(Character::isDigit)) {
                getRecord(command);
            } else {
                System.out.println("Wrong command!");
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
        phoneBook.printDetails(recNum, true);
    }
}