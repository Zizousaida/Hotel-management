package com.hotel.controller;

import com.hotel.model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HotelController {
    private Hotel hotel;
    private List<Booking> bookings;
    private List<Bill> bills;
    private List<Employee> employees;

    public HotelController(Hotel hotel) {
        this.hotel = hotel;
        this.bookings = new ArrayList<>();
        this.bills = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    public Hotel getHotel() { return hotel; }
    public List<Booking> getBookings() { return bookings; }
    public List<Bill> getBills() { return bills; }
    public List<Employee> getEmployees() { return employees; }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Employee login(String username, String password) {
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username) && employee.authenticate(password)) {
                return employee;
            }
        }
        return null;
    }

    public List<Room> getAllRooms() {
        return hotel.getRooms();
    }

    public Room findRoom(int roomId) {
        return hotel.findRoom(roomId);
    }

    public Booking findBooking(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                return booking;
            }
        }
        return null;
    }

    public void viewAllRooms() {
        System.out.println("\n=== ALL ROOMS ===");
        for (Room room : hotel.getRooms()) {
            System.out.println(room);
        }
    }

    public void viewAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found!");
            return;
        }
        System.out.println("\n=== ALL BOOKINGS ===");
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    public Booking bookRoom(Customer customer, int roomId, int numberOfNights) {
        Room room = findRoom(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found!");
        }

        if (room.getBookingStatus() == BookingStatus.BOOKED) {
            throw new IllegalStateException("Room is already booked!");
        }

        Booking booking = new Booking(customer, room, numberOfNights);
        bookings.add(booking);
        room.updateRoomStatus(BookingStatus.BOOKED, null, null);
        return booking;
    }

    public void checkInCustomer(int bookingId) {
        Booking booking = findBooking(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found!");
        }

        if (booking.getCheckInDate() != null) {
            throw new IllegalStateException("Customer already checked in!");
        }

        booking.setCheckInDate(LocalDateTime.now());
        booking.getRoom().updateRoomStatus(null, OccupancyStatus.OCCUPIED, null);
    }

    public void checkOutCustomer(int bookingId) {
        Booking booking = findBooking(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found!");
        }

        if (booking.getCheckInDate() == null) {
            throw new IllegalStateException("Customer has not checked in yet!");
        }

        if (booking.getCheckOutDate() != null) {
            throw new IllegalStateException("Customer already checked out!");
        }

        booking.setCheckOutDate(LocalDateTime.now());
        booking.getRoom().updateRoomStatus(BookingStatus.AVAILABLE, OccupancyStatus.VACANT, CleanlinessStatus.DIRTY);
        
        generateBill(booking);
    }

    public Bill generateBill(Booking booking) {
        Bill bill = new Bill(booking);
        bills.add(bill);
        return bill;
    }

    public void viewAllBills() {
        if (bills.isEmpty()) {
            System.out.println("No bills generated yet!");
            return;
        }
        System.out.println("\n=== ALL BILLS ===");
        for (Bill bill : bills) {
            System.out.println(bill);
        }
    }

    public void calculateTotalRevenue() {
        double totalRevenue = 0;
        for (Bill bill : bills) {
            totalRevenue += bill.getTotalAmount();
        }
        System.out.printf("\n=== TOTAL REVENUE ===\nTotal Revenue: $%.2f\n", totalRevenue);
        System.out.printf("Number of Bills: %d\n", bills.size());
    }

    public void markRoomAsClean(int roomId) {
        Room room = findRoom(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found!");
        }

        if (room.getCleanlinessStatus() == CleanlinessStatus.CLEAN) {
            throw new IllegalStateException("Room is already clean!");
        }

        room.updateRoomStatus(null, null, CleanlinessStatus.CLEAN);
    }

    public List<Room> getDirtyRooms() {
        List<Room> dirtyRooms = new ArrayList<>();
        for (Room room : hotel.getRooms()) {
            if (room.getCleanlinessStatus() == CleanlinessStatus.DIRTY) {
                dirtyRooms.add(room);
            }
        }
        return dirtyRooms;
    }
}

