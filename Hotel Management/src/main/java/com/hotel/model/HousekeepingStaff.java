package com.hotel.model;

import com.hotel.controller.HotelController;

public class HousekeepingStaff extends Employee {
    private HotelController controller;

    public HousekeepingStaff(int id, String firstName, String lastName, String employeeId, String username, String password, HotelController controller) {
        super(id, firstName, lastName, employeeId, username, password, "Housekeeping Staff");
        this.controller = controller;
    }

    public void markRoomAsClean(int roomId) {
        controller.markRoomAsClean(roomId);
    }
}

