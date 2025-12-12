package com.hotel.model;

import com.hotel.controller.HotelController;

public class Manager extends Employee {
    private HotelController controller;

    public Manager(int id, String firstName, String lastName, String employeeId, String username, String password, HotelController controller) {
        super(id, firstName, lastName, employeeId, username, password, "Manager");
        this.controller = controller;
    }

    public void viewAllBills() {
        controller.viewAllBills();
    }

    public void calculateTotalRevenue() {
        controller.calculateTotalRevenue();
    }
}

