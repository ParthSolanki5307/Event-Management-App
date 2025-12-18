package com.example.event_management;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    Context context;
    ArrayList<Event> list;

    public EventAdapter(Context context, ArrayList<Event> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        Event event = list.get(position);

        holder.name.setText(event.getName());
        holder.date.setText(event.getDate() + " | " + event.getTime());
        holder.location.setText(event.getLocation());

        // Photo set karna
        if (event.getImageResId() != 0) {
            holder.image.setImageResource(event.getImageResId());
        } else {
            holder.image.setImageResource(R.drawable.music); // Default image
        }

        // --- CLICK LISTENER: Event Details Activity khulegi ---
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventDetailsActivity.class);

            intent.putExtra("EVENT_NAME", event.getName());
            intent.putExtra("EVENT_DATE", event.getDate());
            intent.putExtra("EVENT_TIME", event.getTime());
            intent.putExtra("EVENT_LOC", event.getLocation());
            intent.putExtra("EVENT_IMG", event.getImageResId());
            intent.putExtra("EVENT_PRICE", event.getPrice());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, location;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvEventName);
            date = itemView.findViewById(R.id.tvEventDate);
            location = itemView.findViewById(R.id.tvEventLocation);
            image = itemView.findViewById(R.id.imgEventPlaceholder);
        }
    }
}