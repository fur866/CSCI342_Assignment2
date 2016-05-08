package com.example.fahad.assignment2.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.fahad.assignment2.Adapters.ClippingsAdapter;
import com.example.fahad.assignment2.Database.DataClasses.Clipping;
import com.example.fahad.assignment2.MainActivity;
import com.example.fahad.assignment2.R;

import java.util.ArrayList;

/**
 * Created by Fahad on 7/05/2016.
 */
public class ClippingListFragment extends Fragment{

    private ArrayList<Clipping> items;
    private String parentCollection;
    private ClippingsAdapter itemsAdapter;
    private MainActivity mCallback;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.itemsAdapter = null;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        this.items = (ArrayList<Clipping>) getArguments().getSerializable("Clippings");
        Log.d("Here",String.valueOf(this.items.size()));
        this.parentCollection = getArguments().getString("parentCollection");

        View view = inflater.inflate(R.layout.list,container,false);

        this.itemsAdapter = new ClippingsAdapter(getContext(), this.items, this);

        Button addbutton = (Button) view.findViewById(R.id.addButton);
        addbutton.setText("Add Clipping");
        addbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCallback.addClipping(parentCollection);
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);

        return view;
    }

    public void deleteClippingPopup(final Clipping toBeRemoved)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Are you sure?");
        alert.setIcon(android.R.drawable.btn_minus);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCallback.deleteClipping(toBeRemoved.getID());
                itemsAdapter.remove(toBeRemoved);
                itemsAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
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

    public void gotToAClipping(Clipping clipping)
    {
        this.mCallback.showClipping(clipping);
    }

    public String getParentCollection()
    {
        return this.parentCollection;
    }

    public void changeList(ArrayList<Clipping> clippings)
    {
        this.itemsAdapter.clear();
        this.itemsAdapter.addAll(clippings);
        this.itemsAdapter.notifyDataSetChanged();
    }
}
