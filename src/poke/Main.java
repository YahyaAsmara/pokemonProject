/* Yahya, Sahil, Jaiveer
 * 2023-05-25
 * Main Class File to Run Text-Based Game.
 * 
 * Description:
 * A text-based Pokemon game that allows to fight wild pokemon, have crazy encounters, and travel through new exotic areas, until you die or feel 
 * like quitting. Credit for Final Project.
 */

package poke;

import java.util.Scanner;

import poke.pokemon.*;

class Main {

	public static void main(String[] args) {

		// Declare all variables
		int lvl = 0;
		Map map = Map.getInstance();
		map.loadMap((int) ((Math.random() * 3) + 1)); // Load a random map
		String user_choice;
		char direction = 's'; // Characters starting direction
		Trainer player;

		// -----------------------------Starting Sequence-----------------------------------//

		System.out.print("Prof. Oak: Hello there new trainer, what is your name? ");
		String name = CheckInput.getString(); // Get the name of the player
		System.out.printf("\n" + "Great to Meet You, " + name + ".");
		System.out.println(" Choose Your First Pokemon\n[1] Charmander\n[2] Bulbasaur\n[3] Squirtle");

		int choice = CheckInput.getIntRange(1, 3); // get the players choice
		PokemonGenerator generator = PokemonGenerator.getInstance();

		// Create a new trainer based on the player's choice of pokemon
		switch (choice) {
		case 1: // Charmander
			player = new Trainer(name, generator.getPokemon("Charmander"));
			break;
		case 2: // bulbasaur
			player = new Trainer(name, generator.getPokemon("Bulbasaur"));
			break;
		default: // Squirtle
			player = new Trainer(name, generator.getPokemon("Squirtle"));
			break;
		}

		// ------------------------Main Menu----------------------------------//
		
		do { // does this loop while the player health is above 1 ---> the while part of the
				// do while loop is lower in the code

			// display player states and main menu
			System.out.println(player);
			user_choice = mainMenu();

			switch (user_choice) {
			// player movement cases
			case "w":
				direction = player.goNorth();
				break;
			case "a":
				direction = player.goWest();
				break;
			case "s":
				direction = player.goSouth();
				break;
			case "d":
				direction = player.goEast();
				break;

			// ends game if player presses p
			case "p":
				System.out.println("\nYou could've caught them all.\nPlay again next time.");
				return;
			}
			// ----------------events that occur based on players location(i,c,n,!)-------------------------//
			
			// Legend:
			// i = item (pokeball or potion)
			// c = city (store or hospital)
			// f = final boss
			// n = nothing there (replaces ! when they are defeated or done dialogue)
			// ! = wild encounter with other trainers or a wild pokemon

			// Player recieves random item
			if (direction == 'i') {
				int random = (int) (Math.random() * 2) + 1;
				switch (random) {
				case 1: // recieves pokeball
					System.out.println("\nYou found a POKEBALL!\n");
					player.receivePokeball();
					map.removeChartAtLoc(player.getLocation());
					break;
				case 2: // recieves potion
					System.out.println("\nYou found a POTION!\n");
					player.recievePotion();
					map.removeChartAtLoc(player.getLocation());
					break;
				}
			}

			/** When the player encounters the finish of the area. */
			// When the player encounters the final boss, Rock
			if (direction == 'f') {
				Pokemon boss = generator.generateRandomPokemon(lvl + 1); // Generate a random Pokemon for the boss
				System.out.println("Rock: Up for a challenge? \n" + "Rock's Pokemon is " + boss.getName()); // Display
																											// boss
																											// introduction
				boolean fight = true; // bool for the fight loop
				boolean check = false; // bool to check the outcome of the fight

				while (fight) {
					System.out.println(boss); // Display boss's details
					System.out.println("What would you like to do\n [1] Fight\n [2] Use a Potion \n [3] Runs Away");
					int userChoice = CheckInput.getIntRange(1, 2); // Get the user's choice

					switch (userChoice) {
					case 1:// player chooses to fight Rock
							// Check if any of the player's pokemon have fainted
						for (int i = 0; i < player.getNumPokemon(); i++) {
							if (player.getPokemon(i).getHp() == 0) {
								fight = false; // Exit the fight loop if a pokemon has fainted
								break;
							} else {
								fight = true; // Continue the fight loop if all Pokemon are still able to fight
							}
						}

						// Check if the boss is defeated
						if (boss.getHp() == 0) {
							check = true; // Set the check flag to true if the boss is defeated
							fight = false; // Exit the fight loop
							break;
						}

						// Player attacks boss
						if (fight) {
							Trainer.trainerAttack(player, boss);
						} else {
							fight = false; // Exit the fight loop if a pokemon has fainted
							check = false; // Set the check bool to false to indicate the player's loss
						}

						// Check if the boss is defeated after the attack
						if (boss.getHp() == 0) {
							check = true;
							fight = false;
							break;
						}

						break;

					case 2: // player chooses to use potion
						if (fight) {
							if (player.hasPotion()) { // Check if the player has a potion
								System.out.println("Choose a Pokemon:\n" + player.getPokemonList()); // Display the list
																										// of player's
																										// Pokemon
								int user_input = CheckInput.getIntRange(1, player.getNumPokemon()); // get user choice
								player.usePotion(user_input - 1); // Use a potion on the chosen Pokemon
							} else {
								System.out.println("You don't have any potions."); // Display a message if the player
																					// has no potions
							}
						}

						break;
					case 3: // runs away
						fight = false;
						check = false;
						break;

					default:
						System.out.println("Invalid choice. Please try again.");
						break;
					}
				}

				// Display a message if the player loses the fight
				if (fight == false && check == false) {
					System.out.println("Rock: Try again once you're stronger");
				}

				// Display a congratulatory message if the player defeats the boss
				if (fight == false && check == true) {
					System.out.println("Congratulations! You defeated Rock and his Pokemon!");
					System.exit(choice);
				}
			}

			/** There is nothing located at that location. */
			if (direction == 'n') {
				System.out.println("\nThere is nothing there ...\n");
				map.removeChartAtLoc(player.getLocation());
			}

			// Player enters the city
			if (direction == 'c') {
				int input = 0;
				// Print options
				System.out.println("\nYou've entered the city.\nWhere would you like to go ?");
				System.out.println("[1] Store\n[2] Pokemon Hospital");
				input = CheckInput.getIntRange(1, 2);

				switch (input) {
				case 1: // If player wants to go to the store
					Map.store(player);
					break;

				case 2: // If player wants to go to the hospital
					System.out.println("\nHello! Welcome to the Pokemon Hospital.");
					System.out.println("I'll fix your pokemon right up.");
					System.out.println("There you go! See you again soon.\n");
					player.healAllPokemon();
					break;
				}
			}

			/** When the trainer encounters either a trainer or wild pokemon */
			if (direction == '!') {
				int random = (int) ((Math.random() * 3) + 1); // player has random chance of encountering trainer or a
																// pokemon
				switch (random) {
				case 1:
					encounterWildPokemon(player, generator, map, lvl);
					break;

				case 2:
					encounterWildPokemon(player, generator, map, lvl);
					break;

				case 3:
					encountersTrainer(player, map);
					break;
				}

			}

		} while (player.getHp() != 0); // runs the above code while players health is above 0

		if (player.getHp() <= 0) { // displays death message once player hp is at 0 or lower
			System.out.println("You died. Better luck next time.");
		}
	}

