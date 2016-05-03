package com.example.fahad.assignment2;

import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Fahad on 2/05/2016.
 */
public class Clipping {

    protected String notes;
    private Drawable image;
    private String path;
    private String dateCreated;
    private int ID;
    private String collectionName;

    Clipping(String notes, Drawable image, String collectionName)
    {
        this.notes = notes;
        this.image = image;
        this.dateCreated =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.collectionName = collectionName;
    }

    Clipping(String notes, Drawable image, String collectionName, String dateCreated,int ID)
    {
        this.notes = notes;
        this.image = image;
        this.dateCreated = dateCreated;
        this.collectionName = collectionName;
        this.ID = ID;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return this.path;
    }
}
