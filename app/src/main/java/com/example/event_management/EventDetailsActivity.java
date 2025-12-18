package com.example.event_management;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // --- 1. ID BINDING ---
        ImageView imgEvent = findViewById(R.id.imgEventDetail);
        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvPrice = findViewById(R.id.tvDetailPrice);
        TextView tvDate = findViewById(R.id.tvDetailDate);
        TextView tvLocation = findViewById(R.id.tvDetailLocation);

        Button btnContact = findViewById(R.id.btnContactOrg);
        Button btnBook = findViewById(R.id.btnBookEvent);
        Button btnBack = findViewById(R.id.btnBackDetail);

        // --- 2. DATA RECEIVING (Intent se data uthao) ---
        String name = getIntent().getStringExtra("EVENT_NAME");
        String date = getIntent().getStringExtra("EVENT_DATE");
        String time = getIntent().getStringExtra("EVENT_TIME");
        String loc = getIntent().getStringExtra("EVENT_LOC");
        String price = getIntent().getStringExtra("EVENT_PRICE");
        int imageId = getIntent().getIntExtra("EVENT_IMG", 0);

        // --- 3. UI SETTING (Dynamic values set karo) ---
        tvName.setText(name);
        tvDate.setText("📅 " + date + " | 🕒 " + time);
        tvLocation.setText("📍 " + loc);

        // Price check
        if (price != null && !price.isEmpty()) {
            tvPrice.setText("Price: ₹ " + price);
        } else {
            tvPrice.setText("Price: ₹ 500 onwards");
        }

        // Image set (Kapil wala locha yahan fix hoga)
        if (imageId != 0) {
            imgEvent.setImageResource(imageId);
        }

        // --- 4. BUTTON LOGIC ---

        // Contact Button
        btnContact.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, OrganizerActivity.class);
            startActivity(intent);
        });

        // 🔥 BOOK BUTTON (Yahan se saara data aage bhejna zaroori hai)
        btnBook.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, BookingActivity.class);
            intent.putExtra("EVENT_NAME", name);
            intent.putExtra("EVENT_PRICE", price); // Ye bhejoge tabhi Ticket mein dikhega
            intent.putExtra("EVENT_DATE", date);   // Ye bhi bhej do
            intent.putExtra("EVENT_IMG", imageId); // Ye image ticket tak jayegi
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> finish());
    }
}