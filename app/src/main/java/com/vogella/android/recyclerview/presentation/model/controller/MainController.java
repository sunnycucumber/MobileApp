package com.vogella.android.recyclerview.presentation.model.controller;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vogella.android.recyclerview.Constants;
import com.vogella.android.recyclerview.Singletons;
import com.vogella.android.recyclerview.presentation.model.Pokemon;
import com.vogella.android.recyclerview.presentation.model.RestPokemonResponse;
import com.vogella.android.recyclerview.presentation.model.view.MainActivity;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences) {
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;

    }
    public void onStart(){

        List<Pokemon> pokemonList = getDataFromCache();

        if( pokemonList != null){
            view.showList(pokemonList);
        }else {
            makeApiCall();
        }
    }

    private List<Pokemon> getDataFromCache() {
        String jsonPokemon = sharedPreferences.getString(Constants.KEY_POKEMON_LIST, null);

        if (jsonPokemon == null){
            return null;
        } else {
            Type listType = new TypeToken<List<Pokemon>>(){}.getType();
            return gson.fromJson(jsonPokemon, listType);
        }
    }


    private void makeApiCall(){



        Call<RestPokemonResponse> call = Singletons.getPokeApi().getPokemonResponse();
        call.enqueue(new Callback<RestPokemonResponse>() {
            @Override
            public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Pokemon> pokemonList = response.body().getResults();
                    saveList(pokemonList);
                    view.showList(pokemonList);

                } else{
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestPokemonResponse> call, Throwable t) {

                view.showError();
            }
        });

    }

    private void saveList(List<Pokemon> pokemonList) {
        String jsonString = gson.toJson(pokemonList);

        sharedPreferences
                .edit()
                .putString(Constants.KEY_POKEMON_LIST, jsonString)
                .apply();

        //Toast.makeText(getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();
    }

    public void onItemClick(Pokemon pokemon){

    }

    public void onButtonAClick(){

    }

    public void onButtonBClick(){

    }
}
