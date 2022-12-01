package com.example.stickynotes.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import com.example.stickynotes.listeners.NoteListeners;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements NoteListeners {

    ImageView addNotes;
    public static final int REQUEST_CODE_AND_NOTE = 1;
    public static final int UPDATE_NOTE = 2;
    public static final int SHOW_NOTE = 3;

    private int clickedPosition = -1;

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
        addNotes.setOnClickListener(v -> startActivityForResult(intent, REQUEST_CODE_AND_NOTE));

        noteRec = view.findViewById(R.id.note_rec);
        noteRec.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        noteEntitiesList = new ArrayList<>();
        noteAdapter = new NoteAdapter(noteEntitiesList, this);
        noteRec.setAdapter(noteAdapter);

        getAllNotes(SHOW_NOTE);

        return view;
    }


    private void getAllNotes(final int requestCode) {

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

                if (requestCode == SHOW_NOTE) {
                    noteEntitiesList.addAll(noteEntities);
                    noteAdapter.notifyDataSetChanged();
                } else if (requestCode == UPDATE_NOTE){
                    noteEntitiesList.remove(clickedPosition);
                    noteEntitiesList.add(clickedPosition, noteEntities.get(clickedPosition));
                    noteAdapter.notifyItemChanged(clickedPosition);
                }
                else {
                    noteEntitiesList.add(0, noteEntities.get(0));
                    noteAdapter.notifyItemInserted(0);
                }

                noteRec.smoothScrollToPosition(0);
            }
        }
        new GetNoteTask().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_AND_NOTE && resultCode == RESULT_OK) {
            getAllNotes(REQUEST_CODE_AND_NOTE);
        } else if (requestCode == UPDATE_NOTE && resultCode == RESULT_OK) {
            if (data != null) {
                getAllNotes(UPDATE_NOTE);
            }
        }
    }

    @Override
    public void noteClick(NoteEntities noteEntities, int position) {

        clickedPosition = position;
        Intent intent = new Intent(getContext().getApplicationContext(), AddNewNoteActivity.class);
        intent.putExtra("updateOrView", true);
        intent.putExtra("notes", noteEntities);
        startActivityForResult(intent, UPDATE_NOTE);

    }
}