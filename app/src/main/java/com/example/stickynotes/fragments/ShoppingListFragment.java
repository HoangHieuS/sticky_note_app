package com.example.stickynotes.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.stickynotes.R;
import com.example.stickynotes.activities.AddNewShopListActivity;


public class ShoppingListFragment extends Fragment {

    ImageView addShopList;
    public static final int REQUEST_CODE_AND_NOTE = 1;

    public ShoppingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        Intent intent = new Intent(getContext(), AddNewShopListActivity.class);

        addShopList = view.findViewById(R.id.add_shopping_list);
        addShopList.setOnClickListener(v -> startActivityForResult(intent,REQUEST_CODE_AND_NOTE));

        return view;
    }
}