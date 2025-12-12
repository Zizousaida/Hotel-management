package com.hotel.model;

public class Customer extends Person {
    private String idNumber;
    private String phoneNumber;
    private String email;
    private String password;

    public Customer(int id, String firstName, String lastName, String idNumber, String phoneNumber) {
        super(id, firstName, lastName);
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
    }

    public Customer(int id, String firstName, String lastName, String idNumber, String phoneNumber, String email, String password) {
        super(id, firstName, lastName);
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public String getIdNumber() { return idNumber; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public void displayInfo() {
        System.out.println("Customer: " + getFullName() + " (ID: " + idNumber + ", Phone: " + phoneNumber + ")");
    }

    @Override
    public String toString() {
        return String.format("Customer: %s (ID: %s, Phone: %s)", getFullName(), idNumber, phoneNumber);
    }
}

