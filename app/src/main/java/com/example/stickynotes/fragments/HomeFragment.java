package com.example.stickynotes.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.stickynotes.R;
import com.example.stickynotes.activities.AddNewNoteActivity;
import com.example.stickynotes.adapters.NoteAdapter;
import com.example.stickynotes.database.NoteDatabase;
import com.example.stickynotes.entities.NoteEntities;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ImageView addNotes;

    private RecyclerView noteRec;
    private List<NoteEntities> noteEntitiesList;
    private NoteAdapter noteAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Intent intent = new Intent(getContext(), AddNewNoteActivity.class);

        addNotes = view.findViewById(R.id.add_notes);
        addNotes.setOnClickListener(v -> mAddNotes.launch(intent));

        noteRec = view.findViewById(R.id.note_rec);
        noteRec.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        noteEntitiesList = new ArrayList<>();
        noteAdapter = new NoteAdapter(noteEntitiesList);
        noteRec.setAdapter(noteAdapter);

        getAllNotes();

        return view;
    }


    private void getAllNotes() {

        @SuppressLint("StaticFieldLeak")
        class GetNoteTask extends AsyncTask<Void, Void, List<NoteEntities>> {

            @Override
            protected List<NoteEntities> doInBackground(Void... voids) {
                return NoteDatabase.getNoteDatabase(getActivity().getApplicationContext())
                        .noteDao()
                        .getAllNotes();
            }

            @Override
            protected void onPostExecute(List<NoteEntities> noteEntities) {
                super.onPostExecute(noteEntities);
                if (noteEntitiesList.size() == 0) {
                    noteEntitiesList.addAll(noteEntities);
                    noteAdapter.notifyDataSetChanged();
                } else {
                    noteEntitiesList.add(0, noteEntities.get(0));
                    noteAdapter.notifyItemInserted(0);
                }

                noteRec.smoothScrollToPosition(0);
            }
        }
        new GetNoteTask().execute();
    }

    ActivityResultLauncher<Intent> mAddNotes = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    getAllNotes();
                }
            });
}