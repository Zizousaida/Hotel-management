package com.hotel.model;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String name;
    private String address;
    private List<Room> rooms;

    public Hotel(String name, String address) {
        this.name = name;
        this.address = address;
        this.rooms = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public List<Room> getRooms() { return rooms; }

    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Room findRoom(int roomId) {
        for (Room room : rooms) {
            if (room.getRoomId() == roomId) {
                return room;
            }
        }
        return null;
    }
}

