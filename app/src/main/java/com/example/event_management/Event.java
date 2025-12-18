package com.example.event_management;

public class Event {
    private String id;
    private String name;
    private String date;
    private String time;
    private String location;
    private int imageResId;
    private String price; // 🔥 Price variable

    // 1. Empty Constructor (Firebase ke liye zaroori hai)
    public Event() { }

    // 2. Full Constructor (MainActivity aur Adapter ke liye)
    public Event(String id, String name, String date, String time, String location, int imageResId, String price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.imageResId = imageResId;
        this.price = price;
    }

    // --- Getters ---
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getLocation() { return location; }
    public int getImageResId() { return imageResId; }

    // 🔥 Ye method hona zaroori hai varna Adapter error dega
    public String getPrice() { return price; }

    // --- Setters (Optional but good for Firebase) ---
    public void setPrice(String price) { this.price = price; }
}