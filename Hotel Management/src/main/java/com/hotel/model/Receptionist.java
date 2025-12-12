package com.hotel.model;

import com.hotel.controller.HotelController;

public class Receptionist extends Employee {
    private HotelController controller;

    public Receptionist(int id, String firstName, String lastName, String employeeId, String username, String password, HotelController controller) {
        super(id, firstName, lastName, employeeId, username, password, "Receptionist");
        this.controller = controller;
    }

    public void checkInCustomer(int bookingId) {
        controller.checkInCustomer(bookingId);
    }

    public void checkOutCustomer(int bookingId) {
        controller.checkOutCustomer(bookingId);
    }

    public void generateBill(Booking booking) {
        controller.generateBill(booking);
    }
}

