package com.example.event_management;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    Context context;
    ArrayList<Booking> list;

    public BookingAdapter(Context context, ArrayList<Booking> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.booking_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking b = list.get(position);
        holder.name.setText(b.getEventName());
        holder.status.setText(b.getStatus());

        // Status Colors
        if (b.getStatus().equalsIgnoreCase("Confirmed")) {
            holder.status.setTextColor(Color.GREEN);
        } else if (b.getStatus().equalsIgnoreCase("Rejected")) {
            holder.status.setTextColor(Color.RED);
        } else {
            holder.status.setTextColor(Color.parseColor("#FFA000")); // Orange
        }

        holder.itemView.setOnClickListener(v -> {
            if (b.getStatus().equalsIgnoreCase("Confirmed")) {
                Intent i = new Intent(context, TicketActivity.class);
                i.putExtra("EVENT_NAME", b.getEventName());
                i.putExtra("EVENT_PRICE", b.getEventPrice());
                i.putExtra("EVENT_DATE", b.getEventDate());
                i.putExtra("EVENT_IMG", b.getEventImg());
                i.putExtra("BOOKING_STATUS", b.getStatus());
                context.startActivity(i);
            } else {
                Toast.makeText(context, "Ticket available after Admin approval!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, status;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvBookedEvent);
            status = itemView.findViewById(R.id.tvBookedStatus);
        }
    }
}