	// ----------------------------Methods---------------------------//

	// Prints the Main Menu and returns the users input
	public static String mainMenu() {
		System.out.println("Main Menu");
		System.out.println("Use WASD controls to move within map");
		System.out.println("[p] Quit");
		Scanner input = new Scanner(System.in);
		String userInput = input.next();
		return userInput;
	}

	// --------------------------------- Is called when player encounters wild pokemon ---------------------------------//

	public static void encounterWildPokemon(Trainer player, PokemonGenerator generator, Map map, int lvl) {
		Pokemon wild = generator.generateRandomPokemon(lvl);
		System.out.println("\nYou ran into a WILD " + wild.getName() + "!");
		boolean fight = true;

		while (fight) {
			System.out.println(wild);
			System.out.println("What would you like to do?");
			System.out.println("[1] Fight");
			System.out.println("[2] Use Potion");
			System.out.println("[3] Throw Poke Ball");
			System.out.println("[4] Run Away");

			int input = CheckInput.getIntRange(1, 4);
			boolean noneFainted = true;

			switch (input) {
			case 1: // Fight
				// Check if any of the player's pokemon have fainted
				for (int i = 0; i < player.getNumPokemon(); i++) {
					if (player.getPokemon(i).getHp() == 0) {
						noneFainted = false; // Indicates that at least one Pokemon has fainted
						break;
					} else {
						noneFainted = true; // Indicates that all Pokemon are able to fight
					}
				}

				if (noneFainted) {
					Trainer.trainerAttack(player, wild);
				} else {
					int rand = (int) ((Math.random() * 4) + 1);
					player.takeDamage(rand);
					System.out.println("All of your Pokemon are fainted.");
					System.out.println(wild.getName() + " attacked you for " + String.valueOf(rand) + " DAMAGE.\n");
					System.out.println("You can either run or use a Potion.");
				}
				break;

			case 2: // Use Potion
				// User chooses a Pokemon to use a potion on
				System.out.println("Choose a Pokemon:\n" + player.getPokemonList());
				int user_input = CheckInput.getIntRange(1, player.getNumPokemon());
				player.usePotion(user_input - 1);
				break;

			case 3: // Throw Poke Ball
				if (player.getNumPokemon() <= 6) {
					if (player.hasPokeball()) {
						
						/* Removed because game is better to "catch them all!"
						 
						// Check if the player has space for another Pokemon
						if (player.getNumPokemon() == 5 && player.catchPokemon(wild)) {
							// If the player has 5 Pokemon, they must remove one before catching the new one
							System.out.println("Choose Which Pokemon to remove:");
							System.out.println(player.getPokemonList());
							int choice = CheckInput.getIntRange(1, player.getNumPokemon());
							//player.removePokemon(choice - 1);
						} */

						System.out.println("Shake.. Shake... Shake...");
						if (player.catchPokemon(wild)) {
							System.out.println("You caught " + wild.getName());
							map.removeChartAtLoc(player.getLocation());
							fight = false;
						} else {
							System.out.println(wild.getName() + " escaped from the Poke Ball.");
						}
					} else {
						System.out.println("You don't have any Poke Balls left!");
					}
				}
				break;

			case 4: // Run Away
				int random = (int) ((Math.random() * 3) + 1); // has a one out of 3 chance of running away

				if (random == 2) {
					System.out.println("\nYou got away successfully! \n");
					fight = false;
					// map.removeCharAtLoc(player.getLocation());
					break;
				} else {
					System.out.println("You didn't successfully escape.");
					fight = true;
				}
				break;

			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}

			if (wild.getHp() == 0) {
				map.removeChartAtLoc(player.getLocation());
				fight = false;
			}
		}
	}

