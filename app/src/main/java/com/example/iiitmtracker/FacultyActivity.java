package com.example.iiitmtracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.iiitmtracker.adapter.RecyclerViewAdapter;
import com.example.iiitmtracker.data.Faculties;
import com.example.iiitmtracker.model.Faculty;
import com.example.iiitmtracker.HomeActivity;
import java.util.ArrayList;

public class FacultyActivity extends AppCompatActivity {

    public ConstraintLayout activity_faculty_bg;
    public RecyclerView faculty_rv;
    public RecyclerViewAdapter recyclerViewAdapter;
    public ArrayList<Faculty> facultyArrayList;
    public ArrayAdapter<String> arrayAdapter;
    public ActionBar actionBar;
    public boolean isDarkModeOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

        actionBar = getSupportActionBar();

        activity_faculty_bg = findViewById(R.id.activity_faculty_bg);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        toggleDarkMode(isDarkModeOn);

        faculty_rv = findViewById(R.id.faculty_rv);
        faculty_rv.setHasFixedSize(true);
        faculty_rv.setLayoutManager(new LinearLayoutManager(this));

//        facultyArrayList = new ArrayList<Faculty>();
        facultyArrayList = Faculties.getAllFaculties();

        //Using Recycler View
        recyclerViewAdapter = new RecyclerViewAdapter(FacultyActivity.this, facultyArrayList, isDarkModeOn);
        faculty_rv.setAdapter(recyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Faculty> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Faculty item : facultyArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            recyclerViewAdapter.filterList(filteredlist);
        }
    }

    public void toggleDarkMode(boolean isDarkModeOn) {
        if(isDarkModeOn) {
            activity_faculty_bg.setBackgroundResource(R.drawable.background_2);
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#217398"));
            actionBar.setBackgroundDrawable(colorDrawable);

        }
        else {
            activity_faculty_bg.setBackgroundResource(R.drawable.background_3);
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0086C3"));
            actionBar.setBackgroundDrawable(colorDrawable);

        }
    }
}