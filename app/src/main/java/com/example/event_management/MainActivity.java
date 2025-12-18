package com.example.event_management;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewUpcoming, recyclerViewTrending;
    EventAdapter adapterUpcoming, adapterTrending;
    ArrayList<Event> listUpcoming, listTrending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- AUTH CHECK ---
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        // --- USERNAME DISPLAY ---
        TextView tvUsername = findViewById(R.id.tvUsernameHome);
        if(user.getDisplayName() != null && !user.getDisplayName().isEmpty()) {
            tvUsername.setText("Welcome Back,\n" + user.getDisplayName() + " 👋");
        } else {
            tvUsername.setText("Welcome Back,\nUser 👋");
        }

        // --- CATEGORIES LOGIC ---
        findViewById(R.id.cardMusic).setOnClickListener(v -> openCategory("Music"));
        findViewById(R.id.cardWedding).setOnClickListener(v -> openCategory("Wedding"));
        findViewById(R.id.cardSports).setOnClickListener(v -> openCategory("Sports"));

        findViewById(R.id.tvSeeAllTrending).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, TrendingActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        // --- TRENDING EVENTS (HORIZONTAL) ---
        recyclerViewTrending = findViewById(R.id.recyclerViewTrending);
        recyclerViewTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        listTrending = new ArrayList<>();

        // 🔥 Yahan 7th parameter (Price) add kar diya hai
        listTrending.add(new Event("101", "IPL Hosting 2025", "20 April", "7:30 PM", "Mumbai", R.drawable.ipl_banner, "1500"));
        listTrending.add(new Event("102", "Sunburn Festival", "28 Dec", "4:00 PM", "Goa", R.drawable.sunburn_banner, "2500"));
        listTrending.add(new Event("103", "Fashion Week", "10 Jan", "6:00 PM", "Mumbai", R.drawable.fashion_banner, "0"));
        listTrending.add(new Event("104", "Food Fest", "15 Jan", "12:00 PM", "Delhi", R.drawable.food_banner, "200"));

        adapterTrending = new EventAdapter(this, listTrending);
        recyclerViewTrending.setAdapter(adapterTrending);

        // --- UPCOMING EVENTS (VERTICAL) ---
        recyclerViewUpcoming = findViewById(R.id.recyclerViewEvents);
        recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(this));
        listUpcoming = new ArrayList<>();

        // 🔥 Yahan bhi 7th parameter (Price) add kar diya hai
        listUpcoming.add(new Event("1", "Comedy Night", "25 Dec", "8:00 PM", "Mumbai", R.drawable.kapil_banner, "499"));
        listUpcoming.add(new Event("2", "Arijit Singh", "01 Jan", "7:30 PM", "Pune", R.drawable.arijit_banner, "1999"));
        listUpcoming.add(new Event("3", "India's Got Latent", "28 June", "7:30 PM", "Pune", R.drawable.latent_banner, "799"));

        adapterUpcoming = new EventAdapter(this, listUpcoming);
        recyclerViewUpcoming.setAdapter(adapterUpcoming);

        // --- BOTTOM NAVIGATION LOGIC ---
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            }
            else if (id == R.id.nav_categories) {
                startActivity(new Intent(MainActivity.this, CategoryActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }
            else if (id == R.id.nav_calendar) {
                startActivity(new Intent(MainActivity.this, MyBookingsActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }
            else if (id == R.id.nav_profile) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }
            return false;
        });
    }

    private void openCategory(String categoryName) {
        Intent intent = new Intent(MainActivity.this, CategoryDetailsActivity.class);
        intent.putExtra("CATEGORY_NAME", categoryName);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}