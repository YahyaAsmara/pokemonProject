/* Yahya, Sahil, Jaiveer
 * 2023-05-29
 * This class file is intended to read the pokemon from the PokemonList.txt file. 
 * It generates pokemon from the txt file and assigns Pokemon.java data and element data.
 * Also adds the interactions.
 * 
 */

package poke.pokemon;

import java.util.*;
import java.io.*;
import java.net.URL;
import poke.elements.*;
import poke.interactions.*;

public class PokemonGenerator {
	private HashMap<String, String> pokemon;
	private static PokemonGenerator instance = null;

	/**
	 * Reads the list of pokemon from a file and places them in a hashmap
	 */
	private PokemonGenerator() {
		try {
			Scanner read = new Scanner(new File("PokemonList.txt"));

			pokemon = new HashMap<String, String>();

			while (read.hasNextLine()) {
				String[] line = read.nextLine().split(",");
				String name = line[0];
				String type = line[1];
				pokemon.put(name, type);
			}
			read.close();
		} catch (FileNotFoundException fnf) {
			System.out.println("File was not found");
		}
	}

	/**
	 * Retrieves an instance of PokemonGenerator if no such instance exists it
	 * creates one
	 * 
	 * @return the instance of PokemonGenerator
	 */
	public static PokemonGenerator getInstance() {
		if (instance == null) {
			instance = new PokemonGenerator();
		}
		return instance;
	}

	/**
	 * Retrieve a randomly generated pokemon
	 * 
	 * @return a randomly generated pokemon for encounters
	 */
	public Pokemon generateRandomPokemon(int level) {
		String[] a = new String[pokemon.size() + 1];
		int count = 0;
		for (String i : pokemon.keySet()) {
			a[count] = i;
			count++;
		}
		int random = (int) ((Math.random() * pokemon.size()));
		Pokemon pokemon = getPokemon(a[random]);
		Pokemon temp = pokemon;
		if (level == 0) {
			return getPokemon(a[random]);
		}
		for (int i = 0; i < level; i++) {
			temp = addPokemonBuff(pokemon);
			pokemon = temp;
		}
		return pokemon;
	}

	/**
	 * Creates a type of pokemon depending on the type
	 * 
	 * @param name sets the type of pokemon
	 * @return the newley created pokemon object
	 */
	public Pokemon getPokemon(String name) {
		int hp = (int) ((Math.random() * 5) + 20);
		Pokemon p = new Fire(name, hp, hp);
		if (pokemon.get(name).equals("Fire")) {
			p = new Fire(name, hp, hp);
		}
		if (pokemon.get(name).equals("Water")) {
			p = new Water(name, hp, hp);
		}
		if (pokemon.get(name).equals("Grass")) {
			p = new Grass(name, hp, hp);
		}
		return p;
	}

	/**
	 * Adds a buff to the Pokemon
	 *
	 * @param pokemon is the Pokemon the buff is being exercised on.
	 */
	public Pokemon addPokemonBuff(Pokemon pokemon) {
	    int random = (int) ((Math.random() * 2) + 1);
	    switch (random) {
	        case 1:
	            pokemon = new AttackUp(pokemon);
	            break;
	        case 2:
	            pokemon = new HpUp(pokemon);
	            break;
	    }
	    return pokemon;
	}

	/**
	 * Adds a random debuff to the Pokemon
	 *
	 * @param is the Pokemon the debuff is being exercised on.
	 */
	public Pokemon addRandomDebuff(Pokemon pokemon) {
	    int random = (int) ((Math.random() * 2) + 1);
	    switch (random) {
	        case 1:
	            pokemon = new AttackDown(pokemon);
	            break;
	        case 2:
	            pokemon = new HpDown(pokemon);
	            break;
	    }
	    return pokemon;
	
	}

}