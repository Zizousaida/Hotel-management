package com.hotel.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bill {
    private static int billCounter = 5000;
    private int billId;
    private Booking booking;
    private double totalAmount;
    private LocalDateTime generatedDate;

    public Bill(Booking booking) {
        this.billId = ++billCounter;
        this.booking = booking;
        this.totalAmount = booking.getRoom().getPricePerNight() * booking.getNumberOfNights();
        this.generatedDate = LocalDateTime.now();
        
        if (this.totalAmount <= 0) {
            throw new IllegalArgumentException("Bill amount must be positive");
        }
    }

    public int getBillId() { return billId; }
    public Booking getBooking() { return booking; }
    public double getTotalAmount() { return totalAmount; }
    public LocalDateTime getGeneratedDate() { return generatedDate; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("\n=== BILL #%d ===\n%s\nRoom Rate: $%.2f/night\nTotal Nights: %d\nTOTAL AMOUNT: $%.2f\nGenerated: %s\n================",
                billId, booking.getCustomer(), booking.getRoom().getPricePerNight(),
                booking.getNumberOfNights(), totalAmount, generatedDate.format(formatter));
    }
}

