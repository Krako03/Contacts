package com.griddynamics.ui;

import com.griddynamics.database.PhoneBook;
import com.griddynamics.model.Contact;
import com.griddynamics.model.Organization;
import com.griddynamics.model.Person;

import java.util.Scanner;
import java.util.regex.*;

public final class Input {
    private final Scanner scanner;
    private final PhoneBook phoneBook;
    private boolean flag = true;

    public Input(Scanner scanner, PhoneBook phoneBook) {
        this.scanner = scanner;
        this.phoneBook = phoneBook;
    }

    public void takeInput() {
        while (flag) {
            System.out.print("[menu] Enter action (add, list, search, count, exit): ");
            String input = scanner.nextLine();
            executeCommand(input);
            if (!input.equals("exit")) {
                System.out.println();
            }
        }
    }

    private void executeCommand(String input) {
        switch (input) {
            case "add" -> getAddInput();
            case "list" -> getList();
            case "search" -> getSearchInput();
            case "count" -> phoneBook.count();
            case "exit" -> exit();
            default ->
                    System.out.println("Wrong command");
        }
    }

    private void getAddInput() {
        System.out.print("Enter the type (person, organization): ");
        String type = scanner.nextLine();
        switch (type) {
            case "person" -> getPersonAddInput();
            case "organization" -> getOrganizationAddInput();
            default -> System.out.println("Wrong type!");
        }
    }

    private void getPersonAddInput() {
        String name;
        String surname;
        String birthday;
        String gender;
        String number;
        System.out.print("Enter the name: ");
        name = scanner.nextLine();
        System.out.print("Enter the surname: ");
        surname = scanner.nextLine();

        System.out.print("Enter the birth date: ");
        birthday = scanner.nextLine();
        if (birthday.isEmpty()) {
            System.out.println("Bad birth date!");
            birthday = "";
        }

        System.out.print("Enter the gender (M, F): ");
        gender = scanner.nextLine();
        if (gender.isEmpty()) {
            System.out.println("Bad gender!");
            gender = "";
        }

        System.out.print("Enter the number: ");
        number = scanner.nextLine();
        if (isNotValidPhoneNumber(number)) {
            System.out.println("Wrong number format!");
            number = "";
        }

        Contact contact = new Person(name, surname, birthday, gender, number);

        phoneBook.addContact(contact);
        System.out.println("The record added.");
    }

    private void getOrganizationAddInput() {
        String name;
        String address;
        String number;
        System.out.print("Enter the organization name: ");
        name = scanner.nextLine();
        System.out.print("Enter the address: ");
        address = scanner.nextLine();

        System.out.print("Enter the number: ");
        number = scanner.nextLine();
        if (isNotValidPhoneNumber(number)) {
            System.out.println("Wrong number format!");
            number = "";
        }

        Contact contact = new Organization(name, address, number);

        phoneBook.addContact(contact);
        System.out.println("The record added.");
    }

    private void exit() {
        flag = false;
    }

    private boolean isNotValidPhoneNumber(String phoneNumber) {
        // String regex = "^\\+?(?:\\([A-Za-z0-9]{1,}\\)|[A-Za-z0-9]{1,})(?:[-\\s](?:\\([A-Za-z0-9]{2,}\\)|[A-Za-z0-9]{2,}))*$";
        String regex = "[+]?(\\(?\\w+\\)?[- ]\\w{2,}|\\w+[- ]\\(?\\w{2,}\\)?|\\(?\\w*\\)?)([- ]\\w{2,})*";
        // String regex = "((^\\w*$)|(^\\+\\w*$)|(^\\+\\w\\s\\w{2})|(^\\d{3}\\W\\w{2,3}($|\\W\\w{2,3}$|\\W\\w{2,4}\\W\\w{2}$)))|((^\\(\\d{3}\\)($|\\W\\d{3}($|\\W\\d{3}($|\\W\\d{3}$))))|((^\\+(\\d\\W\\(\\d{3}\\)\\W\\d{3}\\W\\d{3}\\W\\w{2,4}$|\\(\\w*\\)$))|(^\\d{3}\\W\\(\\d{2,3}\\)($|\\W\\d{2,3}($|\\W\\d{2})))))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return !matcher.matches();
    }

    private void getList() {
        phoneBook.printList(true);
        System.out.println();
        List list = new List(phoneBook, scanner);
        list.menuList();
    }

    private void getSearchInput() {
        Search search = new Search(phoneBook, scanner);
        search.getSearchInput();
        System.out.println();
        search.menuSearch();
    }
}
