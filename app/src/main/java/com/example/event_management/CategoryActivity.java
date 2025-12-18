package com.example.event_management;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // --- CLICK LISTENERS (MUSIC, WEDDING, SPORTS) ---
        findViewById(R.id.catMusic).setOnClickListener(v -> openCategoryDetails("Music"));
        findViewById(R.id.catWedding).setOnClickListener(v -> openCategoryDetails("Wedding"));
        findViewById(R.id.catSports).setOnClickListener(v -> openCategoryDetails("Sports"));

        // --- TRENDING BUTTON ---
        findViewById(R.id.catTrending).setOnClickListener(v -> {
            startActivity(new Intent(CategoryActivity.this, TrendingActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        // --- BACK BUTTON ---
        Button btnBack = findViewById(R.id.btnBackCategory);
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(CategoryActivity.this, MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        // --- BOTTOM NAVIGATION LOGIC ---
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setSelectedItemId(R.id.nav_categories); // Category highlight hona chahiye

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(CategoryActivity.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                return true;
            }
            else if (id == R.id.nav_categories) {
                return true; // Already here
            }
            else if (id == R.id.nav_calendar) {
                startActivity(new Intent(CategoryActivity.this, MyBookingsActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                return true;
            }
            else if (id == R.id.nav_profile) {
                startActivity(new Intent(CategoryActivity.this, ProfileActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                return true;
            }
            return false;
        });

    } // onCreate ends

    private void openCategoryDetails(String categoryName) {
        Intent intent = new Intent(CategoryActivity.this, CategoryDetailsActivity.class);
        intent.putExtra("CATEGORY_NAME", categoryName);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}