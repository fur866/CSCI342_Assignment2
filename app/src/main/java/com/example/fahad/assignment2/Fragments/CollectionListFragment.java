package com.example.fahad.assignment2.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fahad.assignment2.R;

import java.util.ArrayList;

/**
 * Created by Fahad on 4/05/2016.
 */
public class CollectionListFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ArrayList<String> list = getArguments().getStringArrayList("Collections");
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,list);
        View view = inflater.inflate(R.layout.collection_list,container,false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);

        return view;
    }

}