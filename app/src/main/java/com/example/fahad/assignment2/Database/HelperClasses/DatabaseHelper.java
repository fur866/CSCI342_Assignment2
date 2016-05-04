package com.example.fahad.assignment2.Database.HelperClasses;

/**
 * Created by Fahad on 2/05/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fahad.assignment2.Database.ContractClasses.ClippingContract;
import com.example.fahad.assignment2.Database.ContractClasses.CollectionContract;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "scrapbookmodel.db";
    private static final int DATABASE_VERSION = 1;

    private static final String COLLECTION_CREATE = "create table " + CollectionContract.CollectionEntry.TABLE_NAME + " ("
            + CollectionContract.CollectionEntry.COLUMN_NAME_COLLECTION_ID + " integer primary key autoincrement, "
            + CollectionContract.CollectionEntry.COLUMN_NAME_COLLECTION_NAME + " text not null"
            + ");";

    private static final String CLIPPING_CREATE = "create table " + ClippingContract.ClippingEntry.TABLE_NAME + " ("
            + ClippingContract.ClippingEntry.COLUMN_NAME_CLIPPING_ID + " integer primary key autoincrement, "
            + ClippingContract.ClippingEntry.COLUMN_NAME_COLLECTION_NAME + " text, "
            + ClippingContract.ClippingEntry.COLUMN_NAME_NOTES + " text not null, "
            + ClippingContract.ClippingEntry.COLUMN_NAME_PATH + " text not null, "
            + ClippingContract.ClippingEntry.COLUMN_NAME_DATE_CREATED + " text not null"
            + ");";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COLLECTION_CREATE);
        db.execSQL(CLIPPING_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CollectionContract.CollectionEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClippingContract.ClippingEntry.TABLE_NAME);
        onCreate(db);
    }

    //If when a Person is deleted you wish to cascade the delete to all it's Pets, include the following:
    //(You also need to include ON DELETE CASCADE on the foreign key above when you're creating the Pet table)
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            //Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

}