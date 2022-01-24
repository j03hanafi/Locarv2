package com.example.locarv2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.locarv2.R;

public class ListMobilFragment extends Fragment {

    private TextView textFragment;

    public ListMobilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_mobil, container, false);

        textFragment = view.findViewById(R.id.textView4);
        textFragment.setText("Test Fragment");

        return view;
    }
}