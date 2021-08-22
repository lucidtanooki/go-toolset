package de.limbusdev.gotoolset;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TypeInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_info);

        Intent intent = getIntent();
        int type = getIntent().getIntExtra("type",1);

        int[] pokes = PokeInfo.getInstance().types[type];
        String[] pokeNames = new String[pokes.length];

        int j=0;
        for(int i : pokes) {
            pokeNames[j] = getResources().getStringArray(R.array.poke_list)[i-1];
            j++;
        }

        ListView listView = (ListView) findViewById(R.id.pokeListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pokeNames);
        listView.setAdapter(adapter);

        ImageView icon = (ImageView) findViewById(R.id.imgTypeInfoIcon);
        int drId;
        switch(intent.getIntExtra("type",1)) {
            case 1: drId = R.drawable.type_normal; break;
            case 2: drId = R.drawable.type_fighting; break;
            case 3: drId = R.drawable.type_flying; break;
            case 4: drId = R.drawable.type_poison; break;
            case 5: drId = R.drawable.type_ground; break;
            case 6: drId = R.drawable.type_rock; break;
            case 7: drId = R.drawable.type_bug; break;
            case 8: drId = R.drawable.type_ghost; break;
            case 9: drId = R.drawable.type_steel; break;
            case 10: drId = R.drawable.type_fire; break;
            case 11: drId = R.drawable.type_water; break;
            case 12: drId = R.drawable.type_grass; break;
            case 13: drId = R.drawable.type_electric; break;
            case 14: drId = R.drawable.type_psychic; break;
            case 15: drId = R.drawable.type_ice; break;
            case 16: drId = R.drawable.type_dragon; break;
            case 17: drId = R.drawable.type_dark; break;
            case 18: drId = R.drawable.type_fairy; break;
            default: drId = R.drawable.type_any; break;
        }
        icon.setImageDrawable(getResources().getDrawable(drId));

        String colId = getResources().getStringArray(R.array.type_colors)[type];
        icon.setBackgroundColor(Color.parseColor(colId));

        TextView textView = (TextView) findViewById(R.id.textViewTypeInfoTitle);
        textView.setText(getResources().getStringArray(R.array.type_list)[type]);
        textView.setBackgroundColor(Color.parseColor(colId));
    }
}