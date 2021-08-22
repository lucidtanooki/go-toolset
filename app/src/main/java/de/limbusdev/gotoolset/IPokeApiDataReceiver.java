package de.limbusdev.gotoolset;

import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;

/**
 * IPokeApiDataReceiver
 *
 * @author Georg Eckert 2017
 */

public interface IPokeApiDataReceiver {
    void receivePokemonSpeciesData(PokemonSpecies data);
}
