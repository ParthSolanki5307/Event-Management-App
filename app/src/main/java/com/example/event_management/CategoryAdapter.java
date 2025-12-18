package com.example.event_management;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatViewHolder> {

    Context context;
    ArrayList<Event> list;

    public CategoryAdapter(Context context, ArrayList<Event> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new CatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        Event event = list.get(position);

        holder.title.setText(event.getName());
        holder.price.setText(event.getDate());

        // Image Logic
        String name = event.getName();
        if (name.contains("Sunburn")) holder.img.setImageResource(R.drawable.sunburn_img);
        else if (name.contains("Arijit")) holder.img.setImageResource(R.drawable.arijit_img);
        else if (name.contains("Coldplay")) holder.img.setImageResource(R.drawable.coldplay_img);
        else if (name.contains("Royal")) holder.img.setImageResource(R.drawable.wedding_royal_img);
        else if (name.contains("Beach")) holder.img.setImageResource(R.drawable.wedding_beach_img);
        else if (name.contains("IPL")) holder.img.setImageResource(R.drawable.ipl_img);
        else if (name.contains("Kabaddi")) holder.img.setImageResource(R.drawable.kabaddi_img);
        else holder.img.setImageResource(R.drawable.kapil_banner);

        // 🔥 CLICK LISTENERS FOR CARD BUTTONS

        // 1. Book Now Button
        holder.btnBook.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookingActivity.class);
            // Specific Event ka naam bhej rahe hain
            intent.putExtra("EVENT_NAME", event.getName());
            context.startActivity(intent);
        });

        // 2. Connect Button
        holder.btnConnect.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrganizerActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CatViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        ImageView img;
        Button btnBook, btnConnect; // 🔥 Buttons wapas aa gaye

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvDesignName);
            price = itemView.findViewById(R.id.tvDesignPrice);
            img = itemView.findViewById(R.id.imgCatDesign);

            // XML IDs se connect kar rahe hain
            btnBook = itemView.findViewById(R.id.btnItemBook);
            btnConnect = itemView.findViewById(R.id.btnItemConnect);
        }
    }
}