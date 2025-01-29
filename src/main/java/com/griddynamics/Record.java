package com.griddynamics;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Record {
    private final Scanner scanner;
    private final PhoneBook phoneBook;
    private final boolean isFromFullList;
    private final int index;
    private boolean flag = true;

    public Record(PhoneBook phoneBook, Scanner scanner, int index, boolean isFromFullList) {
        this.phoneBook = phoneBook;
        this.scanner = scanner;
        this.index = index;
        this.isFromFullList = isFromFullList;
    }

    public void menuRecord() {
        while (flag) {
            System.out.print("[record] Enter action (edit, delete, menu): ");
            String command = scanner.nextLine();
            executeCommand(command);
            if (!command.equals("menu")) {
                System.out.println();
            }
        }
    }

    private void executeCommand(String command) {
        switch (command) {
            case "edit" -> getEditInput();
            case "delete" -> phoneBook.deleteContact(index, isFromFullList);
            case "menu" -> getBack();
            default -> System.out.println("Wrong command!");
        }
    }

    private void getEditInput() {
        if (phoneBook.isEmpty()) {
            System.out.println("No records to edit!");
        } else {
            String field;
            String value;

            String[] fields = phoneBook.getContactFieldNames(index, isFromFullList);

            StringBuilder output = new StringBuilder("Select a field (");

            for (int i = 0; i < fields.length - 1; i++) {
                output.append(fields[i]).append(", ");
            }

            output.append(fields[fields.length-1]).append("): ");

            System.out.println(output);
            field = scanner.nextLine();

            System.out.print("Enter " + field + ": ");
            value = scanner.nextLine();

            if (field.equals("number") && !isValidPhoneNumber(value)) {
                System.out.println("Wrong number format!");
                value = "";
            }

            phoneBook.editContact(index, field, value, isFromFullList);
            System.out.println("Saved");
            phoneBook.printDetails(index, isFromFullList);
        }
    }

    private void getBack() {
        flag = false;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // String regex = "^\\+?(?:\\([A-Za-z0-9]{1,}\\)|[A-Za-z0-9]{1,})(?:[-\\s](?:\\([A-Za-z0-9]{2,}\\)|[A-Za-z0-9]{2,}))*$";
        String regex = "[+]?(\\(?\\w+\\)?[- ]\\w{2,}|\\w+[- ]\\(?\\w{2,}\\)?|\\(?\\w*\\)?)([- ]\\w{2,})*";
        // String regex = "((^\\w*$)|(^\\+\\w*$)|(^\\+\\w\\s\\w{2})|(^\\d{3}\\W\\w{2,3}($|\\W\\w{2,3}$|\\W\\w{2,4}\\W\\w{2}$)))|((^\\(\\d{3}\\)($|\\W\\d{3}($|\\W\\d{3}($|\\W\\d{3}$))))|((^\\+(\\d\\W\\(\\d{3}\\)\\W\\d{3}\\W\\d{3}\\W\\w{2,4}$|\\(\\w*\\)$))|(^\\d{3}\\W\\(\\d{2,3}\\)($|\\W\\d{2,3}($|\\W\\d{2})))))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
