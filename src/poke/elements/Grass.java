/**
 * Yahya, Sahil, Jaiveer
 * 2023-05-26
 * Pokemon Element for Grass. Includes all grass attributes, special abilities and interactions.
 */

package poke.elements;

import poke.pokemon.Pokemon;

public class Grass extends Pokemon {
    /**
     * Creates a Grass pokemon
     *
     * @param pokemonName - sets the name of the Pokemon
     * @param hp - the hp of the pokemon
     * @param maxHp - the max hp of the pokemon
     */
    public Grass(String pokemonName, int hp, int maxHp) {
        super(pokemonName, hp, maxHp);
    }

    /**
     * Retrieve Pokemon's attack menu
     *
     * @return - returns basic attack menu for Pokemon
     */
    @Override
    public String getAttackMenu(int atkType) {
        if (atkType == 1) {
            return super.getAttackMenu(atkType);
        } else {
            return "1. Vine Whip\n2. Razor Leaf\n3. Solar Beam";
        }
    }

    /**
     * Retrieve the number of attack menu items
     *
     * @param atkType - the user's choice of attack type
     * @return - returns the attack list of attacks
     */
    public int getNumAttackMenu(int atkType) {
        return 3;
    }

    /**
     * Retrieve the number of attack menu items
     *
     * @param atkType - the user's choice of attack type
     * @param move    - the move chosen by the user
     * @return - returns the number of attacks in the attack menu
     */
    @Override
    public String getAttackString(int atkType, int move) {
        if (atkType == 1) {
            return super.getAttackString(atkType, move);
        } else if (atkType == 2) {
            switch (move) {
                case 1:
                    return "WHIPPED";
                case 2:
                    return "RAZOR LEAF";
                case 3:
                    return "SOLAR BEAM";
            }
        }
        return " ";
    }

    /**
     * Retrieve total attack damage
     *
     * @param atkType - the type of attack being done
     * @param move    - the move selected by the trainer
     * @return - returns the generated damage based on the Pokemon's type
     */
    @Override
    public int getAttackDamage(int atkType, int move) {
        if (atkType == 1) {
            return super.getAttackDamage(atkType, move);
        } else {
            switch (move) {
                case 1:
                    return (int) ((Math.random() * 3) + 1);
                case 2:
                    return (int) ((Math.random() * 3) + 2);
                case 3:
                    return (int) ((Math.random() * 6));
            }
        }
        return 0;
    }

    /**
     * Multiplies the attack of the Pokemon
     *
     * @param pokemon - Pokemon that is being passed in
     * @param atkType - the selected attack 1-3
     * @return - returns the amount the attack is to be multiplied by
     */
    @Override
    public double getAttackMultiplier(Pokemon pokemon, int atkType) {
        return super.getAttackMultiplier(pokemon, atkType);
    }
}
