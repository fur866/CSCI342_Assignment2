package com.example.fahad.assignment2;

import android.provider.BaseColumns;

/**
 * Created by Fahad on 2/05/2016.
 */
public class CollectionContract {

    public CollectionContract()
    {}

    public static abstract class CollectionEntry implements BaseColumns {

        public static final String TABLE_NAME = "Collection";
        public static final String COLUMN_NAME_COLLECTION_NAME = "Name";
    }
}
