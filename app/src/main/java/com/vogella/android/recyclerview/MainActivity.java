package com.vogella.android.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity <recyclerView> extends Activity {
    private static final String BASE_URL = "https://pokeapi.co/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showList();
        makeApiCall();
    }



    private void showList() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }
        RecyclerView.Adapter<Adapter.ViewHolder> mAdapter = new Adapter(input);
        recyclerView.setAdapter(mAdapter);
    }


    private void makeApiCall(){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            PokeApi pokeApi =  retrofit.create(PokeApi.class);

            Call<RestPokemonResponse> call = pokeApi.getPokemonResponse();
            call.enqueue(new Callback<RestPokemonResponse>() {
                @Override
                public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                    if(response.isSuccessful() && response.body() != null) {
                            List<Pokemon> pokemonList = response.body().getResults();
                        Toast.makeText(getApplicationContext(), "Api SUCCESS", Toast.LENGTH_SHORT).show();

                    } else{
                        showError();
                    }
                }

                @Override
                public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                    showError();
                }
            });

        }
        private void showError(){
            Toast.makeText(getApplicationContext(), "Api ERROR", Toast.LENGTH_SHORT).show();
        }




}
