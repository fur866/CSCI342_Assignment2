package com.example.fahad.assignment2;

import android.graphics.drawable.Drawable;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Fahad on 2/05/2016.
 */
public class ClippingContract {

    protected String notes;
    private Drawable image;
    private String dateCreated;

    ClippingContract(String notes, Drawable image)
    {
        this.notes = notes;
        this.image = image;
        this.dateCreated = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public abstract class Clipping implements BaseColumns
    {

        public String TABLE_NAME = "Clipping";
        public String COLUMN_NAME_NOTES = notes;
        public Drawable COLUMN_NAME_IMAGE = image;
        public String COLUMN_NAME_DATE_CREATED = dateCreated;

    }
}
