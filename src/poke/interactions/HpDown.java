/* Yahya, Sahil, Jaiveer
 * 2023-05-26
 * Special Interactions. Basically debuffs a pokemon by manipulating it's health.
 */

package poke.interactions;

import poke.pokemon.Pokemon;
import poke.pokemon.PokemonDecorator;

public class HpDown extends PokemonDecorator {
	/**
	 * Decreases the hp of the Pokemon
	 * 
	 * @param pokemon - the pokemon that's getting its hp decreased
	 */
	public HpDown(Pokemon pokemon) {
		super(pokemon, "-Hp", -1);
	}
}