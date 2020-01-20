package com.example.flat;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {
    public String id;
    public String roomType;
    public String location;
    public int price;
    public String description;

    public Room(String id, String roomType, String location, int price, String description) {
        this.id = id;
        this.roomType = roomType;
        this.location = location;
        this.price = price;
        this.description = description;
    }

    protected Room(Parcel in) {
        id = in.readString();
        roomType = in.readString();
        location = in.readString();
        price = in.readInt();
        description = in.readString();
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(roomType);
        parcel.writeString(location);
        parcel.writeInt(price);
        parcel.writeString(description);
    }
}
