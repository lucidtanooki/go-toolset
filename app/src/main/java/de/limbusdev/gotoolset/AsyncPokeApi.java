package de.limbusdev.gotoolset;

import android.os.AsyncTask;
import java.util.HashMap;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;

/**
 * AsyncPokeApi
 *
 * @author Georg Eckert 2017
 */

public class AsyncPokeApi {

    public final static String TAG = AsyncPokeApi.class.getCanonicalName();

    private PokeApi pokeApi;

    private HashMap<Integer,PokemonSpecies> pokemonSpeciesList;
    private NamedApiResourceList pokemonList;

    private static AsyncPokeApi instance;

    public static AsyncPokeApi getInstance() {
        if(instance == null) instance = new AsyncPokeApi();
        return instance;
    }

    private AsyncPokeApi() {
        this.pokeApi = new PokeApiClient();

        pokemonSpeciesList = new HashMap<>();

        fetchPokemonList();
    }

    public void fetchPokemonSpecies(int id, final IPokeApiDataReceiver receiver) {
        AsyncTask<Integer, Integer, PokemonSpecies> task = new AsyncTask<Integer, Integer, PokemonSpecies>() {
            @Override
            protected PokemonSpecies doInBackground(Integer... params) {
                try {
                    int id = params[0];

                    if (pokemonSpeciesList.containsKey(id)) {
                        return pokemonSpeciesList.get(id);
                    } else {
                        PokemonSpecies species = pokeApi.getPokemonSpecies(params[0]);
                        pokemonSpeciesList.put(id, species);
                        return species;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(PokemonSpecies result) {
                if(result == null) return;
                receiver.receivePokemonSpeciesData(result);
            }

        };

        task.execute(id);
    }

    private void fetchPokemonList() {
        AsyncTask<Integer, Integer, NamedApiResourceList> task = new AsyncTask<Integer, Integer, NamedApiResourceList>() {
            @Override
            protected NamedApiResourceList doInBackground(Integer... params) {
                try {
                    pokemonList = pokeApi.getPokemonList(0, 800);
                    return pokemonList;
                } catch(Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(NamedApiResourceList result) {
                if(result == null) return;
            }

        };

        task.execute();
    }

}
