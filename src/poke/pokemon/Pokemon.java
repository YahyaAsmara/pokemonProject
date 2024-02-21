/* Yahya, Sahil, Jaiveer
 * 2023-05-26
 * Pokemon is an abstract representation of an Pokemon, subclass of Entity. Includes all base attributes, basic abilities and interactions.
 */

package poke.pokemon;

import poke.Entity;
import poke.elements.Fire;
import poke.elements.Water;

public abstract class Pokemon extends Entity {
	public static final double[][] battleTable = { { 1, 0.5, 2 }, { 2, 1, 0.5 }, { 0.5, 2, 1 } };

	/**
	 * Retrieve the name and max hp of the Pokemon
	 * 
	 * @param pokemonName - sets the name of the Pokemon
	 * @param hp - the hp of the pokemon
	 * @param maxHp - the max hp of the pokemon
	 */
	public Pokemon(String pokemonName, int hp, int maxHp) {
		super(pokemonName, hp, maxHp);
	}

	/**
	 * Retrieve Pokemons attack menu
	 * 
	 * @return - returns basic attack menu for Pokemon
	 */
	public String getAttackTypeMenu() {
		return "1. Basic Attack\n2. Special Attack";
	}

	/**
	 * Retrieve the number of attack menu items
	 * 
	 * @return - returns the number of items
	 */
	public int getNumAttackTypeMenuItems() {
		return 2;
	}

	/**
	 * Retrieve the number of attack menu items
	 * 
	 * @param atkType - the user's choice of attack type
	 * @return - returns the attack list of attacks
	 */
	public String getAttackMenu(int atkType) {
		if (atkType == 1) {
			return "[1] Slam\n[2] Tackle\n[3] Punch";
		}
		return " ";
	}

	/**
	 * Retrieve the number of attack menu items
	 * 
	 * @param atkType - the user's choice of attack type
	 * @return - returns the number of attacks in the attack menu
	 */
	public int getNumAttackMenuItems(int atkType) {
		return 3;
	}

	/**
	 * Retrieve the number of attack menu items
	 * 
	 * @param atkType - the user's choice of attack type
	 * @param move    - the move choosen by the user
	 * @return - returns the number of attacks in the attack menu
	 */
	public String getAttackString(int atkType, int move) {
		if (atkType == 1) {
			switch (move) {
			case 1:
				return "SLAMMED";
			case 2:
				return "TACKLED";
			case 3:
				return "PUNCHED";
			}
		}
		return "";
	}

	/**
	 * Retrieve total attack damage
	 * 
	 * @param atkType - the type of attack being done
	 * @param move    - the move selected by the trainer
	 * @return - returns the genrated damage based on the pokemon's type
	 */
	public int getAttackDamage(int atkType, int move) {
		if (atkType == 1) {
			switch (move) {
			case 1:
				return (int) ((Math.random() * 5));
			case 2:
				return (int) ((Math.random() * 1) + 2);
			case 3:
				return (int) ((Math.random() * 4) + 1);
			}
		}
		return 0;
	}

	/**
	 * Multiplies the attack of the Pokemon
	 * 
	 * @param pokemon       - pokemon that is getting passed in
	 * @param atkType - the selected attack 1-3
	 * @return - returns amount the attack is to be multiplied by
	 */
	public double getAttackMultiplier(Pokemon pokemon, int atkType) {
		if (atkType == 1) {
			return 1;
		} else {
			return battleTable[this.getType()][pokemon.getType()];
		}
	}

	/**
	 * Retrieve the attack bonus
	 * 
	 * @param atkType is the type of attack
	 */
	public int getAttackBonus(int atkType) {
		return 0;
	}

	/**
	 * Displays the information of the attack in string format
	 * 
	 * @param pokemon       is the pokemon being passed in
	 * @param atkType is the type of attack
	 * @param move    is the move selected by the trainer
	 * @returns the result of the interaction in a string format
	 */

	public String attack(Pokemon pokemon, int atkType, int move) {
		int damage = getAttackDamage(atkType, move);
		double multiplier = getAttackMultiplier(pokemon, atkType);
		int bonus = getAttackBonus(atkType);
		pokemon.takeDamage((int) ((damage * multiplier) + bonus));

		return pokemon.getName() + " is " + this.getAttackString(atkType, move) + " by " + this.getName() + " and takes "
				+ String.valueOf((int) ((damage * multiplier) + bonus)) + " damage. ";

	}

	/**
	 * Get the index of the Elemental type of the pokemon
	 * 
	 * @return the Elemental index of the type of pokemon
	 */
	public int getType() {
		if (this instanceof Fire) {
			return 0;
		} else if (this instanceof Water) {
			return 1;
		} else {
			return 2;
		}
	}
}