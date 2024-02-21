/* Yahya, Sahil, Jaiveer
 * 2023-05-26
 * Special Interactions. Basically buffs a pokemon by manipulating it's health.
 */

package poke.interactions;

import poke.pokemon.Pokemon;
import poke.pokemon.PokemonDecorator;

public class HpUp extends PokemonDecorator {
	/**
	 * Increases the hp of the Pokemon
	 * 
	 * @param pokemon - the pokemon that's getting its hp increased
	 */
	public HpUp(Pokemon pokemon) {
		super(pokemon, "+HP", (int) (Math.random() * 2) + 1);
	}
}