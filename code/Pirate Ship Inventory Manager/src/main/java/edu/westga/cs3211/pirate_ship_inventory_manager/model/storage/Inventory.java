package edu.westga.cs3211.pirate_ship_inventory_manager.model.storage;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;

/**
 * Defines a inventory which holds a collection of compartments for the pirate
 * ship
 * 
 * @author Jake Miranda
 * @version Fall 2025
 */
public class Inventory {

	private List<Compartment> compartments;

	/**
	 * Instantiates a new inventory.
	 * 
	 * @postcondition getCompartments().size() = 4
	 */
	public Inventory() {
		this.compartments = new ArrayList<>();
		this.initializeCompartments();
	}

	/**
	 * Adds the compartment.
	 *
	 * @precondition compartment != null
	 * @postcondition getCompartments().contains(compartment) == true
	 *
	 *
	 * @param compartment the compartment
	 */
	public void addCompartment(Compartment compartment) {
		if (compartment == null) {
			throw new IllegalArgumentException("Compartment cant be null");
		}
		this.compartments.add(compartment);
	}

	/**
	 * Gets the compartments.
	 *
	 * @return the compartments
	 */
	public List<Compartment> getCompartments() {
		return this.compartments;
	}

	/**
	 * Adds the stock to compartment.
	 *
	 * @param stock       the stock
	 * @param compartment the compartment
	 */
	public void addStockToCompartment(Stock stock, Compartment compartment) {
		compartment.addStock(stock);
	}

	private void initializeCompartments() {
		Compartment compartment1 = new Compartment(100, SpecialQuality.NONE);
		Compartment compartment2 = new Compartment(100, SpecialQuality.FLAMMABLE);
		Compartment compartment3 = new Compartment(100, SpecialQuality.LIQUID);
		Compartment compartment4 = new Compartment(100, SpecialQuality.PERISHABLE);
		this.addCompartment(compartment1);
		this.addCompartment(compartment2);
		this.addCompartment(compartment3);
		this.addCompartment(compartment4);
	}

}
