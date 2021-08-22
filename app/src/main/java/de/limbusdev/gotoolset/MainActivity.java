package de.limbusdev.gotoolset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ADVERTISEMENTS
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5186073457003228~4205954395");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Log.d(TAG, "Created Ad View");

        PokeDataBase db = PokeDataBase.getInstance();
        String json = null;
        try {
            InputStream is = getAssets().open("pokemon.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch(Exception e) {
            e.printStackTrace();
        }
        db.loadPokemon(json);
    }

    // ----------------------------------------------------------------
    /* Callbacks */
    public void openTypeGuide(View view) {
        Intent intent = new Intent(this, TypeGuideActivity.class);
        startActivity(intent);
    }

    public void openDex(View view) {
        Intent intent = new Intent(this, DexActivity.class);
        startActivity(intent);
    }

    public void openAbout(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

}
