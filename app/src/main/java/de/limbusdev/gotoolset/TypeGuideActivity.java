package de.limbusdev.gotoolset;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Arrays;


public class TypeGuideActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public final static int ANY=0, NORMAL=1, FIGHT=2, FLYING=3, POISON=4, GROUND=5,
        ROCK=6, BUG=7, GHOST=8, STEEL=9, FIRE=10, WATER=11, GRASS=12, ELECTRO=13,
        PSYCHIC=14, ICE=15, DRAGON=16, DARK=17, FAIRY=18;

    public final static Float[][] effectivenesses =
        {
            {1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f},
            {1f,1f,1f,1f,1f,1f,.5f,1f,0f,.5f,1f,1f,1f,1f,1f,1f,1f,1f,1f},
            {1f,2f,1f,.5f,.5f,1f,2f,.5f,0f,2f,1f,1f,1f,1f,.5f,2f,1f,2f,.5f},
            {1f,1f,2f,1f,1f,1f,.5f,2f,1f,.5f,1f,1f,2f,.5f,1f,1f,1f,1f,1f},
            {1f,1f,1f,1f,.5f,.5f,.5f,1f,.5f,0f,1f,1f,2f,1f,1f,1f,1f,1f,2f},
            {1f,1f,1f,0f,2f,1f,2f,.5f,1f,2f,2f,1f,.5f,2f,1f,1f,1f,1f,1f},
            {1f,1f,.5f,2f,1f,.5f,1f,2f,1f,.5f,2f,1f,1f,1f,1f,2f,1f,1f,1f},
            {1f,1f,.5f,.5f,.5f,1f,1f,1f,.5f,.5f,.5f,1f,2f,1f,2f,1f,1f,2f,.5f},
            {1f,0f,1f,1f,1f,1f,1f,1f,2f,1f,1f,1f,1f,1f,2f,1f,1f,.5f,1f},
            {1f,1f,1f,1f,1f,1f,2f,1f,1f,.5f,.5f,.5f,1f,.5f,1f,2f,1f,1f,2f},
            {1f,1f,1f,1f,1f,1f,.5f,2f,1f,2f,.5f,.5f,2f,1f,1f,2f,.5f,1f,1f},
            {1f,1f,1f,1f,1f,2f,2f,1f,1f,1f,2f,.5f,.5f,1f,1f,1f,.5f,1f,1f},
            {1f,1f,1f,.5f,.5f,2f,2f,.5f,1f,.5f,.5f,2f,.5f,1f,1f,1f,.5f,1f,1f},
            {1f,1f,1f,2f,1f,0f,1f,1f,1f,1f,1f,2f,.5f,.5f,1f,1f,.5f,1f,1f},
            {1f,1f,2f,1f,2f,1f,1f,1f,1f,.5f,1f,1f,1f,1f,.5f,1f,1f,0f,1f},
            {1f,1f,1f,2f,1f,2f,1f,1f,1f,.5f,.5f,.5f,2f,1f,1f,.5f,2f,1f,1f},
            {1f,1f,1f,1f,1f,1f,1f,1f,1f,.5f,1f,1f,1f,1f,1f,1f,2f,1f,0f},
            {1f,1f,.5f,1f,1f,1f,1f,1f,2f,1f,1f,1f,1f,1f,2f,1f,1f,.5f,.5f},
            {1f,1f,2f,1f,.5f,1f,1f,1f,1f,.5f,.5f,1f,1f,1f,1f,1f,2f,2f,1f}
        };

    public final static String[] types = {
        "Normal", "Fight", "Flying", "Poison", "Ground", "Rock", "Bug",
        "Ghost", "Steel", "Fire", "Water", "Grass", "Electro", "Psychic", "Ice",
        "Dragon", "Dark", "Fairy"
    };

    public final static Integer[] typeImgIds = {
        R.drawable.type_any,
        R.drawable.type_normal,
        R.drawable.type_fighting,
        R.drawable.type_flying,
        R.drawable.type_poison,
        R.drawable.type_ground,
        R.drawable.type_rock,
        R.drawable.type_bug,
        R.drawable.type_ghost,
        R.drawable.type_steel,
        R.drawable.type_fire,
        R.drawable.type_water,
        R.drawable.type_grass,
        R.drawable.type_electric,
        R.drawable.type_psychic,
        R.drawable.type_ice,
        R.drawable.type_dragon,
        R.drawable.type_dark,
        R.drawable.type_fairy
    };

    private Integer[] currentTypeList1, currentTypeList2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_guide);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_type_1);
        spinner.setOnItemSelectedListener(this);

        spinner = (Spinner) findViewById(R.id.spinner_type_2);
        spinner.setOnItemSelectedListener(this);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5186073457003228~4205954395");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

        // Empty Arrays for storing Type ID and Efficiency in Attack and Defense
        ArrayMap<Integer,Float> typesAndEffsAtt, typesAndEffsAtt2;
        ArrayMap<Integer,Float> typesAndEffsDef, typesAndEffsDef2;

        // Get chosen positions of both spinners
        Spinner spinner = (Spinner) findViewById(R.id.spinner_type_1);
        int type = spinner.getSelectedItemPosition();
                spinner = (Spinner) findViewById(R.id.spinner_type_2);
        int type2 = spinner.getSelectedItemPosition();


        // DEFENSE +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        ArrayList<Integer> attTypes  = new ArrayList<>();
        ArrayList<String>  defValues = new ArrayList<>();

        typesAndEffsDef  = getTypeDefense(type);
        typesAndEffsDef2 = getTypeDefense(type2);
        System.out.println(typesAndEffsDef);
        System.out.println(typesAndEffsDef2);

        // Filter, take only values different from 1.0
        for(Integer i : typesAndEffsDef.keySet()) {
            Float combinedDef = typesAndEffsDef.get(i);
            if(type != type2) combinedDef *= typesAndEffsDef2.get(i);
            if(Math.abs(combinedDef - 1.0f) > 0.0001f) {
                attTypes.add(i);
                if(combinedDef < 10) {
                    defValues.add(combinedDef.toString());
                } else {
                    defValues.add("\u221E");
                }
            }
        }

        currentTypeList1 = new Integer[attTypes.size()];
        attTypes.toArray(currentTypeList1);
        System.out.println(attTypes);
        System.out.println(defValues);

        String[]  effStringArray = new String  [defValues.size()];
        Integer[] imgIdArray     = new Integer [attTypes.size()];

        // Fill Arrays
        int n=0;
        for(Integer m : attTypes) {
            effStringArray[n] = defValues.get(n);
            imgIdArray[n]     = typeImgIds[m];
            n++;
        }

        ListView list;
        ListViewWithImages lAdapter = new ListViewWithImages(this, effStringArray, imgIdArray);
        list=(ListView)findViewById(R.id.typeListView2);
        list.setAdapter(lAdapter);

        list.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showHelpClickedElement(position,false);
                }
            }
        );


        // ATTACK ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        ArrayList<Integer> defTypes  = new ArrayList<>();
        ArrayList<String>  attValues = new ArrayList<>();

        if(type == type2 || type == ANY || type2 == ANY) {
            typesAndEffsAtt = getTypeAttack(type);
            typesAndEffsAtt2 = getTypeAttack(type2);
            System.out.println(typesAndEffsAtt);
            System.out.println(typesAndEffsAtt2);

            // Filter, take only values different from 1.0
            for (Integer i : typesAndEffsAtt.keySet()) {
                Float combinedAtt = typesAndEffsAtt.get(i);
                if (type != type2) combinedAtt *= typesAndEffsAtt2.get(i);
                if (Math.abs(combinedAtt - 1.0f) > 0.0001f) {
                    defTypes.add(i);
                    attValues.add(combinedAtt.toString());
                }
            }

            System.out.println(defTypes);
            System.out.println(attValues);
        }

        currentTypeList2 = new Integer[defTypes.size()];
        defTypes.toArray(currentTypeList2);

        effStringArray = new String  [attValues.size()];
        imgIdArray     = new Integer [defTypes.size()];

        // Fill Arrays
        int k=0;
        for(Integer m : defTypes) {
            effStringArray[k] = attValues.get(k);
            imgIdArray[k]     = typeImgIds[m];
            k++;
        }

        lAdapter = new ListViewWithImages(this, effStringArray, imgIdArray);
        list=(ListView)findViewById(R.id.typeListView1);
        list.setAdapter(lAdapter);

        list.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showHelpClickedElement(position,true);
                }
            }
        );

    }

    /**
     * Assembles an array containing the defense values of the given type, according to different
     * attacking types.
     * @param defType type of the monster in defense
     * @return        ArrayMap<AttackTypeID,Types Defense-Value against this type>
     */
    public ArrayMap<Integer,Float> getTypeDefense(int defType) {
        ArrayMap<Integer,Float> typeDefValues = new ArrayMap<>();
        // Calculate Defense of given type
            for(int attType=1; attType<=18; attType++) {
                float efficiency = effectivenesses[attType][defType];
                float defenseValue;
                if(Math.abs(efficiency) < 0.0001f) {
                    // Efficiency of 0 means infinite defense
                    defenseValue = 100f;
                } else {
                    // Defense is 1/efficiency
                    defenseValue = 1/efficiency;
                }
                typeDefValues.put(attType,defenseValue);
            }

        return typeDefValues;
    }

    public ArrayMap<Integer,Float> getTypeAttack(int attType) {
        ArrayMap<Integer,Float> typeAttValues = new ArrayMap<>();

            for(int defType=1; defType<=18; defType++) {
                float efficiency = effectivenesses[attType][defType];
                typeAttValues.put(defType,efficiency);
            }

        return typeAttValues;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO
    }

    // Callbacks
    public void openHelpAttack(View view) {
        Intent intent = new Intent(this, HelpAttackActivity.class);
        startActivity(intent);
    }

    public void openHelpDefense(View view) {
        Intent intent = new Intent(this, HelpDefenseActivity.class);
        startActivity(intent);
    }

    public void showHelpClickedElement(int position, boolean side) {

        Intent intent = new Intent(this, TypeInfoActivity.class);
        if(!side) intent.putExtra("type", currentTypeList1[position]);
        else      intent.putExtra("type", currentTypeList2[position]);
        startActivity(intent);

    }
}
