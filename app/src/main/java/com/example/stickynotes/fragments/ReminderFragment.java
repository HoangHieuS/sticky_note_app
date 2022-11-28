package com.example.stickynotes.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.stickynotes.R;
import com.example.stickynotes.activities.AddNewNoteActivity;
import com.example.stickynotes.activities.AddNewReminderActivity;
import com.example.stickynotes.adapters.NoteAdapter;
import com.example.stickynotes.adapters.ReminderAdapter;
import com.example.stickynotes.database.NoteDatabase;
import com.example.stickynotes.entities.NoteEntities;
import com.example.stickynotes.entities.ReminderEntities;

import java.util.ArrayList;
import java.util.List;


public class ReminderFragment extends Fragment {

    ImageView addReminders;

    private RecyclerView reminderRec;
    private List<ReminderEntities> reminderEntitiesList;
    private ReminderAdapter reminderAdapter;

    public ReminderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        Intent intent = new Intent(getContext(), AddNewReminderActivity.class);

        addReminders = view.findViewById(R.id.add_reminder);
        addReminders.setOnClickListener(v -> mAddReminder.launch(intent));

        reminderRec = view.findViewById(R.id.reminder_rec);
        reminderRec.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        reminderEntitiesList = new ArrayList<>();
        reminderAdapter = new ReminderAdapter(reminderEntitiesList);
        reminderRec.setAdapter(reminderAdapter);

        getAllReminders();

        return view;
    }

    private void getAllReminders() {

        @SuppressLint("StaticFieldLeak")
        class GetAllReminder extends AsyncTask<Void, Void, List<ReminderEntities>> {

            @Override
            protected List<ReminderEntities> doInBackground(Void... voids) {
                return NoteDatabase.getNoteDatabase(getActivity().getApplicationContext())
                        .noteDao()
                        .getAllReminders();
            }

            @Override
            protected void onPostExecute(List<ReminderEntities> reminderEntities) {
                super.onPostExecute(reminderEntities);
                if (reminderEntitiesList.size() == 0) {
                    reminderEntitiesList.addAll(reminderEntities);
                    reminderAdapter.notifyDataSetChanged();
                } else {
                    reminderEntitiesList.add(0, reminderEntities.get(0));
                    reminderAdapter.notifyItemInserted(0);
                }

                reminderRec.smoothScrollToPosition(0);
            }
        }
        new GetAllReminder().execute();
    }

    ActivityResultLauncher<Intent> mAddReminder = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    getAllReminders();
                }
            });
}