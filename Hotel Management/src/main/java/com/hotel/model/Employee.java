package com.hotel.model;

public abstract class Employee extends Person {
    protected String employeeId;
    protected String username;
    protected String password;
    protected String role;

    public Employee(int id, String firstName, String lastName, String employeeId, String username, String password, String role) {
        super(id, firstName, lastName);
        this.employeeId = employeeId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getEmployeeId() { return employeeId; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    @Override
    public void displayInfo() {
        System.out.println("Employee: " + getFullName() + " (ID: " + employeeId + ", Role: " + role + ")");
    }
}

