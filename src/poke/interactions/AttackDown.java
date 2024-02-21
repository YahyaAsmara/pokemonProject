/* Yahya, Sahil, Jaiveer
 * 2023-05-26
 * Special Interactions. Basically debuffs a pokemon by manipulating it's damage.
 */

package poke.interactions;

import poke.pokemon.Pokemon;
import poke.pokemon.PokemonDecorator;

public class AttackDown extends PokemonDecorator {
	/**
	 * Decreases the attack of the Pokemon
	 * 
	 * @param p - the pokemon that's getting its hp decreased
	 */
	public AttackDown(Pokemon pokemon) {
		super(pokemon, "-ATK", 0);
	}

	/**
	 * Decreases the damage of the Pokemon
	 * 
	 * @param atkType - The attack type that was chosen by the user
	 */
	@Override
	public int getAttackBonus(int atkType) {
		return super.getAttackBonus(atkType) - (int) ((Math.random() * 2) + 1);
	}
}