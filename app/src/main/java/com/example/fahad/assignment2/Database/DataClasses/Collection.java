package com.example.fahad.assignment2.Database.DataClasses;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fahad on 2/05/2016.
 */
public class Collection implements Serializable{

    private String name;
    private long ID;
    ArrayList<Clipping> clippings;

    public Collection(String name)
    {
        this.name = name;
    }

    public void setID(long ID)
    {
        this.ID = ID;
    }

    public long getID()
    {
        return this.ID;
    }

    public void addClipping(Clipping clipping)
    {
        this.clippings.add(clipping);
    }

    public ArrayList<Clipping> getClipping()
    {
        return this.clippings;
    }

    public String getName()
    {
        return this.name;
    }
}
