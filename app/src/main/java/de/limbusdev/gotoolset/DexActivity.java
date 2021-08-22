package de.limbusdev.gotoolset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.HashMap;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource;
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;

public class DexActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = DexActivity.class.getSimpleName();

    private int currentStart = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_type_dex_1);
        spinner.setOnItemSelectedListener(this);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5186073457003228~4205954395");

        AdView mAdView = (AdView) findViewById(R.id.adView_dex);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        refreshList(1,151);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(position) {
            case 0:     // RGBY
                currentStart = 1;
                refreshList(1,151);
                break;
            case 1:     // GSC
                currentStart = 152;
                refreshList(152,251);
                break;
            case 2:     // RSS
                currentStart = 252;
                refreshList(252,387);
                break;
            case 3:     // DPP
                currentStart = 388;
                refreshList(388,493);
                break;
            case 4:     // SW SW2
                currentStart = 494;
                refreshList(494,649);
                break;
            case 5:     // XY
                currentStart = 650;
                refreshList(650,721);
                break;
            case 6:     // SM
                currentStart = 722;
                refreshList(722,802);
                break;
            default:    // ALL
                currentStart = 1;
                refreshList(1,802);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO
    }

    public void showPokeDetails(PokemonSpecies pokemonSpecies) {
        Log.d(TAG, pokemonSpecies.getName());

    }

    public void showPokeDetails(int id) {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void refreshList(int from, int to) {
        String[] allNames = getResources().getStringArray(R.array.all_pokemon);
        String[] filteredNames = new String[to-from];
        for(int i=from; i<to; i++) {
            filteredNames[i-from] = allNames[i-1];
        }
        ListView list = list=(ListView)findViewById(R.id.pokeListView);

        ArrayAdapter<String> adapter = new ListViewWithIndex(this, from, filteredNames);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showPokeDetails(currentStart+position);
                }
            }
        );
    }

    public AsyncPokeApi getAsyncPokeApi() {
        return getAsyncPokeApi();
    }
}
