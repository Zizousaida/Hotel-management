package model;

public class Room {
    private int roomNumber;
    private String roomType;
    private double pricePerNight;
    private BookingStatus bookingStatus;
    private OccupancyStatus occupancyStatus;
    private CleanlinessStatus cleanlinessStatus;

    public Room(int roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.bookingStatus = BookingStatus.AVAILABLE;
        this.occupancyStatus = OccupancyStatus.VACANT;
        this.cleanlinessStatus = CleanlinessStatus.CLEAN;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public double getPricePerNight() { return pricePerNight; }
    public BookingStatus getBookingStatus() { return bookingStatus; }
    public OccupancyStatus getOccupancyStatus() { return occupancyStatus; }
    public CleanlinessStatus getCleanlinessStatus() { return cleanlinessStatus; }

    public void setBookingStatus(BookingStatus status) { this.bookingStatus = status; }
    public void setOccupancyStatus(OccupancyStatus status) { this.occupancyStatus = status; }
    public void setCleanlinessStatus(CleanlinessStatus status) { this.cleanlinessStatus = status; }

    public void updateRoomStatus(BookingStatus booking, OccupancyStatus occupancy, CleanlinessStatus cleanliness) {
        if (booking != null) this.bookingStatus = booking;
        if (occupancy != null) this.occupancyStatus = occupancy;
        if (cleanliness != null) this.cleanlinessStatus = cleanliness;
    }

    @Override
    public String toString() {
        return String.format("Room %d [%s] - $%.2f/night | Booking: %s | Occupancy: %s | Clean: %s",
                roomNumber, roomType, pricePerNight, bookingStatus, occupancyStatus, cleanlinessStatus);
    }
}

