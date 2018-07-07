package com.example.manpreetkaur.pet_treasure;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.manpreetkaur.pet_treasure_Controller.Pet_Adapter;

import java.util.ArrayList;
import java.util.List;

import pet_treasure_Model.DatabaseHelper;
import pet_treasure_Model.Pet;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private Pet_Adapter petAdapter;
    private List<Pet> mPetList = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    private List<Pet> listUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //databaseHelper = new DatabaseHelper(this);


        //initialize
        mListView = (ListView)findViewById(R.id.listview);

        listUsers = new ArrayList<>();


        // images will come from the data base.........................pending

        //create new planet objects to add to planets list
        Pet pet1 = new Pet(R.drawable.image1, "Mercury");
        Pet pet2 = new Pet(R.drawable.image2, "Venus");
        Pet pet3 = new Pet(R.drawable.image3, "Earth");
        Pet pet4 = new Pet(R.drawable.image1, "Mars");
        Pet pet5 = new Pet(R.drawable.image2, "Jupiter");
        Pet pet6 = new Pet(R.drawable.image3, "Saturn");
        Pet pet7 = new Pet(R.drawable.image1, "Uranus");
        Pet pet8 = new Pet(R.drawable.image1, "Neptune");

        //add to list
        mPetList.add(pet1);
        mPetList.add(pet2);
        mPetList.add(pet3);
        mPetList.add(pet4);
        mPetList.add(pet5);
        mPetList.add(pet5);
        mPetList.add(pet6);
        mPetList.add(pet7);

        //use adapter to populate to homepage
        petAdapter = new Pet_Adapter(this, mPetList);
        mListView.setAdapter(petAdapter);



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent login_intent = new Intent(MainActivity.this, Detail_Activity.class);
                startActivity(login_intent);
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Logout");



        //getDataFromSQLite();


    }



    private void getDataFromSQLite() {

        // AsyncTask is used that SQLite operation not blocks the UI Thread.


        /*new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                System.out.println("yeyeyey"+databaseHelper.getAllUser().get(2));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }.execute();*/


        databaseHelper.getAllData();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), Login_Activity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

}
