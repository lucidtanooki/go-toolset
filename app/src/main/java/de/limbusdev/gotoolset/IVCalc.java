package de.limbusdev.gotoolset;

import android.util.Log;

/**
 * Created by georg on 11.10.16.
 */

public class IVCalc {

    public static final String TAG = IVCalc.class.getSimpleName();

    public static PokemonPossibilityContainer populatePossibilityContainer(
        PokemonPossibilityContainer container) {

        container.setCurrMaxPossiblePokeLvl(
            Math.round(getMaxPokeLevelForTrainerLevel(container.getCurrTrainerLvl())*2-1)
        );
        container.setCurrStarDustCostForNxtLvl(
            getStarDustByPokeLvl(container.getCurrPokeLvl())
        );
        container.setCurrMinPossibleCP(
            getMinCPByPokeLvl(container.getCurrPokemon(), container.getCurrPokeLvl())
        );
        container.setCurrMaxPossibleCP(
            getMaxCPByPokeLvl(container.getCurrPokemon(), container.getCurrPokeLvl())
        );
        container.setCurrMinPossibleHP(
            getMinHPbyPokelvl(container.getCurrPokemon(), container.getCurrPokeLvl())
        );
        container.setCurrMaxPossibleHP(
            getMaxHPbyPokelvl(container.getCurrPokemon(), container.getCurrPokeLvl())
        );

        return container;
    }


    public float percentInRange(int num, int min, int max) {
        return ((num - min)*100)/(max-min);
    }

    public float calcIndStamina(int hp, int baseStamina, float ECpM) {
        return 0;
    }

    public float getAttackPercentage(int indAtk, int indDef) {
        return Math.round((indAtk+indDef)/30f * 100f);
    }

    /**
     * returns the maximum CP a pokemon can reach at the given pokemon level
     * pokemon level = arc position
     * @param poke
     * @param pokelvl
     * @return
     */
    public static int getMaxCPByPokeLvl(Pokemon poke, int pokelvl) {

        float ECpM = PokeInfo.levelToCpm[pokelvl-1];    // Index is Pokemon level - 1
        long maxCP=Math.max(10,Math.round(Math.floor((poke.baseAttack + 15f)*Math.pow(poke.baseDefense + 15f,0.5f)*Math.pow(poke.baseStamina + 15f,0.5f)*Math.pow(ECpM,2f)/10f)));

        Log.d(TAG, "Pokemon with ID " + poke.getID() + " can reach a maximum CP of " + maxCP + " at level " + ((pokelvl*1f)/2f+0.5f));

        return (int) maxCP;
    }

    public static int getMinCPByPokeLvl(Pokemon poke, int pokelvl) {
        float ECpM = PokeInfo.levelToCpm[pokelvl-1];
        long minCP=Math.max(10,Math.round(Math.floor((poke.baseAttack)*Math.pow(poke.baseDefense,0.5f)*Math.pow(poke.baseStamina,0.5f)*Math.pow(ECpM,2f)/10f)));

        Log.d(TAG, "Pokemon with ID " + poke.getID() + " can reach a minimum CP of " + minCP + " at level " + ((pokelvl*1f)/2f+0.5f));

        return (int) minCP;
    }

    public static int getMinHPbyPokelvl(Pokemon poke, int pokelvl) {
        float ECpM = PokeInfo.levelToCpm[pokelvl-1];
        int minHP = Math.max(Math.round(ECpM * (poke.baseStamina)),10);
        return minHP;
    }

    public static int getMaxHPbyPokelvl(Pokemon poke, int pokelvl) {
        float ECpM = PokeInfo.levelToCpm[pokelvl-1];
        int maxHP = Math.max(Math.round(ECpM * (poke.baseStamina + 15)),10);
        return maxHP;
    }

    public static int getStarDustByPokeLvl(int pokeLevel) {
        int starDust = PokeInfo.dustCosts[pokeLevel-1];

        Log.d(TAG, "Chosen Pokemon needs  " + starDust + " star dust to reach level " + ((pokeLevel*1f)/2f+1f));

        return starDust;
    }

    public static float getMaxPokeLevelForTrainerLevel(int trainerLvl) {
        float maxPokeLvl = Math.min(trainerLvl + 1.5f, 40.5f);
        Log.d(TAG, "At trainer level " + trainerLvl + " pokemon can reach a maximum level of " + maxPokeLvl);

        return maxPokeLvl;
    }

    public static int progressToTrainerLvl(int progress) {
        return Math.max(1,Math.round(progress/2.5f));
    }

    public static int trainerLvlToProgress(int trainerLvl) {
        return Math.round(trainerLvl*2.5f);
    }

    public static int progressToCP(int progress, int currMinCp, int currMaxCp) {
        return Math.max(10,Math.round(progress/(100f/(currMaxCp-currMinCp))) + currMinCp);
    }

    public static int progressToHP(int progress, int currMinHP, int currMaxHP) {
        return Math.max(10,progressToCP(progress, currMinHP, currMaxHP));
    }

    public static int cpToProgress(int cp, int currMinCp, int currMaxCp) {
        int range = currMaxCp - currMinCp;
        int partOfCp = cp - currMinCp;
        return Math.round(100f*partOfCp/range);
    }

    public static int progressToPokeLvl(int progress, int currMaxPokeLvl) {
        return Math.max(1,Math.round((1f*progress*currMaxPokeLvl)/100f));
    }
}
