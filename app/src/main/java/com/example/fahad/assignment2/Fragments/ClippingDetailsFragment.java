package com.example.fahad.assignment2.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fahad.assignment2.Database.DataClasses.Clipping;
import com.example.fahad.assignment2.R;

/**
 * Created by Fahad on 8/05/2016.
 */
public class ClippingDetailsFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        Clipping item = (Clipping) getArguments().getSerializable("Clippings");

        View view = inflater.inflate(R.layout.clipping_details,container,false);

        TextView textView = (TextView) view.findViewById(R.id.notes);
        textView.setText(item.getNotes());

        ImageView imageView = (ImageView) view.findViewById(R.id.imageClipping);
        Drawable image = item.getImage();
        if(image != null) {
            imageView.setImageDrawable(image);
        }

        return view;
    }
}
