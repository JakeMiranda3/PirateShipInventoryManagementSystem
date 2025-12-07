package edu.westga.cs3211.pirate_ship_inventory_manager.model.storage;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;

/**
 * Defines a inventory which holds a collection of compartments for the pirate
 * ship
 * 
 * @author Jake Miranda and @author fmensah1 made changes
 * @version Fall 2025
 */
public class Inventory {

	private List<Compartment> compartments;
	private List<StockChange> stockChanges;

	/**
	 * Instantiates a new inventory.
	 * 
	 * @postcondition getCompartments().size() = 4
	 */
	public Inventory() {
		this.compartments = new ArrayList<>();
		this.stockChanges = new ArrayList<>();
		this.initializeCompartments();
	}

	/**
	 * Gets the stock items.
	 *
	 * @return the stock items
	 */
	public List<Stock> getStockItems() {
		List<Stock> allStock = new ArrayList<>();
		for (Compartment compartment : this.compartments) {
			allStock.addAll(compartment.getStoredStock());
		}
		return allStock;
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
	 * Gets the users who made changes.
	 *
	 * @return the users who made changes
	 */
	public List<User> getUsersWhoMadeChanges() {
		List<User> result = new ArrayList<>();

		for (StockChange stockChange : this.stockChanges) {
			User user = stockChange.getUser();
			if (!result.contains(user)) {
				result.add(user);
			}
		}

		return result;
	}

	/**
	 * Adds the stock to compartment.
	 * 
	 * @precondition stock != null && compartment != null
	 * @param stock       the stock
	 * @param compartment the compartment
	 */
	public void addStockToCompartment(Stock stock, Compartment compartment) {
		if (stock == null) {
			throw new IllegalArgumentException("Stock cant be null");
		}

		if (compartment == null) {
			throw new IllegalArgumentException("Compartment cant be null");
		}

		compartment.addStock(stock);
	}

	/**
	 * Adds the stock change.
	 * 
	 * @precondition changeInStock != null
	 *
	 * @param changeInStock the change in stock
	 */
	public void addStockChange(StockChange changeInStock) {
		if (changeInStock == null) {
			throw new IllegalArgumentException("StockChange cannot be null");
		}
		this.stockChanges.add(0, changeInStock);
	}

	/**
	 * Gets the stock changes.
	 *
	 * @return the stock changes
	 */
	public List<StockChange> getStockChanges() {
		return this.stockChanges;
	}

	private void initializeCompartments() {
		Compartment regularStorage = new Compartment("Regular Compartment", 100, SpecialQuality.NONE);
		Compartment flammableStorage = new Compartment("Flammable Compartment", 100, SpecialQuality.FLAMMABLE);
		Compartment liquidStorage = new Compartment("Liquid Compartment", 100, SpecialQuality.LIQUID);
		Compartment perishableStorage = new Compartment("Perishable Compartment", 100, SpecialQuality.PERISHABLE);
		this.addCompartment(regularStorage);
		this.addCompartment(flammableStorage);
		this.addCompartment(liquidStorage);
		this.addCompartment(perishableStorage);
	}

}
