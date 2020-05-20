package com.vogella.android.recyclerview.presentation.model.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vogella.android.recyclerview.R;
import com.vogella.android.recyclerview.presentation.model.Pokemon;
import com.vogella.android.recyclerview.presentation.model.controller.MainController;

import java.util.List;

public class MainActivity <recyclerView> extends Activity {
    private RecyclerView recyclerView;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private MainController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new MainController(
                this,
                new GsonBuilder()
                        .setLenient()
                        .create(),
                 getSharedPreferences("application esiea", Context.MODE_PRIVATE)
);
        controller.onStart();



    }




    public void showList(List<Pokemon> pokemonList) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Adapter mAdapter = new Adapter(pokemonList);
        recyclerView.setAdapter(mAdapter);
    }





    public void showError(){
            Toast.makeText(getApplicationContext(), "Api ERROR", Toast.LENGTH_SHORT).show();
        }




}
