package com.vogella.android.recyclerview.presentation.model.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vogella.android.recyclerview.R;
import com.vogella.android.recyclerview.Singletons;
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
                Singletons.getGson(),
                 Singletons.getSharedPreferences(getApplicationContext())

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
       layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



         mAdapter = new Adapter(pokemonList, new Adapter.OnItemClickListener(){
             @Override
             public void onItemClick(Pokemon item) {
                 controller.onItemClick(item);
             }
         });
        recyclerView.setAdapter(mAdapter);
    }





    public void showError(){
            Toast.makeText(getApplicationContext(), "Api ERROR", Toast.LENGTH_SHORT).show();
        }


    public void navigateToDetails(Pokemon pokemon) {
        Toast.makeText(getApplicationContext(), "TODO NAVIGATE", Toast.LENGTH_SHORT).show();

    }
}
