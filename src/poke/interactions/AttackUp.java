/* Yahya, Sahil, Jaiveer
 * 2023-05-26
 * Special Interactions. Basically buffs a pokemon by manipulating it's damage.
 */

package poke.interactions;

import poke.pokemon.Pokemon;
import poke.pokemon.PokemonDecorator;

public class AttackUp extends PokemonDecorator {
	/**
	 * Increases the attack of the Pokemon
	 * 
	 * @param pokemon - the Pokemon that's getting passed it
	 */
	public AttackUp(Pokemon pokemon) {
		super(pokemon, "+ATK", 0);
	}

	/**
	 * Increases the damage of the Pokemon
	 * 
	 * @param atkType - The attack type that was choosen by the user
	 */
	@Override
	public int getAttackBonus(int atkType) {
		return super.getAttackBonus(atkType) + (int) ((Math.random() * 2) + 1);
	}
}