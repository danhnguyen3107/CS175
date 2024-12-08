package edu.sjsu.android.finalproject6;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InfoFragment extends Fragment {
    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        // On click listener for the phone number button
        view.findViewById(R.id.tel_btn).setOnClickListener(this::onClick);
        return view;
    }

    private void onClick(View v){
        // Create intent to dial phone number
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse(getString(R.string.tel_888_8888));
        intent.setData(data);
        startActivity(intent);
    }

}