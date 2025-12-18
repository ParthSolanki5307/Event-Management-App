package com.example.event_management;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class MyBookingsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BookingAdapter adapter;
    ArrayList<Booking> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_my_bookings);

            recyclerView = findViewById(R.id.recyclerMyBookings);
            ImageButton btnBack = findViewById(R.id.btnBackMyBookings);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            list = new ArrayList<>();
            adapter = new BookingAdapter(this, list);
            recyclerView.setAdapter(adapter);

            if (btnBack != null) {
                btnBack.setOnClickListener(v -> finish());
            }

            // --- BOTTOM NAVIGATION ---
            BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
            bottomNav.setSelectedItemId(R.id.nav_calendar);

            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_categories) {
                    startActivity(new Intent(this, CategoryActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_calendar) {
                    return true;
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(this, ProfileActivity.class));
                    finish();
                    return true;
                }
                return false;
            });

            // --- FIREBASE LOGIC ---
            String myEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Bookings");
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Booking booking = data.getValue(Booking.class);
                        if (booking != null && booking.getEmail() != null && booking.getEmail().equals(myEmail)) {
                            list.add(booking);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });

        } catch (Exception e) {
            new AlertDialog.Builder(this).setMessage(e.getMessage()).show();
        }
    }
}