package edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel;

import java.util.HashSet;
import java.util.Set;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.ActionType;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Authenticator;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Inventory;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.StockChange;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Login ViewModel.
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

		User authenticatedUser = this.authenticator.getUserIfValid(username, password);

		if (authenticatedUser != null) {
			this.errorMessageProperty.set("");

			Session.setCurrentuser(authenticatedUser);
			Session.setInventory(new Inventory());

			User jackSparrow = new User("Jack Sparrow", "password");
			Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
			specialQualities.add(SpecialQuality.NONE);
			Stock gold = new Stock("Gold", 5, Condition.PERFECT, specialQualities, null, StockType.OTHER);
			Session.getInventory().getCompartments().get(0).addStock(gold);
			Session.getInventory().addStockChange(new StockChange(gold, jackSparrow, ActionType.ADDED));
			
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
