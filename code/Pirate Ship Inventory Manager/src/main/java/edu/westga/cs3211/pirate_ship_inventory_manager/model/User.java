package edu.westga.cs3211.pirate_ship_inventory_manager.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Role;

/**
 * Defines a user
 * 
 * @author CS 3211
 * @version Fall 2025
 */
public class User {

	private static final String NAME_CANNOT_BE_NULL_OR_BLANK = "name cannot be null or blank.";
	private static final String PASSWORD_CANNOT_BE_NULL_OR_BLANK = "password cannot be null or blank.";

	private String name;
	private String password;
	private Set<Role> roles;

	/**
	 * Creates a new Person with the specified name.
	 * 
	 * @precondition name!=null && password != null
	 * @postcondition getName() == name && getPassword() == password
	 * 
	 * @param name     name of the user
	 * @param password password of the user
	 */
	public User(String name, String password) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException(NAME_CANNOT_BE_NULL_OR_BLANK);
		} else if (password == null || password.isBlank()) {
			throw new IllegalArgumentException(PASSWORD_CANNOT_BE_NULL_OR_BLANK);
		}

		this.name = name;
		this.password = password;

		this.roles = new HashSet<>();
		this.roles.add(Role.CREWMATE);
	}

	/**
	 * Adds the role.
	 *
	 * @param role the role
	 */
	public void addRole(Role role) {
		this.roles.add(role);
	}

	/**
	 * Checks for role.
	 *
	 * @param role the role
	 * @return true, if user has the role
	 */
	public boolean hasRole(Role role) {
		return this.roles.contains(role);
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return Collections.unmodifiableSet(this.roles);
	}

	/**
	 * Returns the users name
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the users name
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
