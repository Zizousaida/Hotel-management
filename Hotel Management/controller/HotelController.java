package controller;

import model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HotelController {
    private List<Room> rooms;
    private List<Booking> bookings;
    private List<Bill> bills;
    private List<User> users;

    public HotelController() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
        bills = new ArrayList<>();
        users = new ArrayList<>();
        initializeData();
    }

    private void initializeData() {
        rooms.add(new Room(101, "Single", 80.0));
        rooms.add(new Room(102, "Single", 80.0));
        rooms.add(new Room(201, "Double", 120.0));
        rooms.add(new Room(202, "Double", 120.0));
        rooms.add(new Room(301, "Suite", 200.0));
        rooms.add(new Room(302, "Suite", 200.0));

        users.add(new Receptionist("receptionist", "recep123"));
        users.add(new HousekeepingStaff("housekeeper", "house123"));
        users.add(new Manager("manager", "manager123"));
    }

    public List<Room> getRooms() { return rooms; }
    public List<Booking> getBookings() { return bookings; }
    public List<Bill> getBills() { return bills; }

    public Room findRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public Booking findBooking(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                return booking;
            }
        }
        return null;
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.authenticate(password)) {
                return user;
            }
        }
        return null;
    }

    public void bookRoom(Customer customer, int roomNumber, int numberOfNights) {
        Room room = findRoom(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("Room not found!");
        }
        if (room.getBookingStatus() == BookingStatus.BOOKED) {
            throw new IllegalStateException("Room is already booked!");
        }
        Booking booking = new Booking(customer, room, numberOfNights);
        bookings.add(booking);
        room.updateRoomStatus(BookingStatus.BOOKED, null, null);
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
        Bill bill = new Bill(booking);
        bills.add(bill);
    }

    public void markRoomAsClean(int roomNumber) {
        Room room = findRoom(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("Room not found!");
        }
        room.updateRoomStatus(null, null, CleanlinessStatus.CLEAN);
    }

    public List<Room> getDirtyRooms() {
        List<Room> dirtyRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getCleanlinessStatus() == CleanlinessStatus.DIRTY) {
                dirtyRooms.add(room);
            }
        }
        return dirtyRooms;
    }

    public double getTotalRevenue() {
        double total = 0;
        for (Bill bill : bills) {
            total += bill.getTotalAmount();
        }
        return total;
    }
}

