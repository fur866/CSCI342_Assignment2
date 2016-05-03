package com.example.fahad.assignment2;

import java.util.ArrayList;

/**
 * Created by Fahad on 2/05/2016.
 */
public class Collection {

    private String name;
    private long ID;
    private int imageCounter;
    ArrayList<Clipping> clippings;

    Collection(String name)
    {
        this.imageCounter = 0;
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
}
