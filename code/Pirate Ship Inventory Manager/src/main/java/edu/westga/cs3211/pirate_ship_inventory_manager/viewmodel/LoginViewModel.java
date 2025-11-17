package edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel;

import edu.westga.cs3211.pirate_ship_inventory_manager.model.Authenticator;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Inventory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class ViewModel.
 * 
 * @author CS 3211
 * @version Fall 2025
 */

public class LoginViewModel {

	private StringProperty nameProperty;
	private StringProperty passwordProperty;
	private StringProperty errorMessageProperty;
	private Authenticator authenticator;

	/**
	 * Instantiates a new greeting view model.
	 */
	public LoginViewModel() {
		this.nameProperty = new SimpleStringProperty("");
		this.passwordProperty = new SimpleStringProperty("");
		this.errorMessageProperty = new SimpleStringProperty("");
		this.authenticator = new Authenticator();
	}

	/**
	 * Gets the name property.
	 *
	 * @return the nameProperty
	 */
	public StringProperty nameProperty() {
		return this.nameProperty;
	}

	/**
	 * Gets the allNamesProperty.
	 *
	 * @return the allNamesProperty
	 */
	public StringProperty passwordProperty() {
		return this.passwordProperty;
	}

	/**
	 * Error message property.
	 *
	 * @return the string property
	 */
	public StringProperty errorMessageProperty() {
		return this.errorMessageProperty;
	}

	/**
	 * Checks if is submit enabled.
	 *
	 * @return true, if is submit enabled
	 */
	public boolean isSubmitEnabled() {
		return !this.nameProperty.get().isEmpty() && !this.passwordProperty.get().isEmpty();
	}

	/**
	 * Try login.
	 *
	 * @return true, if successful
	 */
	public boolean tryLogin() {
		String username = this.nameProperty.get();
		String password = this.passwordProperty.get();

		if (this.authenticator.verifyUserCredentials(username, password)) {
			this.errorMessageProperty.set("");
			
			Session.setCurrentuser(new User(username, password));
			Session.setInventory(new Inventory());
			
			return true;
		} else {
			this.errorMessageProperty.set("Invalid username or password");
			return false;
		}
	}

	/**
	 * Clear text fields.
	 */
	public void clearTextFields() {
		this.nameProperty.set("");
		this.passwordProperty.set("");
	}

}
