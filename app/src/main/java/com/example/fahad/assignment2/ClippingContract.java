package com.example.fahad.assignment2;

import android.provider.BaseColumns;

/**
 * Created by Fahad on 2/05/2016.
 */
public class ClippingContract {

    public ClippingContract()
    {}

    public static abstract class ClippingEntry implements BaseColumns {

        public static final String TABLE_NAME = "Clipping";
        public static final String COLUMN_NAME_NOTES = "Notes";
        public static final String COLUMN_NAME_DATE_CREATED = "DateCreated";
        public static final String COLUMN_NAME_CLIPPING_ID = "id";
        public static final String COLUMN_NAME_COLLECTION_NAME = "cName";
        public static final String COLUMN_NAME_PATH = "path";
    }
}
