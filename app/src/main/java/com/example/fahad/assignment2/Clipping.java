package com.example.fahad.assignment2;

import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Fahad on 2/05/2016.
 */
public class Clipping {

    private String notes;
    private Drawable image;
    private String dateCreated;

    Clipping(String notes, Drawable image)
    {
        this.notes = notes;
        this.image = image;
        this.dateCreated = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
