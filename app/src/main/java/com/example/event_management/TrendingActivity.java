package com.example.event_management;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TrendingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EventAdapter adapter;
    ArrayList<Event> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        Button btnBack = findViewById(R.id.btnBackTrending);
        btnBack.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerAllTrending);
        // Grid Layout (2 Column)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        list = new ArrayList<>();

        // 🔥 FIX: Har event ke aakhir mein Price (7th parameter) add kar diya hai

        // 1. IPL
        list.add(new Event("101", "IPL Final", "20 April", "7:30 PM", "Mumbai", R.drawable.ipl_banner, "1500"));

        // 2. Sunburn
        list.add(new Event("102", "Sunburn", "28 Dec", "4:00 PM", "Goa", R.drawable.sunburn_banner, "2500"));

        // 3. Fashion Week
        list.add(new Event("103", "Fashion Week", "10 Jan", "6:00 PM", "Mumbai", R.drawable.fashion_banner, "3000"));

        // 4. Food Fest
        list.add(new Event("104", "Food Fest", "15 Jan", "12:00 PM", "Delhi", R.drawable.food_banner, "200"));

        // 5. Gaming
        list.add(new Event("105", "Gaming War", "22 Jan", "8:00 PM", "Online", R.drawable.war, "500"));

        // 6. Tech Summit
        list.add(new Event("106", "Tech Summit", "05 Feb", "10:00 AM", "Bangalore", R.drawable.techsummit, "1000"));

        // 7. Auto Expo
        list.add(new Event("107", "Auto Expo", "12 Feb", "11:00 AM", "Noida", R.drawable.motoexpo, "300"));

        // 8. Pune Event
        list.add(new Event("108", "Lavari Show", "14 Feb", "9:00 PM", "Pune", R.drawable.lavari, "799"));

        adapter = new EventAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
}