package edu.westga.cs3211.pirate_ship_inventory_manager.model.storage;

import java.util.ArrayList;
import java.util.List;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;

/**
 * Defines a storage compartment for the pirate ship
 * 
 * @author Jake Miranda
 * @version Fall 2025
 */
public class Compartment {

	private String compartmentName;
	private int maxCapacity;
	private int usedCapacity;
	private SpecialQuality allowedQuality;
	private List<Stock> storedStock;

	/**
	 * Instantiates a new compartment.
	 *
	 * @precondition maxCapacity > 0 && allowedQuality != null
	 * @postcondition getMaxCapacity() == maxCapacity && getAllowedQuality() ==
	 *                allowedQuality && getUsedCapacity() == 0 &&
	 *                getStoredStock.isEmpty()
	 *
	 *
	 *@param compartmentName the name of the compartment
	 * @param maxCapacity    the max capacity
	 * @param allowedQuality the allowed quality
	 */
	public Compartment(String compartmentName, int maxCapacity, SpecialQuality allowedQuality) {
		
		if (compartmentName == null || compartmentName.isBlank()) {
			throw new IllegalArgumentException("Compartment name can't be null or empty");
		}

		if (maxCapacity <= 0) {
			throw new IllegalArgumentException("Max capacity must be greater than zero");
		}

		if (allowedQuality == null) {
			throw new IllegalArgumentException("Allowed special quality cant be null");
		}
		
		this.compartmentName = compartmentName;
		this.maxCapacity = maxCapacity;
		this.usedCapacity = 0;
		this.allowedQuality = allowedQuality;
		this.storedStock = new ArrayList<>();

	}
	
	/**
	 * Gets the compartment name.
	 *
	 * @return the compartment name
	 */
	public String getCompartmentName() {
		return this.compartmentName;
	}

	/**
	 * Gets the max capacity.
	 *
	 * @return the max capacity
	 */
	public int getMaxCapacity() {
		return this.maxCapacity;
	}

	/**
	 * Gets the used capacity.
	 *
	 * @return the used capacity
	 */
	public int getUsedCapacity() {
		return this.usedCapacity;
	}

	/**
	 * Gets the remaining capacity.
	 *
	 * @return the remaining capacity
	 */
	public int getRemainingCapacity() {
		return this.maxCapacity - this.usedCapacity;
	}

	/**
	 * Gets the allowed quality.
	 *
	 * @return the allowed quality
	 */
	public SpecialQuality getAllowedQuality() {
		return this.allowedQuality;
	}

	/**
	 * Adds the stock to the compartment
	 * 
	 * @precondition stock != null
	 * 
	 * @postcondition getStoredStock.contains(stock) == true && getUsedCapacity() ==
	 *                getUsedCapacity + stock.getQuantity()
	 *
	 * @param stock the stock
	 */
	public void addStock(Stock stock) {
		if (!this.canStoreStock(stock)) {
			throw new IllegalStateException("Compartment cannot store this type of stock.");
		}

		this.storedStock.add(stock);
		this.usedCapacity += stock.getQuantity();
	}

	/**
	 * Gets the stored stock.
	 *
	 * @return the stored stock
	 */
	public List<Stock> getStoredStock() {
		return this.storedStock;
	}

	/**
	 * Can store stock.
	 *
	 * @param stock the stock
	 * @return true, if successful
	 */
	public boolean canStoreStock(Stock stock) {

		if (this.usedCapacity + stock.getQuantity() > this.maxCapacity) {
			return false;
		}

		if (this.allowedQuality == SpecialQuality.NONE) {
			return stock.getQualities().contains(SpecialQuality.NONE);
		}

		for (SpecialQuality specialQuality : stock.getQualities()) {
			if (specialQuality.equals(this.allowedQuality)) {
				return true;
			}
		}

		return false;
	}

}
