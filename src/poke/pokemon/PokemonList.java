/* Yahya, Sahil, Jaiveer
 * 2023-06-05
 * This is the dynamic data structure used to make a roster of pokemon for the trainer. 
 * It utilizes concepts from the module, but tries to incorporate the code from PokemonGenerator.java.
 */

package poke.pokemon;

import java.util.*;
import java.io.*;
import poke.elements.*;
import poke.interactions.*;
import java.util.ArrayList;

public class PokemonList {

	private PokemonNode head; // Reference to the first node in the list

	public PokemonList() {
		this.head = null;
	}

	/**
	 * Adds a Pokemon to the end of the list.
	 *
	 * @param pokemon the Pokemon to be added
	 */
	public void addPokemon(Pokemon pokemon) {
		PokemonNode newNode = new PokemonNode(pokemon); // Create a new node
		if (head == null) {
			head = newNode; // If the list is empty, make the new node the head
		} else {
			PokemonNode current = head;
			while (current.getNext() != null) {
				current = current.getNext(); // Traverse to the last node
			}
			current.setNext(newNode); // Append the new node to the end of the list
		}
	}

	/**
	 * Removes the Pokemon at the specified index from the list.
	 *
	 * @param index the index of the Pokemon to be removed
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public void removePokemon(int index) {
		if (index < 0 || index >= getNumPokemon()) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		if (index == 0) {
			head = head.getNext(); // Remove the head node
		} else {
			PokemonNode prev = getNode(index - 1);
			PokemonNode current = prev.getNext();
			prev.setNext(current.getNext()); // Remove the node at the given index
		}
	}

	/**
	 * Returns the Pokemon at the specified index in the list.
	 *
	 * @param index the index of the Pokemon to be returned
	 * @return the Pokemon at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public Pokemon getPokemon(int index) {
		if (index < 0 || index >= getNumPokemon()) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		return getNode(index).getPokemon(); // Retrieve the Pokemon at the given index
	}

	/**
	 * Returns the number of Pokemon in the list.
	 *
	 * @return the number of Pokemon in the list
	 */
	public int getNumPokemon() {
		int count = 0;
		PokemonNode current = head;
		while (current != null) {
			count++;
			current = current.getNext(); // Traverse the list and count the nodes
		}
		return count;
	}
    
    /**
	 * Returns the Node (position) of the PokemonNode in the list
	 *
	 * @param index the index of the Pokemon to return
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	private PokemonNode getNode(int index) {
		if (index < 0 || index >= getNumPokemon()) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		PokemonNode current = head;
		for (int i = 0; i < index; i++) {
			current = current.getNext(); // Traverse the list to reach the node at the given index
		}
		return current;
	}

	/**
	 * Returns a list of all the Pokemon in the list.
	 *
	 * @return a list of all the Pokemon in the list
	 */
	public ArrayList<Pokemon> getAllPokemon() {
		ArrayList<Pokemon> pokemonList = new ArrayList<>();
		PokemonNode current = head;
		while (current != null) {
			pokemonList.add(current.getPokemon()); // Add each Pokemon to the list
			current = current.getNext(); // Traverse the list
		}
		return pokemonList;
	}

	/**
	 * Returns the number of pokemon in the list.
	 * Note this is the same as getNumPokemon().
	 * Used to simplify a part of code.
	 * 
	 * @return the size of the list
	 */

	public int size() {
		int size = 0;
		PokemonNode current = head;
		while (current != null) {
			size++;
			current = current.getNext();
		}
		return size;
	}
}
