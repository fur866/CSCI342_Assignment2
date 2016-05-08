package com.example.fahad.assignment2.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.fahad.assignment2.MainActivity;
import com.example.fahad.assignment2.R;

/**
 * Created by Fahad on 8/05/2016.
 */
public class SearchFragment extends Fragment{

    private MainActivity mCallback;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.search,container,false);

        final EditText editText = (EditText) view.findViewById(R.id.searchView);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {

                Log.d("here",String.valueOf(arg1));
                if(arg2.getAction() == KeyEvent.ACTION_UP) {
                    mCallback.searchClippings(editText.getText().toString());
                    return  true;
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            this.mCallback = (MainActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
