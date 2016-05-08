package com.example.fahad.assignment2.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fahad.assignment2.Database.DataClasses.Clipping;
import com.example.fahad.assignment2.Fragments.ClippingListFragment;
import com.example.fahad.assignment2.R;

import java.util.ArrayList;

/**
 * Created by Fahad on 8/05/2016.
 */
public class ClippingsAdapter extends ArrayAdapter<Clipping>{

    private Context context;
    private ClippingListFragment fragementList;

    public ClippingsAdapter(Context context, ArrayList<Clipping> list, ClippingListFragment fragementList)
    {
        super(context,-1,list);
        this.context = context;
        this.fragementList = fragementList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView first;
        ImageView imageView;
        Button button;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.clippings_row, parent, false);
            first = (TextView) convertView.findViewById(android.R.id.text1);
            imageView = (ImageView) convertView.findViewById(R.id.clippingImage);
            button = (Button) convertView.findViewById(R.id.deleteClippingButton);
            convertView.setTag(new ViewHolder(first,button,imageView));
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            first = viewHolder.name;
            imageView = viewHolder.imageView;
            button = viewHolder.button;
        }

        final Clipping clipping = getItem(position);
        first.setText(clipping.getNotes());
        first.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fragementList.gotToAClipping(clipping);
            }
        });

        Drawable image = clipping.getImage();
        if(image != null) {
            imageView.setImageDrawable(image);
        }


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fragementList.deleteClippingPopup(clipping);
            }
        });

        return convertView;
    }

    private static class ViewHolder
    {
        public final TextView name;
        public final ImageView imageView;
        public final Button button;

        public ViewHolder(TextView name,Button button, ImageView image)
        {
            this.name = name;
            this.button = button;
            this.imageView = image;
        }
    }
}
