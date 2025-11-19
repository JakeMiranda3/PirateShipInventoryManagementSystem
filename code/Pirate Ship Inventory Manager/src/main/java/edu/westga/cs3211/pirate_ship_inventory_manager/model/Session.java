package edu.westga.cs3211.pirate_ship_inventory_manager.model;

import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Inventory;

/**
 * Holds information about the Session of the user
 * 
 * @author Jake Miranda
 * @version Fall 2025
 */
public final class Session {

	private static User currentUser;
	private static Inventory currentInventory;

	private Session() {
	}

	/**
	 * Sets the Current User.
	 *
	 * @param user the new currentUser
	 */
	public static void setCurrentuser(User user) {
		currentUser = user;
	}

	/**
	 * Gets the current user.
	 *
	 * @return the current user
	 */
	public static User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Sets the inventory.
	 *
	 * @param inventory the new inventory
	 */
	public static void setInventory(Inventory inventory) {
		currentInventory = inventory;
	}

	/**
	 * Gets the inventory.
	 *
	 * @return the inventory
	 */
	public static Inventory getInventory() {
		return currentInventory;
	}

}
