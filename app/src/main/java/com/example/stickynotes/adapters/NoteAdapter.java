package com.example.stickynotes.adapters;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Looper;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stickynotes.R;
import com.example.stickynotes.entities.NoteEntities;
import com.example.stickynotes.listeners.NoteListeners;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<NoteEntities> noteEntitiesList;
    NoteListeners noteListeners;

    private List<NoteEntities> noteSearch;
    private Timer timer;

    public NoteAdapter(List<NoteEntities> noteEntitiesList, NoteListeners noteListeners) {
        this.noteEntitiesList = noteEntitiesList;
        this.noteListeners = noteListeners;
        noteSearch = noteEntitiesList;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {

        holder.setNote(noteEntitiesList.get(position));
        holder.linearLayout.setOnClickListener(view -> noteListeners
                .noteClick(noteEntitiesList
                        .get(holder.getAdapterPosition()), holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return noteEntitiesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title, textNote, dateTime;
        private final LinearLayout linearLayout;
        RoundedImageView roundedImageView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.text_title);
            textNote = itemView.findViewById(R.id.text_note);
            dateTime = itemView.findViewById(R.id.text_date_time);
            linearLayout = itemView.findViewById(R.id.layout_note);
            roundedImageView = itemView.findViewById(R.id.image_note_item);
        }

        public void setNote(NoteEntities noteEntities){
            title.setText(noteEntities.getTitle());
            textNote.setText(noteEntities.getNoteText());
            dateTime.setText(noteEntities.getDateTime());

            GradientDrawable gradientDrawable = (GradientDrawable) linearLayout.getBackground();
            if(noteEntities.getColor() != null) {
                gradientDrawable.setColor(Color.parseColor(noteEntities.getColor()));
            }
            else {
                gradientDrawable.setColor(Color.parseColor("#FF937B"));
            }
            if (noteEntities.getImageUrl() != null){
                roundedImageView.setImageBitmap(BitmapFactory.decodeFile(noteEntities.getImageUrl()));
                roundedImageView.setVisibility(View.VISIBLE);
            } else {
                roundedImageView.setVisibility(View.GONE);
            }
        }
    }

    public void searchNote(final String searchKeyword) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (searchKeyword.trim().isEmpty()) {
                    noteEntitiesList = noteSearch;
                } else {
                    ArrayList<NoteEntities> temp = new ArrayList<>();
                    for (NoteEntities entities : noteSearch) {

                        if (entities.getTitle().toLowerCase().contains(searchKeyword.toLowerCase())
                        || entities.getNoteText().toLowerCase().contains(searchKeyword.toLowerCase())) {

                            temp.add(entities);

                        }
                    }
                    noteEntitiesList = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });

            }
        },500);
    }

    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
