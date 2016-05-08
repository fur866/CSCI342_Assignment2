package com.example.fahad.assignment2.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.fahad.assignment2.Adapters.CollectionsAdapter;
import com.example.fahad.assignment2.Database.ConvenienceClasses.ScrapbookModel;
import com.example.fahad.assignment2.MainActivity;
import com.example.fahad.assignment2.R;

import java.util.ArrayList;

/**
 * Created by Fahad on 4/05/2016.
 */
public class CollectionListFragment extends Fragment{

    private ScrapbookModel scrapbookModel;
    private ArrayList<String> items;
    private CollectionsAdapter itemsAdapter;
    private MainActivity mCallback;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        this.scrapbookModel = new ScrapbookModel(getContext());
        this.items = getArguments().getStringArrayList("Collections");

        this.itemsAdapter = new CollectionsAdapter(getContext(),this.items,this);

        View view = inflater.inflate(R.layout.list,container,false);

        Button addbutton = (Button) view.findViewById(R.id.addButton);
        addbutton.setText("Add Collection");
        addbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addCollectionPopUp();
            }
        });


        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);

        return view;
    }


    public void addCollectionPopUp()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Add Collection");
        alert.setIcon(android.R.drawable.btn_plus);
        final EditText input = new EditText (getContext());
        input.setHint(R.string.add_collection_hint);
        alert.setView(input);

        alert.setPositiveButton("Add Collection", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scrapbookModel.createCollection(input.getText().toString());
                itemsAdapter.add(input.getText().toString());
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

    public void deleteCollectionPopup(final String toBeRemoved)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Are you sure?");
        alert.setIcon(android.R.drawable.btn_minus);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scrapbookModel.deleteCollection(toBeRemoved);
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

    public void gotToACollection(String name)
    {
        this.mCallback.showClippingsFragment(name);
    }

    public void isChanged()
    {
        this.itemsAdapter.notifyDataSetChanged();
    }
}