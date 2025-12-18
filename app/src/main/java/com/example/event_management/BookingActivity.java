package com.example.event_management;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class BookingActivity extends AppCompatActivity {
    DatabaseReference bookingDb;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        bookingDb = FirebaseDatabase.getInstance().getReference("Bookings");

        TextView tvEventName = findViewById(R.id.tvBookingEventName);
        EditText etName = findViewById(R.id.etYourName);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etSeats = findViewById(R.id.etSeats);
        Button btnConfirm = findViewById(R.id.btnConfirmBooking);
        Button btnCancel = findViewById(R.id.btnCancel);
        progressBar = findViewById(R.id.progressBarBooking);

        // Data Receiving from Details Activity
        String eventName = getIntent().getStringExtra("EVENT_NAME");
        String eventPrice = getIntent().getStringExtra("EVENT_PRICE");
        String eventDate = getIntent().getStringExtra("EVENT_DATE");
        int eventImg = getIntent().getIntExtra("EVENT_IMG", 0);

        if (eventName != null) tvEventName.setText("Booking for: " + eventName);

        btnConfirm.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String seats = etSeats.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || seats.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            String bookingId = bookingDb.push().getKey();
            String email = FirebaseAuth.getInstance().getCurrentUser() != null ?
                    FirebaseAuth.getInstance().getCurrentUser().getEmail() : "no-email";

            HashMap<String, Object> map = new HashMap<>();
            map.put("bookingId", bookingId);
            map.put("eventName", eventName);
            map.put("eventPrice", eventPrice);
            map.put("eventDate", eventDate);
            map.put("eventImg", eventImg);
            map.put("userName", name);
            map.put("phone", phone);
            map.put("seats", seats);
            map.put("status", "Pending");
            map.put("email", email);

            if (bookingId != null) {
                bookingDb.child(bookingId).setValue(map).addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Booking Successful! ✅", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }
}