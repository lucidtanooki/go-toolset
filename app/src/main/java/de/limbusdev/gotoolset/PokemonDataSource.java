package de.limbusdev.gotoolset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by georg on 10.10.16.
 */
public class PokemonDataSource {

    private static final String TAG = PokemonDataSource.class.getSimpleName();

    private String[] columns =  {
        PokeDataOpenHelper.COLUMN_ID,
        PokeDataOpenHelper.COLUMN_ATT,
        PokeDataOpenHelper.COLUMN_DEF,
        PokeDataOpenHelper.COLUMN_STA
    };

    private SQLiteDatabase database;
    private PokeDataOpenHelper dbHelper;



    public PokemonDataSource(Context context) {
        Log.d(TAG, "DataSource creates dbHelper");
        dbHelper = new PokeDataOpenHelper(context, this);
    }

    public void open() {
        Log.d(TAG, "Asking for reference on database");
        database = dbHelper.getWritableDatabase();
        Log.d(TAG, "Received database. Path is: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(TAG, "Closed database with dbHelper");
    }

    public Pokemon createPokemon(int id, int att, int def, int stam) {
        ContentValues values = new ContentValues();
        values.put(PokeDataOpenHelper.COLUMN_ID, id);
        values.put(PokeDataOpenHelper.COLUMN_ATT, att);
        values.put(PokeDataOpenHelper.COLUMN_DEF, def);
        values.put(PokeDataOpenHelper.COLUMN_STA, stam);

        long insertId = database.insert(PokeDataOpenHelper.TABLE_POKE_LIST, null, values);

        Cursor cursor = database.query(PokeDataOpenHelper.TABLE_POKE_LIST, columns, PokeDataOpenHelper.COLUMN_ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        Pokemon pokemon = cursorToPokemon(cursor);
        cursor.close();

        return pokemon;
    }

    public Pokemon cursorToPokemon(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(PokeDataOpenHelper.COLUMN_ID);
        int idAttack = cursor.getColumnIndex(PokeDataOpenHelper.COLUMN_ATT);
        int idDefense = cursor.getColumnIndex(PokeDataOpenHelper.COLUMN_DEF);
        int idStamina = cursor.getColumnIndex(PokeDataOpenHelper.COLUMN_STA);

        Pokemon pokemon = new Pokemon(cursor.getInt(idIndex), cursor.getInt(idAttack), cursor.getInt(idDefense), cursor.getInt(idStamina));

        return pokemon;
    }

    public List<Pokemon> getAllPokemon() {
        List<Pokemon> pokeList = new ArrayList<>();

        Cursor cursor = database.query(PokeDataOpenHelper.TABLE_POKE_LIST, columns, null, null, null, null, null);

        cursor.moveToFirst();
        Pokemon pokemon;

        while(!cursor.isAfterLast()) {
            pokemon = cursorToPokemon(cursor);
            pokeList.add(pokemon);
            //Log.d(TAG, "ID: " + pokemon.getID() + ", Content: " + pokemon.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return pokeList;
    }

    public Pokemon getPokemonById(int id) {
        return getAllPokemon().get(id-1);
    }

    public void initDatabase(SQLiteDatabase db) {
        try {
            database = db;
            for (int[] i : PokeInfo.poke_base_values) {
                createPokemon(i[0], i[1], i[2], i[3]);
            }
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
