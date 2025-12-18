package com.example.event_management;

public class Booking {
    public String bookingId, eventName, eventPrice, eventDate, userName, phone, seats, status, email;
    public int eventImg;

    // Default constructor (Firebase ke liye compulsory hai)
    public Booking() {}

    public Booking(String bookingId, String eventName, String eventPrice, String eventDate, int eventImg, String userName, String phone, String seats, String status, String email) {
        this.bookingId = bookingId;
        this.eventName = eventName;
        this.eventPrice = eventPrice;
        this.eventDate = eventDate;
        this.eventImg = eventImg;
        this.userName = userName;
        this.phone = phone;
        this.seats = seats;
        this.status = status;
        this.email = email;
    }

    // Saare Getters ek saath (Isme galti nahi honi chahiye)
    public String getBookingId() { return bookingId; }
    public String getEventName() { return eventName; }
    public String getEventPrice() { return eventPrice; }
    public String getEventDate() { return eventDate; }
    public int getEventImg() { return eventImg; }
    public String getUserName() { return userName; }
    public String getPhone() { return phone; }
    public String getSeats() { return seats; }
    public String getStatus() { return status; }
    public String getEmail() { return email; }
}