	//------------------------------- Is called when player encounters wild trainer------------------------------//
	//player.getPokemon(0).getName()
	  
	  public static void encountersTrainer(Trainer player, Map map) {
		  
		  int random = (int) ((Math.random() * 4) + 1); //player has random chance of encountering trainer
	      Scanner in = new Scanner(System.in);
	      String input;
	      
	      System.out.println("Press 'Enter' to continue.");
	      
	      switch (random) {
		      case 1: // Encounters Rock
		          System.out.println("Rock: Hey there! I challenge you to a battle!");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": Bring it on!");
		          input = in.nextLine();
		          System.out.println("Rock: Go, Geodude!");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": I choose you, Pikachu!");
		          input = in.nextLine();
		          System.out.println("Rock: Geodude, use Rock Throw!");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": " + player.getPokemon(0).getName() + ", attack.");
		          input = in.nextLine();
		          System.out.println("Rock: Oh no, my Geodude fainted! You win this time.");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": Thanks for the battle!");
		          input = in.nextLine();
		          System.out.println("Rock: You're a strong Trainer. Here, take this as a token of respect.");
		          input = in.nextLine();
		          System.out.println("You received 3 Pokeballs\n");
		          player.receivePokeball();
		          player.receivePokeball();
		          player.receivePokeball();
		          break;
		
		      case 2: // Encounters Misty
		          System.out.println("Misty: Hey, have you seen any Water-type Pokemon around here?");
		          input = in.nextLine();
		          System.out.print(player.getName() + ": Yes, I saw a Magikarp by the lake.");
		          input = in.nextLine();
		          System.out.println("Misty: Really? Can you show me the way?");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": Sure, follow me!");
		          input = in.nextLine();
		          System.out.println("Misty: Wow, there it is! Thanks for your help.");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": No problem, happy to assist!");
		          input = in.nextLine();
		          System.out.println("Misty: Here, take this as a token of gratitude.");
		          input = in.nextLine();
		          System.out.println("You received a Potion\n");
		          player.recievePotion();
		          break;
		
		      case 3: // Encounters Officer Jenny
		          System.out.println("Officer Jenny: Stop right there! I need your help with something.");
		          player.takeDamage(2);
		          System.out.println(player.getName() + ": What's the problem, Officer?");
		          input = in.nextLine();
		          System.out.println("Officer Jenny: We have reports of a dangerous Pokemon causing trouble nearby.");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": Don't worry, I'll do my best to handle it.");
		          input = in.nextLine();
		          System.out.println("Officer Jenny: Thank you. Be careful, it's a powerful one.");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": I'm ready for the challenge!");
		          input = in.nextLine();
		          System.out.println("Officer Jenny: Good luck!");
		          input = in.nextLine();
		          System.out.println("After a fierce battle...");
		          input = in.nextLine();
		          System.out.println("Officer Jenny: You did it! You defeated the dangerous Pokemon!");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": Phew, that was intense.");
		          input = in.nextLine();
		          System.out.println("Officer Jenny: As a token of our appreciation, take this.");
		          input = in.nextLine();
		          System.out.println("You received 5 Money\n");
		          player.recieveMoney(5);
		          break;

		        case 4: //encounters professor oak
		          System.out.println("Professor Oak: Look out!!!");
		          player.takeDamage(5);
		          input = in.nextLine();
		          System.out.println(player.getName() + " takes 5 DAMAGE from MEW-TWO");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": What was that?");
		          input = in.nextLine();
		          System.out.println("Professor Oak: That was a clone of MEW scientifically made by scientists.");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": Why is it going on a rampage?");
		          input = in.nextLine();
		          System.out.println("Professor Oak: MEW-TWO has never had any human interaction since he was created.");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": Oh I see. I'll see if I can do anything to help.");
		          input = in.nextLine();
		          System.out.println("Professor Oak: That would be a huge help.");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": Hey MEW-TWO, my name is " + player.getName() + ".");
		          input = in.nextLine();
		          System.out.println("MEW-TWO: Who am I? Where is this place? What was I born for?");
		          input = in.nextLine();
		          System.out.println(player.getName()
		              + ": I don't know why you were created but what you're doing right now is hurting a bunch of people.");
		          input = in.nextLine();
		          System.out.println("MEW-TWO: Why should I listen to you?");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": I'm only trying to stop other people from getting hurt.");
		          input = in.nextLine();
		          System.out.println("MEW-TWO: I'll listen to you and stop going on a rampage.");
		          input = in.nextLine();
		          System.out.println(player.getName() + ": Come MEW-TWO let's go back to Professor Oak.");
		          input = in.nextLine();
		          System.out.println("Professor Oak: Thanks for all the help calming down MEW-TWO.");
		          input = in.nextLine();
		          System.out.println("You received 2 Potions.");
		          player.recievePotion();
		          player.recievePotion();
		          break;
	      }
	      map.getCharAtLoc(player.getLocation());
	  }

}
