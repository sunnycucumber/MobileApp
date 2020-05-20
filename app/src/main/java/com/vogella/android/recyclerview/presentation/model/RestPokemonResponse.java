package com.vogella.android.recyclerview.presentation.model;

import java.util.List;

public class RestPokemonResponse {
    private Integer count;
    private String next;
    private List<Pokemon>results;

    public RestPokemonResponse() {
    }

    public List<Pokemon> getResults() {
        return results;
    }
}

