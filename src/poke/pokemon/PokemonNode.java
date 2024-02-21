/* Yahya, Sahil, Jaiveer
 * 2023-06-05
 * This is the part of dynamic data structure used to make a roster of pokemon for the trainer. 
 * Used for the creation of nodes within the roster (list).
 */

package poke.pokemon;

public class PokemonNode {
	private Pokemon pokemon; // The Pokemon stored in the node
	private PokemonNode next; // Reference to the next node

	/**
	 * Constructs a new PokemonNode object with the given Pokemon.
	 *
	 * @param pokemon the Pokemon to be stored in the node
	 */
	public PokemonNode(Pokemon pokemon) {
		this.pokemon = pokemon;
		this.next = null;
	}

	/**
	 * Returns the Pokemon stored in the node.
	 *
	 * @return the Pokemon stored in the node
	 */
	public Pokemon getPokemon() {
		return pokemon;
	}

	/**
	 * Returns the next node in the list.
	 *
	 * @return the next node in the list
	 */
	public PokemonNode getNext() {
		return next;
	}

	/**
	 * Sets the next node in the list.
	 *
	 * @param next the next node in the list
	 */
	public void setNext(PokemonNode next) {
		this.next = next;
	}
}
