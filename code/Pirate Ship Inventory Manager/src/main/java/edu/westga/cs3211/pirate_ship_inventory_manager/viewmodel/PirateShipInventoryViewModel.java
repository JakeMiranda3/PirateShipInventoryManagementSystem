package edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Role;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * View Model for PirateShipInventoryMainPage
 * 
 * @author Jake Miranda
 * @version Fall 2025
 */
public class PirateShipInventoryViewModel {

	private BooleanProperty quarterMaster;
	private BooleanProperty chef;

	/**
	 * Instantiates a new pirate ship inventory view model.
	 */
	public PirateShipInventoryViewModel() {
		boolean isQuarterMaster = Session.getCurrentUser().getRoles().contains(Role.QUARTERMASTER);
		boolean isChef = Session.getCurrentUser().getRoles().contains(Role.CHEF);
		this.chef = new SimpleBooleanProperty(isChef);
		this.quarterMaster = new SimpleBooleanProperty(isQuarterMaster);
	}

	/**
	 * Checks if is quarter master property.
	 *
	 * @return the boolean property
	 */
	public BooleanProperty isQuarterMasterProperty() {
		return this.quarterMaster;
	}
	
	/**
	 * Checks if is chef property.
	 *
	 * @return the boolean property
	 */
	public BooleanProperty isChefProperty() {
		return this.chef;
	}

}
