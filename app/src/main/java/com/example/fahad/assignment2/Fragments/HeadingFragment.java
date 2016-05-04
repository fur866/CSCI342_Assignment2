package com.example.fahad.assignment2.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fahad.assignment2.R;

/**
 * Created by Fahad on 4/05/2016.
 */
public class HeadingFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.heading_fragment,container,false);
        TextView textView = (TextView) view.findViewById(R.id.heading);
        textView.setText("Collections");

        return view;
    }

}
