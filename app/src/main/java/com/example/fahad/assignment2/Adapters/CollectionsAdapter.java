package com.example.fahad.assignment2.Adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.fahad.assignment2.Fragments.CollectionListFragment;
import com.example.fahad.assignment2.MainActivity;
import com.example.fahad.assignment2.R;

import java.util.ArrayList;

/**
 * Created by Fahad on 7/05/2016.
 */
public class CollectionsAdapter extends ArrayAdapter<String> {

    private Context context;
    private CollectionListFragment fragementList;

    public CollectionsAdapter(Context context, ArrayList<String> list,CollectionListFragment fragementList)
    {
        super(context,-1,list);
        this.context = context;
        this.fragementList = fragementList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView first;
        Button button;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
            first = (TextView) convertView.findViewById(android.R.id.text1);
            button = (Button) convertView.findViewById(R.id.deleteCollectionButton);
            convertView.setTag(new ViewHolder(first,button));
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            first = viewHolder.name;
            button = viewHolder.button;
        }

        final String collectionName = getItem(position);
        first.setText(collectionName);
        first.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fragementList.gotToACollection(collectionName);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fragementList.deleteCollectionPopup(collectionName);
            }
        });

        return convertView;
    }

    private static class ViewHolder
    {
        public final TextView name;
        public final Button button;

        public ViewHolder(TextView name, Button button)
        {
            this.name = name;
            this.button = button;
        }
    }
}
