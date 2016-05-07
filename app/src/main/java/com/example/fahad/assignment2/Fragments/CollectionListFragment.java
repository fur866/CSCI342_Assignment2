package com.example.fahad.assignment2.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.fahad.assignment2.Database.ConvenienceClasses.ScrapbookModel;
import com.example.fahad.assignment2.R;

import java.util.ArrayList;

/**
 * Created by Fahad on 4/05/2016.
 */
public class CollectionListFragment extends Fragment{

    private ScrapbookModel scrapbookModel;
    private ArrayAdapter<String> itemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        this.scrapbookModel = new ScrapbookModel(getContext());
        ArrayList<String> list = getArguments().getStringArrayList("Collections");
        this.itemsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row,list);
        View view = inflater.inflate(R.layout.collection_list,container,false);

        Button button = (Button) view.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
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
}