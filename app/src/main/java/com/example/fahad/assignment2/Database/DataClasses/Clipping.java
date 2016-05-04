package com.example.fahad.assignment2.Database.DataClasses;

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
    private long ID;
    private String collectionName;

    public Clipping(String notes, Drawable image)
    {
        this.notes = notes;
        this.image = image;
        this.dateCreated = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public Clipping(String notes, Drawable image, String dateCreated,long ID)
    {
        this.notes = notes;
        this.image = image;
        this.dateCreated = dateCreated;
        this.ID = ID;
    }

    public Clipping(String notes, Drawable image, String collectionName)
    {
        this.notes = notes;
        this.image = image;
        this.dateCreated =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.collectionName = collectionName;
    }

    public Clipping(String notes, Drawable image, String collectionName, String dateCreated,long ID)
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

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getID()
    {
        return this.ID;
    }

    public String getPath()
    {
        return this.path;
    }

    public String getNotes()
    {
        return this.notes;
    }

    public String getDateCreated()
    {
        return this.dateCreated;
    }
}
