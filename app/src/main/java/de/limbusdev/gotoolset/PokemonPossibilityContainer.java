package de.limbusdev.gotoolset;


import java.util.Observable;
import java.util.Observer;

/**
 * Created by georg on 12.10.16.
 */

public class PokemonPossibilityContainer extends Observable {
    private int currMaxPossiblePokeLvl = 1;
    public static final int minPossiblePokeLvl = 1;
    public static final int maxPossiblePokeLvl = 80;
    private int currPokeLvl = 1;
    private int currMaxPossibleHP=10;
    private int currMinPossibleHP=10;
    public static final int minPossibleHP = 10;
    private int currMaxPossibleCP = 10;
    private int currMinPossibleCP = 10;
    public static final int minPossibleCP = 10;
    private int currStarDustCostForNxtLvl = 200;
    public static final int minStarDustCostForNxtLvl = 200;
    private int currPokeId = 1;
    private int currTrainerLvl = 1;
    public static final int minPossibleTrainerLvl = 1;
    public static final int maxPossibleTrainerLvl = 40;
    private Pokemon currPokemon;

    public PokemonPossibilityContainer(Pokemon poke) {
        this.currPokeId = poke.getID();
        currPokeLvl = 1;
        currPokemon = poke;
    }

    public PokemonPossibilityContainer(Pokemon poke, int currPokeLvl) {
        this.currPokeLvl = currPokeLvl;
        this.currPokemon = poke;
        this.currPokeId  = poke.ID;
    }

    public int getCurrPokeLvl() {
        return currPokeLvl;
    }

    public void setCurrPokeLvl(int currPokeLvl) {
        this.currPokeLvl = currPokeLvl;
        setChanged();
        notifyObservers(this);
    }

    public int getCurrMaxPossiblePokeLvl() {
        return currMaxPossiblePokeLvl;
    }

    public void setCurrMaxPossiblePokeLvl(int currMaxPossiblePokeLvl) {
        this.currMaxPossiblePokeLvl = currMaxPossiblePokeLvl;
        setChanged();
        notifyObservers(this);
    }

    public static int getMinPossiblePokeLvl() {
        return minPossiblePokeLvl;
    }

    public static int getMaxPossiblePokeLvl() {
        return maxPossiblePokeLvl;
    }

    public int getCurrMaxPossibleHP() {
        return currMaxPossibleHP;
    }

    public void setCurrMaxPossibleHP(int currMaxPossibleHP) {
        this.currMaxPossibleHP = currMaxPossibleHP;
        setChanged();
        notifyObservers(this);
    }

    public static int getMinPossibleHP() {
        return minPossibleHP;
    }

    public int getCurrMaxPossibleCP() {
        return currMaxPossibleCP;
    }

    public void setCurrMaxPossibleCP(int currMaxPossibleCP) {
        this.currMaxPossibleCP = currMaxPossibleCP;
        setChanged();
        notifyObservers(this);
    }

    public static int getMinPossibleCP() {
        return minPossibleCP;
    }

    public int getCurrStarDustCostForNxtLvl() {
        return currStarDustCostForNxtLvl;
    }

    public void setCurrStarDustCostForNxtLvl(int currStarDustCostForNxtLvl) {
        this.currStarDustCostForNxtLvl = currStarDustCostForNxtLvl;
        setChanged();
        notifyObservers(this);
    }

    public static int getMinStarDustCostForNxtLvl() {
        return minStarDustCostForNxtLvl;
    }

    public int getCurrPokeId() {
        return currPokeId;
    }

    public void setCurrPokeId(int currPokeId) {
        this.currPokeId = Math.max(1,currPokeId);
        setChanged();
        notifyObservers(this);
    }

    public int getCurrTrainerLvl() {
        return currTrainerLvl;
    }

    public void setCurrTrainerLvl(int currTrainerLvl) {
        this.currTrainerLvl = currTrainerLvl;
    }

    public int getCurrMinPossibleHP() {
        return currMinPossibleHP;
    }

    public void setCurrMinPossibleHP(int currMinPossibleHP) {
        this.currMinPossibleHP = currMinPossibleHP;
    }

    public int getCurrMinPossibleCP() {
        return currMinPossibleCP;
    }

    public void setCurrMinPossibleCP(int currMinPossibleCP) {
        this.currMinPossibleCP = currMinPossibleCP;
    }

    public Pokemon getCurrPokemon() {
        return currPokemon;
    }

    public void setCurrPokemon(Pokemon currPokemon) {
        this.currPokemon = currPokemon;
    }

    public int getCurrCPRange() {
        return currMaxPossibleCP - currMinPossibleCP;
    }
}
