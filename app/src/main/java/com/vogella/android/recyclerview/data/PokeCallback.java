package com.vogella.android.recyclerview.data;

import com.vogella.android.recyclerview.presentation.model.Pokemon;
import com.vogella.android.recyclerview.presentation.model.RestPokemonResponse;

import java.util.List;

public interface PokeCallback {
        public void onSuccess(List<Pokemon> response);
        public void onFailed();
}
