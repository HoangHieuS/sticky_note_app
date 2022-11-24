package com.example.stickynotes.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.stickynotes.R;
import com.example.stickynotes.activities.AddNewNoteActivity;

public class HomeFragment extends Fragment {

    ImageView addNotes;
    ActivityResultLauncher<String> addNote;
    public static final int REQUEST_CODE_AND_NOTE = 1;

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
        addNotes.setOnClickListener(v -> startActivityForResult(intent,REQUEST_CODE_AND_NOTE));

        return view;
    }
}