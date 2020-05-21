package com.vogella.android.recyclerview.presentation.model.controller;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vogella.android.recyclerview.Constants;
import com.vogella.android.recyclerview.Singletons;
import com.vogella.android.recyclerview.data.PokeCallback;
import com.vogella.android.recyclerview.data.PokeRepository;
import com.vogella.android.recyclerview.presentation.model.Pokemon;
import com.vogella.android.recyclerview.presentation.model.RestPokemonResponse;
import com.vogella.android.recyclerview.presentation.model.view.MainActivity;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {
    private final PokeRepository pokeRepository;
    private MainActivity view;

    public MainController(MainActivity mainActivity, PokeRepository pokeRepository) {
        this.view = mainActivity;
        this.pokeRepository = pokeRepository;
    }
    public void onStart(){

        pokeRepository.getPokemonResponse(new PokeCallback() {
            @Override
            public void onSuccess(List<Pokemon> response) {
                view.showList(response);

            }

            @Override
            public void onFailed() {
                view.showError();
            }
        });


    }


    public void onItemClick(Pokemon pokemon){

        view.navigateToDetails(pokemon);

    }

    public void onButtonAClick(){

    }

    public void onButtonBClick(){

    }

    public PokeRepository getPokeRepository() {
        return pokeRepository;
    }
}
