package model;

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

    public Booking(Customer customer, Room room, int numberOfNights) {
        this.bookingId = ++bookingCounter;
        this.customer = customer;
        this.room = room;
        this.numberOfNights = numberOfNights;
        this.checkInDate = null;
        this.checkOutDate = null;
    }

    public int getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public Room getRoom() { return room; }
    public LocalDateTime getCheckInDate() { return checkInDate; }
    public LocalDateTime getCheckOutDate() { return checkOutDate; }
    public int getNumberOfNights() { return numberOfNights; }

    public void setCheckInDate(LocalDateTime date) { this.checkInDate = date; }
    public void setCheckOutDate(LocalDateTime date) { this.checkOutDate = date; }
}

