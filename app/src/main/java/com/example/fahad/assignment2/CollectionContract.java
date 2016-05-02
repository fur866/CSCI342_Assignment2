package com.example.fahad.assignment2;

import android.provider.BaseColumns;

/**
 * Created by Fahad on 2/05/2016.
 */
public class CollectionContract {

    private String name;

    CollectionContract(String name)
    {
        this.name = name;
    }

    public abstract class Clipping implements BaseColumns
    {
        public String TABLE_NAME = "Collection";
        public String COLUMN_NAME_NAME = name;
    }
}
