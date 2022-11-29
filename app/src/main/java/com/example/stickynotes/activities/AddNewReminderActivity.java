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
import com.example.stickynotes.entities.ReminderEntities;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNewReminderActivity extends AppCompatActivity {

    private EditText reminderTitle, reminderText;
    private TextView textDateTime, saveReminder;
    private View reminderView;
    private String selectReminderColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_reminder);

        saveReminder = findViewById(R.id.save_reminder);
        reminderTitle = findViewById(R.id.input_reminder_title);
        reminderText = findViewById(R.id.input_reminder_text);
        textDateTime = findViewById(R.id.text_date_time);
        reminderView = findViewById(R.id.view_reminder);

        selectReminderColor = "#FF937B";

        saveReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveReminder();
            }
        });

        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );

        bottomSheet();
        setViewColor();

    }

    private void saveReminder() {

        if (reminderTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note Title Can't Be Empty", Toast.LENGTH_SHORT).show();
        }

        final ReminderEntities reminderEntities = new ReminderEntities();
        reminderEntities.setTitle(reminderTitle.getText().toString());
        reminderEntities.setReminderText(reminderText.getText().toString());
        reminderEntities.setDateTime(textDateTime.getText().toString());
        reminderEntities.setColor(selectReminderColor);

        class SaveReminder extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                NoteDatabase.getNoteDatabase(getApplicationContext())
                        .noteDao()
                        .insertReminder(reminderEntities);
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
        new SaveReminder().execute();
    }

    private void bottomSheet() {

        final LinearLayout linearLayout = findViewById(R.id.bottom_sheet_reminder);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        linearLayout.findViewById(R.id.bottom_sheet_reminder_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

        final ImageView imgColor1 = linearLayout.findViewById(R.id.imageColor1);
        final ImageView imgColor2 = linearLayout.findViewById(R.id.imageColor2);
        final ImageView imgColor3 = linearLayout.findViewById(R.id.imageColor3);
        final ImageView imgColor4 = linearLayout.findViewById(R.id.imageColor4);


        linearLayout.findViewById(R.id.viewColor1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectReminderColor = "#FF937B";
                imgColor1.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                setViewColor();
            }
        });

        linearLayout.findViewById(R.id.viewColor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectReminderColor = "#FFFB7B";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                setViewColor();
            }
        });

        linearLayout.findViewById(R.id.viewColor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectReminderColor = "#ADFF7B";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor4.setImageResource(0);
                setViewColor();
            }
        });

        linearLayout.findViewById(R.id.viewColor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectReminderColor = "#96FFEA";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(R.drawable.ic_baseline_done_24);
                setViewColor();
            }
        });
    }

    private void setViewColor() {
        GradientDrawable gradientDrawable = (GradientDrawable) reminderView.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectReminderColor));

    }
}