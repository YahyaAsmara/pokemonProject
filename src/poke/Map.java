/* Yahya, Sahil, Jaiveer
 * 2023-05-25
 * Map design class. 
 * Uses the Hashmap to read the map with set characteristics.
 * Includes the store element as well.
 * Includes associated methods.
 */

package poke;

import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.Point;

public class Map {
	private char[][] mp;
	private boolean[][] revealed;
	private static Map instance = null;

	/**
	 * Creates a default map 8 rows 8 columns also makes an accompanying boolean
	 * table
	 */
	public Map() {
		mp = new char[8][8];
		revealed = new boolean[8][8];
	}
	
	
    /**
     * Create new instance of the map. Can be reused so that the Map is still
     * the same map being played, and not a new map from the list of 3.
     * 
     * @return instance
     */
	public static Map getInstance() {
		if (instance == null) {
			instance = new Map();
		}
		return instance;
	}

	/**
	 * Passes in the integer representing the map to be loaded
	 * 
	 * @param mapNum number of map to be loaded in
	 */
	void loadMap(int mapNumber) {
		try {
			Scanner read = new Scanner(new File("Area"+ mapNumber + ".txt"));
					
			while (read.hasNext()) {
				for (int i = 0; i < mp.length; i++) {
					String line = read.nextLine().replace(" ", "");
					for (int j = 0; j < line.length(); j++) {
						mp[i][j] = line.charAt(j);
					}
				}
			}
			read.close();
		} catch (FileNotFoundException fnf) { // throws an exeption if file not found
			System.out.println("File was not found");
		}
	}

	/**
	 * Returns a character at a given point on the map otherwise returns 0
	 * 
	 * @param Point p the point where the player is
	 * @return char a point p otherwise return 0
	 */
	public char getCharAtLoc(Point p) {
		return mp[p.y][p.x];
	}

	/**
	 * Passes Point p where the player is and displays the map
	 * 
	 * @param Point p the point where the player is
	 * @return s which displays the map
	 */
	public String mapToString(Point p) {
		String s = "";
		for (int i = 0; i < mp.length; i++) {
			for (int j = 0; j < mp[0].length; j++) {
				if (i == p.getY() && j == p.getX()) {
					s += '*';
				} else if (!revealed[i][j]) {
					s += "X";
				} else if (revealed[i][j] || mp[i][j] != 's') {
					s += mp[i][j];
				}
				// includes a space after every character
				s += " ";
			}
			// Starts a new line instead of printing all on one line
			s += "\n";
		}
		return s;
	}

	/**
	 * Finds the start 's' of the map
	 * 
	 * @return p which shows where the start of the map is
	 */
	public Point findStart() {
		Point p = new Point();
		for (int i = 0; i < mp.length; i++) {
			for (int j = 0; j < mp[0].length; j++) {
				if (mp[i][j] == 's') {
					p.setLocation(j, i);
					revealed = new boolean[8][8];
					reveal(p);
				}
			}
		}
		return p;
	}

	/**
	 * Passes in Point p and reveals that tile / point
	 * 
	 * @param Point p the point to be revealed
	 */

	void reveal(Point p) {
		revealed[p.y][p.x] = true;
	}

	/**
	 * Passes in Point p and removes that tile /point
	 * 
	 * @param Point p where the char is to be removed
	 */
	void removeChartAtLoc(Point p) {
		if (mp[p.y][p.x] != 's' && mp[p.y][p.x] != 'c') {
			mp[p.y][p.x] = 'n';
		}
	}

	/**
	 * The store located in the city. That allows you to buy potions and pokeballs
	 * 
	 * @param player - the trainer that entered the store
	 */
	public static void store(Trainer player) {
		int input = 0;
		while (input != 3) {
			System.out.println("\nHello " + player.getName() + "! What can I help you with?");
			System.out.println("\nAvailable cash: $" + player.getMoney());
			System.out.println("\n[1] Buy Potions - $5");
			System.out.println("[2] Buy Poke Balls - $3");
			System.out.println("[3] Exit");
			input = CheckInput.getIntRange(1, 3);
			switch (input) {
			case 1:
				if (player.spendMoney(5)) {
					player.recievePotion();
					System.out.println("Here's your potion");
				}
				break;
			case 2:
				if (player.spendMoney(3)) {
					player.receivePokeball();
					System.out.println("Here's your PokeBall");
				}
				break;
			case 3:
				break;
			}
		}
		System.out.println("\nThank You, Come Again Soon!\n");
	}
}