package com.example.stickynotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stickynotes.R;
import com.example.stickynotes.database.NoteDatabase;
import com.example.stickynotes.entities.NoteEntities;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNewNoteActivity extends AppCompatActivity {

    private EditText inputNoteTitle, inputNoteText;
    private TextView textDateTime, saveNote;
    private View leftIndicator, rightIndicator;
    String selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        leftIndicator = findViewById(R.id.view_indicator_left);
        rightIndicator = findViewById(R.id.view_indicator_right);
        saveNote = findViewById(R.id.save_note);
        inputNoteText = findViewById(R.id.input_note_text);
        inputNoteTitle = findViewById(R.id.input_note_title);
        textDateTime = findViewById(R.id.text_date_time);

        selectedColor = "#FF937B";

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );

        bottomSheet();
        setViewColor();
    }

    private void setViewColor() {
        GradientDrawable gradientDrawable = (GradientDrawable) leftIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedColor));

        GradientDrawable gradientDrawable2 = (GradientDrawable) rightIndicator.getBackground();
        gradientDrawable2.setColor(Color.parseColor(selectedColor));
    }

    private void saveNote() {

        if (inputNoteTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note Title Can't Be Empty", Toast.LENGTH_SHORT).show();
        } else if (inputNoteText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note Text Can't Be Empty", Toast.LENGTH_SHORT).show();
        }

        final NoteEntities noteEntities = new NoteEntities();
        noteEntities.setTitle(inputNoteTitle.getText().toString());
        noteEntities.setNoteText(inputNoteText.getText().toString());
        noteEntities.setDateTime(textDateTime.getText().toString());
        noteEntities.setColor(selectedColor);

        class SaveNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                NoteDatabase.getNoteDatabase(getApplicationContext())
                        .noteDao()
                        .insertNote(noteEntities);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        new SaveNote().execute();
    }

    private void bottomSheet(){

        final LinearLayout linearLayout = findViewById(R.id.bottom_sheet_layout);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        linearLayout.findViewById(R.id.bottom_sheet_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED ){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

        final ImageView imgColor1 = linearLayout.findViewById(R.id.imageColor1);
        final ImageView imgColor2 = linearLayout.findViewById(R.id.imageColor2);
        final ImageView imgColor3 = linearLayout.findViewById(R.id.imageColor3);
        final ImageView imgColor4 = linearLayout.findViewById(R.id.imageColor4);
        final ImageView imgColor5 = linearLayout.findViewById(R.id.imageColor5);
        final ImageView imgColor6 = linearLayout.findViewById(R.id.imageColor6);

        linearLayout.findViewById(R.id.viewColor1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor = "#FF937B";
                imgColor1.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                imgColor5.setImageResource(0);
                imgColor6.setImageResource(0);
                setViewColor();
            }
        });

        linearLayout.findViewById(R.id.viewColor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor = "#FFFB7B";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                imgColor5.setImageResource(0);
                imgColor6.setImageResource(0);
                setViewColor();
            }
        });

        linearLayout.findViewById(R.id.viewColor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor = "#ADFF7B";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor4.setImageResource(0);
                imgColor5.setImageResource(0);
                imgColor6.setImageResource(0);
                setViewColor();
            }
        });

        linearLayout.findViewById(R.id.viewColor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor = "#96FFEA";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor5.setImageResource(0);
                imgColor6.setImageResource(0);
                setViewColor();
            }
        });

        linearLayout.findViewById(R.id.viewColor5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor = "#969CFF";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                imgColor5.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor6.setImageResource(0);
                setViewColor();
            }
        });

        linearLayout.findViewById(R.id.viewColor6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedColor = "#FF96F5";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                imgColor5.setImageResource(0);
                imgColor6.setImageResource(R.drawable.ic_baseline_done_24);
                setViewColor();
            }
        });

    }
}