package com.example.event_management;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    Context context;
    ArrayList<Booking> list;
    ArrayList<String> keys;

    public AdminAdapter(Context context, ArrayList<Booking> list, ArrayList<String> keys) {
        this.context = context;
        this.list = list;
        this.keys = keys;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.admin_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list == null || list.size() <= position) return;

        Booking booking = list.get(position);
        if (booking == null) return;

        String bookingKey = keys.get(position);

        holder.eventName.setText(booking.getEventName() != null ? booking.getEventName() : "Unknown Event");

        // --- User Name Fix (Using Getter) ---
        String userDetails = "User: " + (booking.getUserName() != null ? booking.getUserName() : "Unknown");
        holder.userName.setText(userDetails);

        String currentStatus = booking.getStatus();
        if (currentStatus == null) currentStatus = "Pending";

        holder.status.setText("Status: " + currentStatus);

        // 🔥 COLOR LOGIC FIX: 'status' ko badal kar 'currentStatus' kar diya hai
        if (currentStatus.equalsIgnoreCase("Pending")) {
            holder.status.setTextColor(Color.parseColor("#FFA000")); // Orange
        } else if (currentStatus.equalsIgnoreCase("Confirmed")) {
            holder.status.setTextColor(Color.GREEN);
        } else if (currentStatus.equalsIgnoreCase("Rejected")) { // 👈 Yahan fix kiya hai
            holder.status.setTextColor(Color.RED);
        }

        holder.btnApprove.setOnClickListener(v -> updateStatus(bookingKey, "Confirmed"));
        holder.btnReject.setOnClickListener(v -> updateStatus(bookingKey, "Rejected"));
    }

    private void updateStatus(String key, String newStatus) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Bookings").child(key);
        db.child("status").setValue(newStatus).addOnSuccessListener(aVoid -> {
            Toast.makeText(context, "Booking " + newStatus, Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, userName, status;
        Button btnApprove, btnReject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.tvAdminEvent);
            userName = itemView.findViewById(R.id.tvAdminUser);
            status = itemView.findViewById(R.id.tvAdminStatus);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }
}