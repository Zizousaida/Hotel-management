package com.hotel.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {
    private static int bookingCounter = 1000;
    private int bookingId;
    private Customer customer;
    private Room room;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private int numberOfNights;
    private String status;

    public Booking(Customer customer, Room room, int numberOfNights) {
        this.bookingId = ++bookingCounter;
        this.customer = customer;
        this.room = room;
        this.numberOfNights = numberOfNights;
        this.checkInDate = null;
        this.checkOutDate = null;
        this.status = "reserved";
    }

    public int getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public Room getRoom() { return room; }
    public LocalDateTime getCheckInDate() { return checkInDate; }
    public LocalDateTime getCheckOutDate() { return checkOutDate; }
    public int getNumberOfNights() { return numberOfNights; }
    public String getStatus() { return status; }

    public void setCheckInDate(LocalDateTime date) { this.checkInDate = date; }
    public void setCheckOutDate(LocalDateTime date) { this.checkOutDate = date; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String checkIn = checkInDate != null ? checkInDate.format(formatter) : "Not checked in";
        String checkOut = checkOutDate != null ? checkOutDate.format(formatter) : "Not checked out";
        return String.format("Booking #%d | %s | Room %d | Nights: %d | Check-in: %s | Check-out: %s | Status: %s",
                bookingId, customer.getFullName(), room.getRoomId(), numberOfNights, checkIn, checkOut, status);
    }
}

