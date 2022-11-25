package com.example.stickynotes.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stickynotes.R;
import com.example.stickynotes.entities.NoteEntities;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<NoteEntities> noteEntitiesList;

    public NoteAdapter(List<NoteEntities> noteEntitiesList) {
        this.noteEntitiesList = noteEntitiesList;
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

        private TextView title, textNote, dateTime;
        private LinearLayout linearLayout;
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
        }
    }
}
