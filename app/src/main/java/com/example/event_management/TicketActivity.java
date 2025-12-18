package com.example.event_management;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TicketActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        if (getSupportActionBar() != null) getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#121212"));

        ImageView ivEvent = findViewById(R.id.ivTicketEvent);
        ImageView imgQr = findViewById(R.id.imgTicketQr);
        TextView tvName = findViewById(R.id.tvTicketEventName);
        TextView tvPrice = findViewById(R.id.tvTicketPrice);
        TextView tvDate = findViewById(R.id.tvTicketDate);
        TextView tvStatus = findViewById(R.id.tvTicketStatus);
        Button btnSave = findViewById(R.id.btnBackToHome);

        // Receive Data
        String name = getIntent().getStringExtra("EVENT_NAME");
        String price = getIntent().getStringExtra("EVENT_PRICE");
        String date = getIntent().getStringExtra("EVENT_DATE");
        String status = getIntent().getStringExtra("BOOKING_STATUS");
        int img = getIntent().getIntExtra("EVENT_IMG", 0);

        // UI Binding
        tvName.setText(name);
        tvDate.setText("📅 " + (date != null ? date : "TBA"));
        tvStatus.setText("Status: " + status + " ✅");
        tvStatus.setTextColor(Color.GREEN);

        // 🔥 PRICE DYNAMIC LOGIC
        if (price != null && !price.isEmpty()) {
            tvPrice.setText("Paid: ₹" + price);
        } else {
            tvPrice.setText("Paid: ₹500 (Default)");
        }

        // 🔥 IMAGE DYNAMIC LOGIC
        if (img != 0) {
            ivEvent.setImageResource(img);
        } else {
            ivEvent.setImageResource(R.drawable.admin_logo); // Error check logo
        }

        imgQr.setImageResource(R.drawable.admin_logo); // QR Placeholder

        btnSave.setOnClickListener(v -> saveTicket(findViewById(R.id.ticketCard)));
    }

    private void saveTicket(View view) {
        try {
            view.setDrawingCacheEnabled(true);
            Bitmap b = Bitmap.createBitmap(view.getDrawingCache());
            MediaStore.Images.Media.insertImage(getContentResolver(), b, "Ticket_" + System.currentTimeMillis(), "Event Ticket");
            Toast.makeText(this, "Saved to Gallery! ✅", Toast.LENGTH_SHORT).show();
            view.setDrawingCacheEnabled(false);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}