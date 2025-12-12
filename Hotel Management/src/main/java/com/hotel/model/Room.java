package com.hotel.model;

public class Room {
    private int roomId;
    private String roomType;
    private double pricePerNight;
    private BookingStatus bookingStatus;
    private OccupancyStatus occupancyStatus;
    private CleanlinessStatus cleanlinessStatus;

    public Room(int roomId, String roomType, double pricePerNight) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.bookingStatus = BookingStatus.AVAILABLE;
        this.occupancyStatus = OccupancyStatus.VACANT;
        this.cleanlinessStatus = CleanlinessStatus.CLEAN;
    }

    public int getRoomId() { return roomId; }
    public String getRoomType() { return roomType; }
    public double getPricePerNight() { return pricePerNight; }
    public BookingStatus getBookingStatus() { return bookingStatus; }
    public OccupancyStatus getOccupancyStatus() { return occupancyStatus; }
    public CleanlinessStatus getCleanlinessStatus() { return cleanlinessStatus; }

    public void setBookingStatus(BookingStatus status) { 
        this.bookingStatus = status; 
    }
    
    public void setOccupancyStatus(OccupancyStatus status) { 
        this.occupancyStatus = status; 
    }
    
    public void setCleanlinessStatus(CleanlinessStatus status) { 
        this.cleanlinessStatus = status; 
    }

    public void updateBookingStatus(BookingStatus booking) {
        if (booking != null) this.bookingStatus = booking;
    }

    public void updateOccupancyStatus(OccupancyStatus occupancy) {
        if (occupancy != null) this.occupancyStatus = occupancy;
    }

    public void updateCleanlinessStatus(CleanlinessStatus cleanliness) {
        if (cleanliness != null) this.cleanlinessStatus = cleanliness;
    }

    public void updateRoomStatus(BookingStatus booking, OccupancyStatus occupancy, CleanlinessStatus cleanliness) {
        updateBookingStatus(booking);
        updateOccupancyStatus(occupancy);
        updateCleanlinessStatus(cleanliness);
    }

    @Override
    public String toString() {
        return String.format("Room %d [%s] - $%.2f/night | Booking: %s | Occupancy: %s | Clean: %s",
                roomId, roomType, pricePerNight, bookingStatus, occupancyStatus, cleanlinessStatus);
    }
}

