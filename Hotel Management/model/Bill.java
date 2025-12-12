package model;

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
    }

    public int getBillId() { return billId; }
    public Booking getBooking() { return booking; }
    public double getTotalAmount() { return totalAmount; }
    public LocalDateTime getGeneratedDate() { return generatedDate; }
}

