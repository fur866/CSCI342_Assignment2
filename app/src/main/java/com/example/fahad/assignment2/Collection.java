package com.example.fahad.assignment2;

import java.util.ArrayList;

/**
 * Created by Fahad on 2/05/2016.
 */
public class Collection {

    private String name;
    private long ID;
    ArrayList<Clipping> clippings;

    Collection(String name)
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
