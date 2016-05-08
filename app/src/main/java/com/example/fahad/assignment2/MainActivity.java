package com.example.fahad.assignment2;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fahad.assignment2.Database.DataClasses.Clipping;
import com.example.fahad.assignment2.Database.DataClasses.Collection;
import com.example.fahad.assignment2.Database.ConvenienceClasses.ScrapbookModel;
import com.example.fahad.assignment2.Fragments.AddClippingFragment;
import com.example.fahad.assignment2.Fragments.ClippingDetailsFragment;
import com.example.fahad.assignment2.Fragments.ClippingListFragment;
import com.example.fahad.assignment2.Fragments.CollectionListFragment;
import com.example.fahad.assignment2.Fragments.SearchFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ScrapbookModel scrapbookModel;
    CollectionListFragment collectionList;
    ClippingListFragment clippingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.collectionList = new CollectionListFragment();
        this.clippingList = new ClippingListFragment();

        this.scrapbookModel = new ScrapbookModel(getApplicationContext());
        ArrayList<Collection> collections = scrapbookModel.getCollections();
        ArrayList<String> names = new ArrayList<String>();
        for(Collection collection : collections)
        {
            names.add(collection.getName());
        }

        this.addCollectionListFragment(names);
    }

    public void addCollectionListFragment(ArrayList<String> names)
    {
        names.add(0,"All Clippings");
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("Collections",names);
        this.collectionList.setArguments(bundle);

        TextView textView = (TextView) findViewById(R.id.header);
        textView.setText("Collections");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frames, collectionList);
        fragmentTransaction.commit();
    }

    public void showClippingsFragment(String parentCollection)
    {
        Bundle bundle = new Bundle();
        if(parentCollection != "All Clippings") {
            bundle.putSerializable("Clippings", this.scrapbookModel.getClippingsByCollection(parentCollection));
        }
        else{
            bundle.putSerializable("Clippings", this.scrapbookModel.getClippings());
        }
        bundle.putString("parentCollection",parentCollection);
        this.clippingList.setArguments(bundle);

        TextView textView = (TextView) findViewById(R.id.header);
        textView.setText(parentCollection == "All Clippings" ? parentCollection : "Collection "+parentCollection);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frames, clippingList);
        fragmentTransaction.replace(R.id.searchFrame,new SearchFragment());
        fragmentTransaction.commit();
    }

    public void showClipping(Clipping clipping)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Clippings", clipping);
        ClippingDetailsFragment clippingDetailsFragment = new ClippingDetailsFragment();
        clippingDetailsFragment.setArguments(bundle);

        TextView textView = (TextView) findViewById(R.id.header);
        textView.setText("Clipping Created: "+clipping.getDateCreated());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frames, clippingDetailsFragment);
        fragmentTransaction.commit();
    }

    public void addClipping(String parentCollection)
    {
        Bundle bundle = new Bundle();
        bundle.putString("parentCollection",parentCollection);
        AddClippingFragment addClippingFragment = new AddClippingFragment();
        addClippingFragment.setArguments(bundle);

        TextView textView = (TextView) findViewById(R.id.header);
        textView.setText("Add Clipping");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frames, addClippingFragment);
        fragmentTransaction.commit();
    }

    public void searchClippings(String searchBar)
    {
        String parentCollection = this.clippingList.getParentCollection();
        ArrayList<Clipping> clippings;
        if(searchBar.length() > 0)
        {
            if(parentCollection == "All Clippings")
            {
                clippings = this.scrapbookModel.searchClippings(searchBar,"");
            }
            else
            {
                clippings = this.scrapbookModel.searchClippings(searchBar,parentCollection);
            }
        }
        else
        {
            if(parentCollection != "All Clippings")
            {
                clippings = this.scrapbookModel.getClippingsByCollection(parentCollection);
            }
            else
            {
                clippings = this.scrapbookModel.getClippings();
            }
        }
        this.clippingList.changeList(clippings);
    }

    public void saveClipping(Clipping clipping,String parent)
    {
        Clipping cp1 = this.scrapbookModel.createClipping(clipping.getNotes(),clipping.getDateCreated(),clipping.getImage());
        if(parent != "") {
            this.scrapbookModel.assignClipping(cp1.getID(), parent);
        }
    }

    public void deleteClipping(long id)
    {
        this.scrapbookModel.deleteClipping(id);
    }
}
