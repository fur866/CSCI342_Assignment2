package com.example.fahad.assignment2.Database.ConvenienceClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.fahad.assignment2.Database.ContractClasses.ClippingContract;
import com.example.fahad.assignment2.Database.ContractClasses.CollectionContract;
import com.example.fahad.assignment2.Database.DataClasses.Clipping;
import com.example.fahad.assignment2.Database.DataClasses.Collection;
import com.example.fahad.assignment2.Database.HelperClasses.DatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by Fahad on 3/05/2016.
 */
public class ScrapbookModel {

    private DatabaseHelper dbHelper;
    private Context context;

    public ScrapbookModel(Context context)
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
                CollectionContract.CollectionEntry.COLUMN_NAME_COLLECTION_NAME
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

    public ArrayList<Clipping> getClippings()
    {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String[] projection = {
                ClippingContract.ClippingEntry.COLUMN_NAME_CLIPPING_ID,
                ClippingContract.ClippingEntry.COLUMN_NAME_DATE_CREATED,
                ClippingContract.ClippingEntry.COLUMN_NAME_NOTES,
                ClippingContract.ClippingEntry.COLUMN_NAME_PATH
        };


        Cursor cursor = db.query(
                ClippingContract.ClippingEntry.TABLE_NAME,
                projection,
                null,
                null,
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

            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Drawable image = new BitmapDrawable(this.context.getResources(), bitmap);

            Clipping clipping = new Clipping(notes,image,dateCreated,Integer.parseInt(id));
            clipping.setPath(path);
            clippings.add(clipping);
        }

        cursor.close();
        return clippings;
    }

    public long createClipping(String notes, String dateCreated, Drawable image, String cName)
    {
        String path = this.context.getFilesDir() + new BigInteger(130, new SecureRandom()).toString(32)+".png";

        try{
            Bitmap bitmap = drawableToBitmap(image);
            OutputStream outStream = new FileOutputStream(new File(path));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (IOException e) {
            Log.d("Here","In Exception: "+String.valueOf(e));
            System.out.println(e);
        }

        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClippingContract.ClippingEntry.COLUMN_NAME_NOTES, notes);
        contentValues.put(ClippingContract.ClippingEntry.COLUMN_NAME_PATH, path);
        contentValues.put(ClippingContract.ClippingEntry.COLUMN_NAME_DATE_CREATED, dateCreated);
        return db.insert(ClippingContract.ClippingEntry.TABLE_NAME,null, contentValues);
    }

    public Clipping createClipping(String notes, String dateCreated, Drawable image)
    {
        String path = this.context.getFilesDir() + new BigInteger(130, new SecureRandom()).toString(32)+".png";

        try{
            Bitmap bitmap = drawableToBitmap(image);
            OutputStream outStream = new FileOutputStream(new File(path));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (IOException e) {
            System.out.println(e);
        }

        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClippingContract.ClippingEntry.COLUMN_NAME_NOTES, notes);
        contentValues.put(ClippingContract.ClippingEntry.COLUMN_NAME_PATH, path);
        contentValues.put(ClippingContract.ClippingEntry.COLUMN_NAME_DATE_CREATED, dateCreated);
        long id = db.insert(ClippingContract.ClippingEntry.TABLE_NAME,null, contentValues);

        Clipping clipping = new Clipping(notes,image,dateCreated,id);
        clipping.setPath(path);

        return clipping;
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

            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Drawable image = new BitmapDrawable(this.context.getResources(), bitmap);

            Clipping clipping = new Clipping(notes,image,dateCreated,Integer.parseInt(id));
            clipping.setPath(path);
            clippings.add(clipping);
        }

        cursor.close();
        return clippings;
    }

    public void assignClipping(long id,String cName){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ClippingContract.ClippingEntry.COLUMN_NAME_COLLECTION_NAME, cName);
        db.update(ClippingContract.ClippingEntry.TABLE_NAME, values, ClippingContract.ClippingEntry.COLUMN_NAME_CLIPPING_ID + "= ?", new String[]{String.valueOf(id)});
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

            if((collectionName.length() > 0 && name.contentEquals(collectionName)) || collectionName.length() == 0){
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                Drawable image = new BitmapDrawable(this.context.getResources(), bitmap);

                Clipping clipping = new Clipping(notes,image,dateCreated,Integer.parseInt(id));
                clipping.setPath(path);
                clippings.add(clipping);
            }
        }
        cursor.close();
        return clippings;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}