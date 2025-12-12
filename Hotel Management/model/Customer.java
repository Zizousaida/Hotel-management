package model;

public class Customer {
    private String name;
    private String idNumber;
    private String phoneNumber;

    public Customer(String name, String idNumber, String phoneNumber) {
        this.name = name;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
    }

    public String getName() { return name; }
    public String getIdNumber() { return idNumber; }
    public String getPhoneNumber() { return phoneNumber; }

    @Override
    public String toString() {
        return String.format("Customer: %s (ID: %s, Phone: %s)", name, idNumber, phoneNumber);
    }
}

