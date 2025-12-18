package com.example.event_management;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdminAdapter adapter;
    ArrayList<Booking> list;
    ArrayList<String> keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // UI Setup
        recyclerView = findViewById(R.id.recyclerAdmin);
        Button btnBack = findViewById(R.id.btnBackAdmin);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lists Initialize
        list = new ArrayList<>();
        keys = new ArrayList<>();

        // Adapter Attach
        adapter = new AdminAdapter(this, list, keys);
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        // Firebase Load
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Bookings");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                keys.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    try {
                        Booking booking = data.getValue(Booking.class);
                        if(booking != null) {
                            list.add(booking);
                            keys.add(data.getKey());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}