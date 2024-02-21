/* Yahya, Sahil, Jaiveer
 * 2023-05-25
 * Representation of Trainer, which is an Entity.
 * Contains all associated systems.
 * Map - Location and Navigation.
 * Money - Purchase items 
 * Items - Potions and Pokeballs (collected)
 * Pokemon - Starter and Captured (collected)
 * Battles and Interactions
 */


package poke;

import java.util.*;

import poke.Entity;
import poke.pokemon.*;

import java.awt.Point;

public class Trainer extends Entity {
	private int money;
	private int potions;
	private int pokeballs;
	private Point loc;
	private PokemonList pokemon;

	public Trainer(String n, Pokemon p) {
		super(n, 25, 25);
		this.pokemon = new PokemonList();
		this.pokemon.addPokemon(p);
		this.money = 25;
		this.potions = 1;
		this.pokeballs = 5;
		Map map = Map.getInstance();
		map.loadMap(1);
		this.loc = map.findStart();
	}

	/**
	 * Return the amount of money the Trainer has
	 * 
	 * @return money the amount of money the Trainer has.
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Increases the amount of money the Trainer has
	 * 
	 * @param amt how much the trianer is trying to use
	 * @return true if the use has enough money
	 * @return false if the user doesnt have enough money
	 */
	public boolean spendMoney(int amt) {
		if ((getMoney() - amt) > 0) {
			money -= amt;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Increases the amount of money the Trainer has
	 * 
	 * @param amt how much the Trainer recieved
	 */
	public void recieveMoney(int amt) {
		money += amt;
	}

	/**
	 * Check if the Trainer has enough potions
	 * 
	 * @return true Trainer has enough potions
	 * @return false Trainer doesn't have enough potions
	 */
	public boolean hasPotion() {
		if (potions == 0) {
			return false;
		} else {
			return true;
		}
	}

	/** Increases the amount of potions the Trainer has */
	public void recievePotion() {
		potions += 1;
	}

	/**
	 * Uses a potion to heal a pokemon
	 * 
	 * @param pokeIndex used to get the pokemon from the PokemonList
	 */
	public void usePotion(int pokeIndex) {
		if (hasPotion()) {
			Pokemon p = pokemon.getPokemon(pokeIndex);
			PokemonGenerator gen = PokemonGenerator.getInstance();
			potions -= 1;
			p.heal();
			p = gen.addPokemonBuff(p);
			// pokemon.removePokemon(pokeIndex);
			// pokemon.addPokemon(p);
		}
	}

	/**
	 * Check if the Trainer has enough Pokeballs
	 * 
	 * @return true has enough pokeballs
	 * @return false doesnt have enough pokeballs
	 */
	public boolean hasPokeball() {
		if (pokeballs <= 0) {
			return false;
		} else {
			return true;
		}
	}

	/** Increases the amount of pokeballs the Trainer has */
	public void receivePokeball() {
		pokeballs += 1;
	}

	/**
	 * Check to see if the pokemon has a high enough probabilty of being caught
	 * 
	 * @param p the pokemon that is trying to be caught
	 * @return true if the probablity of it being caught is acheived return false if
	 *         the probablity is not acheived
	 */
	public boolean catchPokemon(Pokemon p) {
		// use getHp to establish a probability of being caught
		if (pokemon.size() <= 5) {
			double percentage = p.getHp() / p.getMaxHp();
			double prob = 1.0 - percentage;
			pokeballs -= 1;
			if (Math.random() <= prob) {
				pokemon.addPokemon(p);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Gets the locations that the player is at
	 * 
	 * @return loc returns the location the player is at
	 */
	public Point getLocation() {
		return loc;
	}

	/**
	 * Moves the player north on the map
	 * 
	 * @return toremove the char at the location that the player is moving to
	 * @return 'x' if the player cant move that direction
	 */
	public char goNorth() {
		// Done just to make sure we are within bounds
		if (loc.getY() != 0) {
			Map map = Map.getInstance();
			loc.translate(0, -1);
			map.reveal(loc.getLocation());
			// We move if it's a valid now we have to account for where we were standing.
			char toremove = map.getCharAtLoc(loc.getLocation());
			return toremove;
		}
		// This is just in the event that we can't go north the char that was there
		// remains the same.
		System.out.println("\nYou can't go that way !\n");
		return 'x';
	}

	/**
	 * Moves the player South on the map
	 * 
	 * @return toremove the char at the location that the player is moving to
	 * @return 'x' if the player cant move that direction
	 */
	public char goSouth() {
		// Makes sure we aren't at the "bottom" of the map and don't go out of bounds
		Map map = Map.getInstance();
		if (loc.getY() != 7) {
			loc.translate(0, 1);
			map.reveal(loc.getLocation());

			char toremove = map.getCharAtLoc(loc.getLocation());
			// System.out.println("\n" + toremove+"\n");
			return toremove;
		}
		System.out.println("\nYou can't go that way !\n");
		return 'x';
	}

	/**
	 * Moves the player East on the map
	 * 
	 * @return toremove the char at the location that the player is moving to
	 * @return 'x' if the player cant move that direction
	 */
	public char goEast() {
		// Just to check if we're on the edge of the map and not go out of bounds
		if (loc.getX() != 7) {
			Map map = Map.getInstance();
			char fin = map.getCharAtLoc(loc.getLocation());
			if (fin == 'f') {
				loc = map.findStart();
			}
			loc.translate(1, 0);
			map.reveal(loc.getLocation());

			char toremove = map.getCharAtLoc(loc.getLocation());
			return toremove;
		}

		System.out.println("\nYou can't go that way !\n");
		return 'x';
	}

	/**
	 * Moves the player West on the map
	 * 
	 * @return toremove the char at the location that the player is moving to
	 * @return 'x' if the player cant move that direction
	 */
	public char goWest() {
		// If we aren't on the left most edge move one to the left
		if (loc.getX() != 0) {
			Map map = Map.getInstance();
			loc.translate(-1, 0);
			map.reveal(loc.getLocation());

			char toremove = map.getCharAtLoc(loc.getLocation());
			return toremove;
		}
		System.out.println("\nYou can't go that way !\n");
		return 'x';
	}

	/**
	 * Gets the number of pokemons in the PokemonList
	 * 
	 * @return pokemon.size() the size of the PokemonList
	 */
	public int getNumPokemon() {
		return pokemon.size();
	}

	/** Heals all the pokemon in the PokemonList */
	public void healAllPokemon() {
		for (int i = 0; i < pokemon.size(); i++) {
			pokemon.getPokemon(i).heal();
		}
	}

	/** Buffs all the pokemon in the PokemonList */
	public void buffAllPokemon() {
		for (int i = 0; i < getNumPokemon(); i++) {
			Pokemon p = pokemon.getPokemon(i);
			PokemonGenerator gen = PokemonGenerator.getInstance();

			p = gen.addPokemonBuff(p);
			// pokemon.removePokemon(i);
			// pokemon.addPokemon(p);
		}
	}

	/** Debuffs all the pokemon in the PokemonList */
	public void debuffPokemon(int index) {
		PokemonGenerator gen = PokemonGenerator.getInstance();

		Pokemon temp = pokemon.getPokemon(index);
		temp = gen.addRandomDebuff(temp);
		// pokemon.removePokemon(index);
		// pokemon.addPokemon(temp);
	}

	/**
	 * Grabs the Pokemon
	 * 
	 * @param index the index of the pokemon in the PokemonList
	 * @return pokemon.get(index) returns the pokemon at the index that was choosen
	 */
	public Pokemon getPokemon(int index) {
		return pokemon.getPokemon(index);
	}

	/**
	 * Grabs the pokemons in the pokemon PokemonList
	 * 
	 * @return pokemon_list the string holding all the Pokemon and their health
	 */
	public String getPokemonList() {
		String pokemon_list = "";
		for (int i = 0; i < pokemon.size(); i++) {
			pokemon_list += String.valueOf(i + 1) + ". " + pokemon.getPokemon(i).getName() + "  HP: "
					+ pokemon.getPokemon(i).getHp() + "/" + pokemon.getPokemon(i).getMaxHp() + "\n";
		}
		return pokemon_list;
	}

	/**
	 * Allows the user to choose a Pokemon to fight the wild Pokemon with.
	 * 
	 * @param player - the trainer that is fighting the wild Pokemon
	 * @param wild   - the wild Pokemon that was encountered
	 * @return wild - returns the wild Pokemon
	 */
	public static Pokemon trainerAttack(Trainer player, Pokemon wild) {
	    // Prompt the player to choose a Pokemon to battle with
	    System.out.println("Choose a Pokemon: ");
	    
	    // Display the available Pokemon options for the player
	    for (int i = 0; i < player.getNumPokemon(); i++) {
	        if (player.getPokemon(i).getHp() > 0) {
	            System.out.println(i + 1 + ". " + player.getPokemon(i).getName() + " Hp: "
	                    + player.getPokemon(i).getHp() + "/" + player.getPokemon(i).getMaxHp());
	        }
	    }
	    
	    // Get the player's choice of Pokemon
	    int input = CheckInput.getIntRange(1, player.getNumPokemon());
	    System.out.println(player.getPokemon(input - 1).getName() + ", I choose you.");
	    
	    // Display the available attack types for the chosen Pokemon
	    System.out.println(player.getPokemon(input - 1).getAttackTypeMenu());
	    
	    // Get the player's choice of attack type
	    int atkType = CheckInput.getIntRange(1, player.getPokemon(input - 1).getNumAttackTypeMenuItems());
	    
	    // Randomly select an attack type and move for the wild Pokemon
	    int rand = (int) ((Math.random() * wild.getNumAttackTypeMenuItems()) + 1);
	    int rand1 = (int) ((Math.random() * wild.getNumAttackMenuItems(atkType)) + 1);
	    
	    // Display the available attack options for the chosen Pokemon and attack type
	    System.out.println(player.getPokemon(input - 1).getAttackMenu(atkType));
	    
	    // Get the player's choice of attack move
	    int move = CheckInput.getIntRange(1, player.getPokemon(input - 1).getNumAttackMenuItems(atkType));
	    
	    // Perform the attack of the player's Pokemon on the wild Pokemon
	    System.out.println(player.getPokemon(input - 1).attack(wild, atkType, move));
	    
	    // Apply a debuff to the player's Pokemon with a 10% chance
	    if (Math.random() <= 0.1) {
	        player.debuffPokemon(input - 1);
	    }
	    
	    // Generate an instance of the PokemonGenerator class
	    PokemonGenerator gen = PokemonGenerator.getInstance();
	    
	    // Add a random debuff to the wild Pokemon with a 25% chance
	    if (Math.random() <= 0.25) {
	        wild = gen.addRandomDebuff(wild);
	    }
	    
	    // If the wild Pokemon's HP reaches 0, return the wild Pokemon
	    if (wild.getHp() == 0) {
	        return wild;
	    }
	    
	    // Perform the attack of the wild Pokemon on the player's chosen Pokemon
	    System.out.println(wild.attack(player.getPokemon(input - 1), rand, rand1));
	    
	    // Return the wild Pokemon
	    return wild;
	}

	/**
	 * String representation of a Trainer object
	 * 
	 * @return string representation of Trainer
	 */
	@Override
	public String toString() {
		Map map = Map.getInstance();

		String s = "\n-----------\n" + super.toString() + "\nMoney: " + String.valueOf(this.getMoney()) + "\nPotions: "
				+ String.valueOf(this.potions) + "\nPoke Balls: " + String.valueOf(this.pokeballs)
				+ "\nPokemon\n-----------\n";
		String fin = s + getPokemonList();
		return fin + "\nMap:\n" + map.mapToString(loc);
	}

}