package de.limbusdev.gotoolset;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by georg on 10.10.16.
 */
public class PokeDataOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = PokeDataOpenHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "poke_data.db";

    public static final String TABLE_POKE_LIST = "poke_base_values";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ATT = "base_attack";
    public static final String COLUMN_DEF = "base_defense";
    public static final String COLUMN_STA = "base_stamina";

    private PokemonDataSource source;

    private static final String SQL_CREATE =
        "CREATE TABLE " + TABLE_POKE_LIST +
            "(" + COLUMN_ID  + " INTEGER PRIMARY KEY, " +
                  COLUMN_ATT + " INTEGER NOT NULL, " +
                  COLUMN_DEF + " INTEGER NOT NULL, " +
                  COLUMN_STA + " INTEGER NOT NULL);";



    public PokeDataOpenHelper(Context context, PokemonDataSource source) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "DbHelper created database " + getDatabaseName());
        this.source = source;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE);
            Log.d(TAG, "Created table with command: " + SQL_CREATE);
            source.initDatabase(db);
        } catch (Exception e) {
            Log.e(TAG, "Database creation failed: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO
    }
}
