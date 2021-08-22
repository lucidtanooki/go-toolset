package de.limbusdev.gotoolset;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import me.sargunvohra.lib.pokekotlin.model.ApiResource;
import me.sargunvohra.lib.pokekotlin.model.EvolutionChain;
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;

public class DetailViewActivity extends AppCompatActivity implements IPokeApiDataReceiver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5186073457003228~4205954395");

        AdView mAdView = (AdView) findViewById(R.id.adView_dex);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        int id = getIntent().getIntExtra("id", 1);
        AsyncPokeApi.getInstance().fetchPokemonSpecies(id,this);

        TextView hint = (TextView) findViewById(R.id.textViewWarning);
        hint.setText("Hint: Please wait, loading data from PokeAPI.co");
    }

    @Override
    public void receivePokemonSpeciesData(PokemonSpecies data) {
        TextView textID = (TextView) findViewById(R.id.textViewId);
        int id = data.getId();
        textID.setText(Integer.toString(id));

        TextView textName = (TextView) findViewById(R.id.textViewName);
        String[] names = getResources().getStringArray(R.array.all_pokemon);
        textName.setText(names[id-1]);

        TextView hint = (TextView) findViewById(R.id.textViewWarning);
        hint.setText("");

    }

}
