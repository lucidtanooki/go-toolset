package de.limbusdev.gotoolset;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.devadvance.circularseekbar.CircularSeekBar;

import java.util.Observable;
import java.util.Observer;

public class IVCalcActivity extends AppCompatActivity
    implements AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener, CircularSeekBar.OnCircularSeekBarChangeListener, Observer {

    private static final String TAG = IVCalcActivity.class.getSimpleName();

    private PokemonDataSource pokeDataSource;
    private PokemonPossibilityContainer possibilities;

    private int currTrainerLvlProgress, currCPProgress, currHPProgress, currSDProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivcalc);

        // DATABASE
        pokeDataSource = new PokemonDataSource(this);

        // Initialize container
        pokeDataSource.open();
        Pokemon startPoke = pokeDataSource.getPokemonById(1);
        pokeDataSource.close();
        possibilities = new PokemonPossibilityContainer(startPoke);

        // Restore Preferences
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        possibilities.setCurrTrainerLvl(settings.getInt("trainer_lvl", 10));

        IVCalc.populatePossibilityContainer(possibilities);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_choose_poke);
        spinner.setSelection(1);
        spinner.setOnItemSelectedListener(this);

        possibilities.addObserver(this);
        initSeekBars();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Save Settings
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("trainer_lvl", possibilities.getCurrTrainerLvl());
        editor.apply();
    }

    private void initSeekBars() {
        resetSeekbars();

        // Trainer Level
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_lvl);
        seekBar.setProgress(IVCalc.trainerLvlToProgress(possibilities.getCurrTrainerLvl()));
        seekBar.setOnSeekBarChangeListener(this);

        seekBar = (SeekBar) findViewById(R.id.seekbar_sd);
        seekBar.setOnSeekBarChangeListener(this);

        CircularSeekBar circularSeekBar = (CircularSeekBar) findViewById(R.id.circularSeekBar1);
        circularSeekBar.setOnSeekBarChangeListener(this);

        seekBar = (SeekBar) findViewById(R.id.seekbar_cp);
        seekBar.setOnSeekBarChangeListener(this);

        seekBar = (SeekBar) findViewById(R.id.seekbar_hp);
        seekBar.setOnSeekBarChangeListener(this);
    }

    /**
     * Set all Seek Bars (not the trainer level) to 50%
     */
    private void resetSeekbars() {
        currCPProgress = 50;
        currHPProgress = 50;
        currSDProgress = 50;

        refreshUI();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Button button = (Button) findViewById(R.id.button_calc_iv);
        button.setClickable(true);

        resetSeekbars();

        possibilities.setCurrPokeId(i);

        resetSeekbars();
        refreshOnPokemonSelection();
    }

    private void refreshUI() {
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_cp);
        seekBar.setProgress(currCPProgress);
        seekBar = (SeekBar) findViewById(R.id.seekbar_hp);
        seekBar.setProgress(currHPProgress);
        seekBar = (SeekBar) findViewById(R.id.seekbar_sd);
        seekBar.setProgress(currSDProgress);
        CircularSeekBar cirkSeek = (CircularSeekBar) findViewById(R.id.circularSeekBar1);
        cirkSeek.setProgress(currSDProgress);

        ((TextView) findViewById(R.id.text_minIV)).setText(Integer.toString(possibilities.getCurrMinPossibleCP()));
        ((TextView) findViewById(R.id.text_maxIV)).setText(Integer.toString(possibilities.getCurrMaxPossibleCP()));
        ((TextView) findViewById(R.id.text_lvl)).setText(Integer.toString(possibilities.getCurrTrainerLvl()));

        // CP
        TextView text = (TextView) findViewById(R.id.text_cp);
        text.setText(Integer.toString(
            IVCalc.progressToCP(currCPProgress, possibilities.getCurrMinPossibleCP(), possibilities.getCurrMaxPossibleCP())
        ));

        // HP
        text = (TextView) findViewById(R.id.text_hp);
        text.setText(Integer.toString(
            IVCalc.progressToHP(currHPProgress, possibilities.getCurrMinPossibleHP(), possibilities.getCurrMaxPossibleHP())
        ));

        // SD
        text = (TextView) findViewById(R.id.text_sd);
        text.setText(Integer.toString(
            possibilities.getCurrStarDustCostForNxtLvl()
        ));
    }

    private void refreshModelFromUI() {
        SeekBar trainerLvlBar = ((SeekBar) findViewById(R.id.seekbar_lvl));
        currTrainerLvlProgress = trainerLvlBar.getProgress();

        SeekBar cpBar = ((SeekBar) findViewById(R.id.seekbar_cp));
        currCPProgress = cpBar.getProgress();

        SeekBar hpBar = ((SeekBar) findViewById(R.id.seekbar_hp));
        currHPProgress = hpBar.getProgress();

        CircularSeekBar sdBar = ((CircularSeekBar) findViewById(R.id.circularSeekBar1));
        currSDProgress = sdBar.getProgress();

        Spinner pokeChoice = ((Spinner) findViewById(R.id.spinner_choose_poke));
        possibilities.setCurrPokeId(pokeChoice.getSelectedItemPosition());
        pokeDataSource.open();
        possibilities.setCurrPokemon(pokeDataSource.getPokemonById(possibilities.getCurrPokeId()));
        pokeDataSource.close();

        possibilities.setCurrTrainerLvl(IVCalc.progressToTrainerLvl(currTrainerLvlProgress));

        IVCalc.populatePossibilityContainer(possibilities);

        possibilities.setCurrPokeLvl(IVCalc.progressToPokeLvl(currSDProgress, possibilities.getCurrMaxPossiblePokeLvl()));
    }

    private void refreshOnPokemonSelection() {
        pokeDataSource.open();
        Pokemon poke = pokeDataSource.getAllPokemon().get(possibilities.getCurrPokeId());
        pokeDataSource.close();
        possibilities.setCurrPokemon(poke);

        IVCalc.populatePossibilityContainer(possibilities);

        refreshModelFromUI();
        refreshUI();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Button button = (Button) findViewById(R.id.button_calc_iv);
        button.setClickable(false);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        refreshModelFromUI();
        refreshUI();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO
    }

    @Override
    public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {
        refreshModelFromUI();
        refreshUI();
    }

    @Override
    public void onStopTrackingTouch(CircularSeekBar seekBar) {
        // TODO
    }

    @Override
    public void onStartTrackingTouch(CircularSeekBar seekBar) {
        // TODO
    }

    public void onCalculateIV(View view) {
        TextView text = (TextView) findViewById(R.id.text_acerageIV);
        long att2def=0;

        if(IVCalc.progressToCP(currCPProgress, possibilities.getCurrMinPossibleCP(), possibilities.getCurrMaxPossibleCP()) == 10 ||
           IVCalc.progressToHP(currHPProgress, possibilities.getCurrMinPossibleHP(), possibilities.getCurrMaxPossibleHP())== 10) {
            text.setText("Unknown");
        } else {
            int currCP = IVCalc.progressToCP(currCPProgress, possibilities.getCurrMinPossibleCP(), possibilities.getCurrMaxPossibleCP());
            int range = possibilities.getCurrCPRange();
            int currRange = currCP - possibilities.getCurrMinPossibleCP();
            att2def = Math.round(((float)(currRange))/((float)(range))*100f);
            text.setText(Long.toString(att2def) + " %");
        }

    }

    @Override
    public void update(Observable observable, Object o) {
        Log.d(TAG, "Got notified by possibility container");
    }
}
