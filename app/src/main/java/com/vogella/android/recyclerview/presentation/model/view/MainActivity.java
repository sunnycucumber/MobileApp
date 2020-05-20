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
import com.google.gson.reflect.TypeToken;
import com.vogella.android.recyclerview.Constants;
import com.vogella.android.recyclerview.R;
import com.vogella.android.recyclerview.data.PokeApi;
import com.vogella.android.recyclerview.presentation.model.Pokemon;
import com.vogella.android.recyclerview.presentation.model.RestPokemonResponse;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity <recyclerView> extends Activity {
    private static final String BASE_URL = "https://pokeapi.co/";
    private RecyclerView recyclerView;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         sharedPreferences = getSharedPreferences("application esiea", Context.MODE_PRIVATE);
         gson = new GsonBuilder()
                .setLenient()
                .create();
         List<Pokemon> pokemonList = getDataFromCache();
         if( pokemonList != null){
             showList(pokemonList);
         }else {
             makeApiCall();
         }

    }

    private List<Pokemon> getDataFromCache() {
        String jsonPokemon  = sharedPreferences.getString(Constants.KEY_POKEMON_LIST, null);
        if(jsonPokemon == null){
            return null;
        }else {
            Type ListType = new TypeToken<List<Pokemon>>(){}.getType();
            return gson.fromJson(jsonPokemon, ListType);
        }
    }


    private void showList(List<Pokemon> pokemonList) {
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


    private void makeApiCall(){


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
                            saveList(pokemonList);
                            showList(pokemonList);

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

    private void saveList(List<Pokemon> pokemonList) {
        String jsonString = gson.toJson(pokemonList);

        sharedPreferences
                .edit()
                .putString(Constants.KEY_POKEMON_LIST, jsonString)
                .apply();

        Toast.makeText(getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();
    }


    private void showError(){
            Toast.makeText(getApplicationContext(), "Api ERROR", Toast.LENGTH_SHORT).show();
        }




}
