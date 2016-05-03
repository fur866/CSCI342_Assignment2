package com.example.fahad.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Fahad on 3/05/2016.
 */
public class ScrapbookModel {

    private DatabaseHelper dbHelper;
    private Context context;

    ScrapbookModel(Context context)
    {
        this.context = context;
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

    public long createClipping(String notes, String dateCreated, Drawable image, String cName, int counter)
    {
        String path = this.context.getFilesDir() + String.valueOf(counter);
        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(image);
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClippingContract.ClippingEntry.COLUMN_NAME_NOTES, notes);
        contentValues.put(ClippingContract.ClippingEntry.COLUMN_NAME_DATE_CREATED, dateCreated);
        contentValues.put(ClippingContract.ClippingEntry.COLUMN_NAME_COLLECTION_NAME, cName);
        contentValues.put(ClippingContract.ClippingEntry.COLUMN_NAME_PATH, path);
        return db.insert(ClippingContract.ClippingEntry.TABLE_NAME,null, contentValues);
    }

    public  ArrayList<Clipping> getClippingsByCollection(String name)
    {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        String[] projections = {
                ClippingContract.ClippingEntry.COLUMN_NAME_NOTES,
                ClippingContract.ClippingEntry.COLUMN_NAME_DATE_CREATED,
                ClippingContract.ClippingEntry.COLUMN_NAME_PATH,
                ClippingContract.ClippingEntry.COLUMN_NAME_CLIPPING_ID
        };
        String whereClause = ClippingContract.ClippingEntry.COLUMN_NAME_COLLECTION_NAME + "=?";

        Cursor cursor = db.query(
                ClippingContract.ClippingEntry.TABLE_NAME,
                projections,
                whereClause,
                new String[]{name},
                null,
                null,
                null
        );

        ArrayList<Clipping> clippings = new ArrayList<Clipping>();
        while (cursor.moveToNext())
        {
            String notes = cursor.getString(cursor.getColumnIndex(ClippingContract.ClippingEntry.COLUMN_NAME_NOTES));
            String dateCreated = cursor.getString(cursor.getColumnIndex(ClippingContract.ClippingEntry.COLUMN_NAME_DATE_CREATED));
            String path = cursor.getString(cursor.getColumnIndex(ClippingContract.ClippingEntry.COLUMN_NAME_PATH));
            String id = cursor.getString(cursor.getColumnIndex(ClippingContract.ClippingEntry.COLUMN_NAME_CLIPPING_ID));

            try (
                    InputStream file = new FileInputStream(path);
                    InputStream buffer = new BufferedInputStream(file);
                    ObjectInput input = new ObjectInputStream(buffer);
            ) {
                Drawable image = (Drawable) input.readObject();
                Clipping clipping = new Clipping(notes,image,name,dateCreated,Integer.parseInt(id));
                clipping.setPath(path);
                clippings.add(clipping);
            } catch (ClassNotFoundException ex) {

            } catch (IOException ex) {

            }
        }

        cursor.close();
        return clippings;
    }

    public void deleteCollection(String name)
    {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ClippingContract.ClippingEntry.TABLE_NAME, new String[]{ClippingContract.ClippingEntry.COLUMN_NAME_CLIPPING_ID}
                , "LOWER(" + ClippingContract.ClippingEntry.COLUMN_NAME_COLLECTION_NAME + ") = ?", new String[]{name.toLowerCase()} , null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            int id = cursor.getInt(cursor.getColumnIndex(ClippingContract.ClippingEntry.COLUMN_NAME_CLIPPING_ID));
            deleteClipping(id);
            cursor.moveToNext();
        }
        cursor.close();
        db = this.dbHelper.getWritableDatabase();
        db.delete(CollectionContract.CollectionEntry.TABLE_NAME, CollectionContract.CollectionEntry.COLUMN_NAME_COLLECTION_NAME + "=?", new String[]{name});
    }

    public void deleteClipping(long id)
    {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ClippingContract.ClippingEntry.TABLE_NAME, new String[]{ClippingContract.ClippingEntry.COLUMN_NAME_PATH}
                , ClippingContract.ClippingEntry.COLUMN_NAME_CLIPPING_ID + " = ?", new String[]{String.valueOf(id)} , null, null, null);
        if (cursor.getCount() != 0)
        {
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(ClippingContract.ClippingEntry.COLUMN_NAME_PATH));
            if (path != null){

                try{
                    File file = new File(path);

                    if(!file.delete()){
                        System.out.println("Delete operation is failed.");
                    }
                }catch(Exception e){
                    e.printStackTrace();

                }
            }
        }
        cursor.close();
        db = this.dbHelper.getWritableDatabase();
        db.delete(ClippingContract.ClippingEntry.TABLE_NAME, ClippingContract.ClippingEntry.COLUMN_NAME_CLIPPING_ID + "=?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Clipping> searchClippings(String needle, String collectionName)
    {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String[] projections = {ClippingContract.ClippingEntry.COLUMN_NAME_PATH,
                ClippingContract.ClippingEntry.COLUMN_NAME_NOTES,
                ClippingContract.ClippingEntry.COLUMN_NAME_DATE_CREATED,
                ClippingContract.ClippingEntry.COLUMN_NAME_COLLECTION_NAME,
                ClippingContract.ClippingEntry.COLUMN_NAME_CLIPPING_ID};

        String likeQuery = "%" + needle + "%";
        ArrayList<Clipping> clippings = new ArrayList<Clipping>();
        Cursor cursor = db.query(ClippingContract.ClippingEntry.TABLE_NAME,
                projections
                , "LOWER(" + ClippingContract.ClippingEntry.COLUMN_NAME_NOTES + ") LIKE ?", new String[]{likeQuery.toLowerCase()}
                , null, null,null);


        while (cursor.moveToNext())
        {
            String notes = cursor.getString(cursor.getColumnIndex(ClippingContract.ClippingEntry.COLUMN_NAME_NOTES));
            String dateCreated = cursor.getString(cursor.getColumnIndex(ClippingContract.ClippingEntry.COLUMN_NAME_DATE_CREATED));
            String path = cursor.getString(cursor.getColumnIndex(ClippingContract.ClippingEntry.COLUMN_NAME_PATH));
            String id = cursor.getString(cursor.getColumnIndex(ClippingContract.ClippingEntry.COLUMN_NAME_CLIPPING_ID));
            String name = cursor.getString(cursor.getColumnIndex(ClippingContract.ClippingEntry.COLUMN_NAME_COLLECTION_NAME));

            if((collectionName.length() > 0 && name == collectionName) || collectionName.length() == 0){
                try (
                        InputStream file = new FileInputStream(path);
                        InputStream buffer = new BufferedInputStream(file);
                        ObjectInput input = new ObjectInputStream(buffer);
                ) {
                    Drawable image = (Drawable) input.readObject();
                    Clipping clipping = new Clipping(notes,image,name,dateCreated,Integer.parseInt(id));
                    clipping.setPath(path);
                    clippings.add(clipping);
                } catch (ClassNotFoundException ex) {

                } catch (IOException ex) {

                }
            }
        }
        cursor.close();
        return clippings;
    }
}
