package edu.westga.cs3211.pirate_ship_inventory_manager.model;

/**
 * Defines a user
 * 
 * @author CS 3211
 * @version Fall 2025
 */
public class User {

	private static final String NAME_CANNOT_BE_NULL = "name cannot be null.";
	private static final String PASSWORD_CANNOT_BE_NULL = "password cannot be null.";
	private String name;
	private String password;

	/**
	 * Creates a new Person with the specified name.
	 * 
	 * @precondition name!=null && password != null
	 * @postcondition getName() == name && getPassword() == password
	 * 
	 * @param name name of the user
	 * @param password password of the user
	 */
	public User(String name, String password) {
		if (name == null) {
			throw new IllegalArgumentException(NAME_CANNOT_BE_NULL);
		} else if (password == null) {
			throw new IllegalArgumentException(PASSWORD_CANNOT_BE_NULL);
		}

		this.name = name;
		this.password = password;
	}

	/**
	 * Returns the Person's name
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the Person's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}
}
