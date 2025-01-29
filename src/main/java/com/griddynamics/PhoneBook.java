package com.griddynamics;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhoneBook {
    private List<Contact> contactList;
    private List<Contact> filteredContacts;
    private boolean onMemory = false;
    private final String uri = System.getProperty("user.dir") + "/Contacts (Java)/task/src/contacts/";
    private final String[] file;
    // private final DateTimeFormatter formatter;

    public PhoneBook(String[] file) {
        contactList = new ArrayList<>();
        this.file = file;
        // this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    }

    public void printList(boolean flag) {
        int number = 1;
        List<Contact> temp;
        if (flag) {
            temp = contactList;
        } else {
            temp = filteredContacts;
        }

        for (Contact contact : temp) {
            String output = number + ". " + contact.getFullName();
            System.out.println(output);
            number++;
        }
    }

    public void searchContact(String query) {
        String normalizedQuery = query.toLowerCase().replaceAll("\\s+", "");

        filteredContacts = contactList.stream()
                .filter(obj -> {
                    String normalizedName = Arrays.toString(obj.getFieldValues());
                    normalizedName = normalizedName.toLowerCase().replaceAll("\\s+", "");
                    return normalizedName.contains(normalizedQuery);
                })
                .toList();

        printList(false);
    }

    public void printDetails(int index, boolean flag) {
        index--;
        Contact contact;
        if (flag) {
            contact = contactList.get(index);
        } else {
            contact = filteredContacts.get(index);
        }

        String[] fields = contact.getFieldNames();
        String[] values = contact.getFieldValues();

        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i] + ": " + values[i]);
        }
        // String timeCreated = contact.getTimeCreated().format(formatter);
        // String timeModified = contact.getTimeModified().format(formatter);
    }

    public void addContact(Contact contact) {
        contactList.addFirst(contact);
        updateFile();
    }

    public void deleteContact(int index, boolean isFromFullList) {
        index--;
        if (isFromFullList) {
            contactList.remove(index);
        } else {
            Contact elementToDelete = filteredContacts.get(index);
            contactList.remove(elementToDelete);
        }
        updateFile();
    }

    public void editContact(int index, String field, String newValue, boolean isFromFullList) {
        index--;
        List<Contact> temp;
        if (isFromFullList) {
            temp = contactList;
        } else {
            temp = filteredContacts;
        }

        Contact contact = temp.get(index);
        contact.editValue(field, newValue);
        updateFile();
    }

    public boolean isEmpty() {
        return contactList.isEmpty();
    }

    public void count() {
        System.out.println("The Phone Book has " + contactList.size() + " records.");
    }

    public void loadFile() {
        try {
            String fileName;
            if (file.length == 0) {
                onMemory = true;
                return;
            } else {
                fileName = file[0];
            }

            System.out.println("Open: " + fileName);

            File filePath = new File(uri + fileName);

            if (!filePath.exists()) {
                if (filePath.createNewFile()) {
                    // System.out.println("File created: " + fileName);
                }
            } else if (filePath.length() > 0) {
                try (FileInputStream fis = new FileInputStream(filePath);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {

                    contactList = (List<Contact>) ois.readObject();
                    // System.out.println("Contacts loaded successfully!");

                } catch (ClassNotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } // else {
            // System.out.println("File exists but is empty. No data loaded.");
            // }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();
    }

    private void updateFile() {
        if (!onMemory) {
            String fileName = (file.length == 0) ? "Contacts.db" : file[0];
            // System.out.println("Updating file: " + fileName);

            try (FileOutputStream fos = new FileOutputStream(uri + fileName);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                oos.writeObject(contactList);
                // System.out.println("File updated successfully!");

            } catch (IOException e) {
                System.out.println("Error while updating file: " + e.getMessage());
            }
        }
    }

    public String[] getContactFieldNames(int index, boolean isFromFullList) {
        index--;
        List<Contact> temp;
        if (isFromFullList) {
            temp = contactList;
        } else {
            temp = filteredContacts;
        }

        Contact contact = temp.get(index);
        return contact.getEditFieldNames();
    }
}
