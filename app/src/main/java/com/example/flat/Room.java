package com.example.flat;

public class Room {
    public String id;
    public String roomType;
    public String location;
    public int price;

    public Room(String id, String roomType, String location, int price) {
        this.id = id;
        this.roomType = roomType;
        this.location = location;
        this.price = price;
    }
}
