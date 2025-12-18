package com.example.event_management;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class CategoryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        TextView tvTitle = findViewById(R.id.tvCategoryTitle);
        RecyclerView recyclerView = findViewById(R.id.recyclerCategory);
        ImageButton btnBack = findViewById(R.id.btnBack);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String category = getIntent().getStringExtra("CATEGORY_NAME");
        if(category != null) tvTitle.setText(category + " Events");

        ArrayList<Event> list = new ArrayList<>();

        // 🔥 Yahan humne 7th parameter (Price) add kar diya hai
        if (category != null) {
            if (category.equals("Music")) {
                list.add(new Event("1", "Sunburn Festival", "28 Dec", "4:00 PM", "Goa", R.drawable.sunburn_img, "2500"));
                list.add(new Event("2", "Arijit Singh Live", "01 Jan", "7:30 PM", "Pune", R.drawable.music, "1800"));
                list.add(new Event("3", "Coldplay Concert", "15 Jan", "8:00 PM", "Mumbai", R.drawable.coldplay_img, "5000"));
            }
            else if (category.equals("Sports")) {
                list.add(new Event("4", "IPL Final Match", "20 April", "7:30 PM", "Mumbai", R.drawable.ipl_banner, "1200"));
                list.add(new Event("5", "Pro Kabaddi", "22 Jan", "8:00 PM", "Online", R.drawable.kabaddi_img, "400"));
            }
            else if (category.equals("Wedding")) {
                list.add(new Event("6", "Royal Wedding Expo", "10 Jan", "6:00 PM", "Mumbai", R.drawable.wedding_royal_img, "10L"));
                list.add(new Event("7", "Beach Destination", "15 Jan", "12:00 PM", "Goa", R.drawable.wedding_beach_img, "5L"));
            }
        }

        EventAdapter adapter = new EventAdapter(this, list);
        recyclerView.setAdapter(adapter);

        // --- BACK BUTTON ---
        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        // --- BOTTOM NAVIGATION ---
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setSelectedItemId(R.id.nav_categories);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                return true;
            } else if (id == R.id.nav_categories) {
                return true;
            } else if (id == R.id.nav_calendar) {
                startActivity(new Intent(this, MyBookingsActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                return true;
            }
            return false;
        });
    }
}