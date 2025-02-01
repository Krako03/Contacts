package com.griddynamics.database;

import com.griddynamics.model.Contact;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PhoneBook {
    private List<Contact> contactList;
    private List<Contact> filteredContacts;
    private boolean onMemory = false;
    private final String uri = System.getProperty("user.dir")
            + "/src/main/resources/";
    private final String[] file;

    public PhoneBook(final String[] file) {
        contactList = new ArrayList<>();
        this.file = file;
    }

    public void printList(final boolean flag) {
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

    public void searchContact(final String query) {
        String normalizedQuery = query.toLowerCase().replaceAll(" ", "");

        filteredContacts = contactList.stream()
                .filter(obj -> {
                    String normalized = Arrays.toString(obj.getFieldValues());
                    normalized = normalized.toLowerCase()
                            .replaceAll(" ", "");
                    return normalized.contains(normalizedQuery);
                })
                .toList();

        printList(false);
    }

    public void printDetails(
            int index, final boolean flag) {
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
    }

    public void addContact(final Contact contact) {
        contactList.add(contact);
        updateFile();
    }

    public void deleteContact(
            int index, final boolean isFromFullList) {
        index--;
        if (isFromFullList) {
            contactList.remove(index);
        } else {
            Contact elementToDelete = filteredContacts.get(index);
            contactList.remove(elementToDelete);
        }
        updateFile();
    }

    public void editContact(int index, final String field,
                            final String newValue,
                            final boolean isFromFullList) {
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
        System.out.println("The Phone Book has "
                + contactList.size() + " records.");
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
                filePath.createNewFile();
            } else if (filePath.length() > 0) {
                try (FileInputStream fis = new FileInputStream(filePath);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {

                    contactList = (List<Contact>) ois.readObject();

                } catch (ClassNotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();
    }

    private void updateFile() {
        if (!onMemory) {
            String fileName = (file.length == 0) ? "Contacts.db" : file[0];

            try (FileOutputStream fos = new FileOutputStream(uri + fileName);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                oos.writeObject(contactList);

            } catch (IOException e) {
                System.out.println("Error while updating file: "
                        + e.getMessage());
            }
        }
    }

    public String[] getContactFieldNames(
            int index, final boolean isFromFullList) {
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

    public void setOnMemory(boolean onMemory) {
        this.onMemory = onMemory;
    }
}
