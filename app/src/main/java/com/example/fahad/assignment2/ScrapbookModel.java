package com.example.fahad.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Fahad on 3/05/2016.
 */
public class ScrapbookModel {

    private DatabaseHelper dbHelper;

    ScrapbookModel(Context context)
    {
        this.dbHelper = new DatabaseHelper(context);
    }

    //create a Collection and return the ID of the newly created collection
    public long createCollection(String name)
    {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CollectionContract.CollectionEntry.COLUMN_NAME_COLLECTION_NAME, name);
        return db.insert(CollectionContract.CollectionEntry.TABLE_NAME,null, values);
    }

    public ArrayList<Collection> getCollections()
    {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String[] projection = {
                CollectionContract.CollectionEntry.COLUMN_NAME_COLLECTION_NAME//,
                //CollectionContract.CollectionEntry.COLUMN_NAME_COLLECTION_ID,
        };

        String sortOrder = CollectionContract.CollectionEntry.COLUMN_NAME_COLLECTION_NAME + " ASC";

        Cursor cursor = db.query(
                CollectionContract.CollectionEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        ArrayList<Collection> collections = new ArrayList<Collection>();
        while (cursor.moveToNext())
        {
            String name = cursor.getString(cursor.getColumnIndex(CollectionContract.CollectionEntry.COLUMN_NAME_COLLECTION_NAME));
            collections.add(new Collection(name));
        }

        cursor.close();
        return collections;
    }

    public long createClipping(String notes, String dateCreated, Drawable image)
    {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClippingContract.ClippingEntry.COLUMN_NAME_NOTES, notes);
        contentValues.put(ClippingContract.ClippingEntry.COLUMN_NAME_DATE_CREATED, dateCreated);
        return db.insert(ClippingContract.ClippingEntry.TABLE_NAME,null, contentValues);
    }


}
