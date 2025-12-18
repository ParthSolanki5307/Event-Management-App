package com.example.event_management;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OrganizerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer);

        // 1. UI Elements ko link karo
        ImageView imgOrg = findViewById(R.id.imgOrganizer);
        Button btnCall = findViewById(R.id.btnCall);
        Button btnMsg = findViewById(R.id.btnMessage);
        Button btnBack = findViewById(R.id.btnBackOrg);

        // 2. 🔥 LOGO/IMAGE LOGIC
        // Pehle check karo kya EventDetailsActivity se koi image aayi hai?
        int eventImgId = getIntent().getIntExtra("EVENT_IMG", 0);

        if (eventImgId != 0) {
            // Agar event ki photo hai toh wo dikhao
            imgOrg.setImageResource(eventImgId);
        } else {
            // Agar nahi hai, toh apna admin_logo dikhao
            imgOrg.setImageResource(R.drawable.admin_logo);
        }

        // 3. Click Listeners
        btnCall.setOnClickListener(v -> {
            Toast.makeText(this, "Calling Organizer: +91 6354771762", Toast.LENGTH_SHORT).show();
            // Yahan chaho toh Intent.ACTION_DIAL bhi daal sakte ho
        });

        btnMsg.setOnClickListener(v -> {
            Toast.makeText(this, "Opening WhatsApp...", Toast.LENGTH_SHORT).show();
        });

        btnBack.setOnClickListener(v -> finish());
    }
}