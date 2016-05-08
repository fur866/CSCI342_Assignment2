package com.example.fahad.assignment2.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.fahad.assignment2.Database.ConvenienceClasses.ScrapbookModel;
import com.example.fahad.assignment2.Database.DataClasses.Clipping;
import com.example.fahad.assignment2.MainActivity;
import com.example.fahad.assignment2.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Fahad on 8/05/2016.
 */
public class AddClippingFragment extends Fragment{

    private MainActivity mCallback;
    private ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.add_clipping,container,false);
        final String parent = getArguments().getString("parentCollection");

        this.imageView = (ImageView) view.findViewById(R.id.newClippingAddImage);
        this.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        final EditText editText = (EditText) view.findViewById(R.id.newClippingText);

        Button button = (Button) view.findViewById(R.id.addClipping);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Clipping clipping;

                if(parent != "") {
                    clipping = new Clipping(editText.getText().toString(),imageView.getDrawable(),parent);
                }
                else
                {
                    clipping = new Clipping(editText.getText().toString(),imageView.getDrawable());
                }
                mCallback.saveClipping(clipping,parent);
                mCallback.showClippingsFragment(parent != "" ? parent : "All Clippings");
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Log.d("here",String.valueOf(requestCode));

        switch(requestCode) {
            case 1:
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = this.mCallback.getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        this.imageView.setImageDrawable(new BitmapDrawable(getContext().getResources(), selectedImage));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
        }
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
