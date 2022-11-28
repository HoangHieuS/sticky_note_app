package com.example.stickynotes.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stickynotes.R;
import com.example.stickynotes.entities.ReminderEntities;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    List<ReminderEntities> reminderEntities;

    public ReminderAdapter(List<ReminderEntities> reminderEntities) {
        this.reminderEntities = reminderEntities;
    }
    @NonNull
    @Override
    public ReminderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.ViewHolder holder, int position) {

        holder.setReminder(reminderEntities.get(position));
    }

    @Override
    public int getItemCount() {
        return reminderEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, dateTime;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.reminder_heading);
            dateTime = itemView.findViewById(R.id.date_reminder);
            view = itemView.findViewById(R.id.view_reminder);

        }

        public void setReminder(ReminderEntities reminderEntities) {

            title.setText(reminderEntities.getTitle());
            dateTime.setText(reminderEntities.getDateTime());

            GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
            if(reminderEntities.getColor() != null) {
                gradientDrawable.setColor(Color.parseColor(reminderEntities.getColor()));
            }
            else {
                gradientDrawable.setColor(Color.parseColor("#FFFB7B"));
            }

        }
    }
}
