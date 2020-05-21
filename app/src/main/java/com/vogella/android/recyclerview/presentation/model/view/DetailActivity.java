package com.vogella.android.recyclerview.presentation.model.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vogella.android.recyclerview.R;
import com.vogella.android.recyclerview.Singletons;
import com.vogella.android.recyclerview.presentation.model.Pokemon;
import com.vogella.android.recyclerview.presentation.model.controller.MainController;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView txtDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtDetail = findViewById(R.id.detail_txt);
        Intent intent = getIntent();
        String pokemonJson = intent.getStringExtra("pokemonKey");
        Pokemon pokemon = Singletons.getGson().fromJson(pokemonJson, Pokemon.class);
        showDetail(pokemon);


    }

    private void showDetail(Pokemon pokemon) {
        txtDetail.setText(pokemon.getName());
    }

}




