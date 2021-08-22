package de.limbusdev.gotoolset;

import android.util.JsonReader;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import me.sargunvohra.lib.pokekotlin.util.ApiResourceAdapter;

/**
 * PokeDataBase
 *
 * @author Georg Eckert 2017
 */

public class PokeDataBase {

    private static PokeDataBase instance;

    private PokeDataBase() {

    }

    public static PokeDataBase getInstance() {
        if(instance == null) instance = new PokeDataBase();
        return instance;
    }

    public void loadPokemon(String json) {

    }



}
