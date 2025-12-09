package edu.westga.cs3211.pirate_ship_inventory_manager.model;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Role;

/**
 * Authenticator to verify user credentials
 * 
 * @author Jake Miranda
 * @version Fall 2025
 */
public class Authenticator {
	private List<User> credentialSet;

	/**
	 * Creates a new authenticator with default users
	 */
	public Authenticator() {
		this.credentialSet = new ArrayList<>();

		User validUser1 = new User("John", "Doe");
		User validUser2 = new User("Jane", "Doe");
		User validUser3 = new User("Matt", "Doe");
		validUser2.addRole(Role.QUARTERMASTER);
		validUser3.addRole(Role.CHEF);

		this.credentialSet.add(validUser1);
		this.credentialSet.add(validUser2);
		this.credentialSet.add(validUser3);
	}

	/**
	 * Gets the user if valid.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the user if valid
	 */
	public User getUserIfValid(String username, String password) {
		for (User user : this.credentialSet) {
			if (user.getName().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Verify user credentials.
	 *
	 * @param username the username
	 * @param password the password
	 * @return true, if successful
	 */
	public boolean verifyUserCredentials(String username, String password) {
		return this.getUserIfValid(username, password) != null;
	}

}
