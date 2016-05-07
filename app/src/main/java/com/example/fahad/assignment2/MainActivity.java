package com.example.fahad.assignment2;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.fahad.assignment2.Database.DataClasses.Collection;
import com.example.fahad.assignment2.Database.HelperClasses.DatabaseHelper;
import com.example.fahad.assignment2.Database.ConvenienceClasses.ScrapbookModel;
import com.example.fahad.assignment2.Fragments.CollectionListFragment;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ScrapbookModel scrapbookModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getApplicationContext().deleteDatabase(DatabaseHelper.DATABASE_NAME);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.scrapbookModel = new ScrapbookModel(getApplicationContext());
        Collection c1 = new Collection("A");
        Collection c2 = new Collection("B");
        c1.setID(scrapbookModel.createCollection(c1.getName()));
        c2.setID(scrapbookModel.createCollection(c2.getName()));

        ArrayList<Collection> collections = scrapbookModel.getCollections();
        ArrayList<String> names = new ArrayList<String>();
        Log.d("here",String.valueOf(collections.size()));
        for(Collection collection : collections)
        {
            Log.d("here",collection.getName());
            names.add(collection.getName());
        }

        addCollectionListFragment(names);
        //get Scrapbook model instance
       /* ScrapbookModel scrapbookModel = new ScrapbookModel(getApplicationContext());

        //creating collections
        Collection c1 = new Collection("A");
        Collection c2 = new Collection("B");
        c1.setID(scrapbookModel.createCollection(c1.getName()));
        c2.setID(scrapbookModel.createCollection(c2.getName()));

        //creating clippings
        Clipping cp1 = scrapbookModel.createClipping("1 foo",new SimpleDateFormat("yyyy-MM-dd").format(new Date()),ContextCompat.getDrawable(this,R.drawable.baldhills));
        Clipping cp2 = scrapbookModel.createClipping("2 foo",new SimpleDateFormat("yyyy-MM-dd").format(new Date()),ContextCompat.getDrawable(this,R.drawable.lakes));
        Clipping cp3 = scrapbookModel.createClipping("3 bar",new SimpleDateFormat("yyyy-MM-dd").format(new Date()),ContextCompat.getDrawable(this,R.drawable.cathedrals));


        ArrayList<Collection> collections = scrapbookModel.getCollections();
        for(Collection collection : collections)
        {
            Log.d("HERE",collection.getName());
        }

        ArrayList<Clipping> clippings = scrapbookModel.getClippings();
        for(Clipping clipping : clippings)
        {
            Log.d("HERE notes",clipping.getNotes());
            Log.d("HERE date",clipping.getDateCreated());
            Log.d("HERE path",clipping.getPath());
        }

        scrapbookModel.assignClipping(cp1.getID(),c1.getName());
        scrapbookModel.assignClipping(cp2.getID(),c1.getName());

        ArrayList<Clipping> clippings1 = scrapbookModel.getClippingsByCollection(c1.getName());
        for(Clipping clipping : clippings1)
        {
            Log.d("HERE notes1",clipping.getNotes());
            Log.d("HERE date1",clipping.getDateCreated());
            Log.d("HERE path1",clipping.getPath());
        }

        scrapbookModel.deleteClipping(cp1.getID());
        ArrayList<Clipping> clippings2 = scrapbookModel.getClippingsByCollection(c1.getName());
        for(Clipping clipping : clippings2)
        {
            Log.d("HERE notes2",clipping.getNotes());
            Log.d("HERE date2",clipping.getDateCreated());
            Log.d("HERE path2",clipping.getPath());
        }

        ArrayList<Clipping> clippings3 = scrapbookModel.searchClippings("bar","");
        Log.d("HERE size",String.valueOf(clippings3.size()));
        for(Clipping clipping : clippings3)
        {
            Log.d("HERE notes3",clipping.getNotes());
            Log.d("HERE date3",clipping.getDateCreated());
            Log.d("HERE path3",clipping.getPath());
        }*/
        getApplicationContext().deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }

    public void addCollectionListFragment(ArrayList<String> names)
    {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("Collections",names);
        CollectionListFragment collectionList = new CollectionListFragment();
        collectionList.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frames, collectionList);
        fragmentTransaction.commit();
    }
}
