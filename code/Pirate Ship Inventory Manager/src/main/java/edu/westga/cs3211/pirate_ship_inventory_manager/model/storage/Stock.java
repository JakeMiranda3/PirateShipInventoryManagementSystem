package edu.westga.cs3211.pirate_ship_inventory_manager.model.storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;

/**
 * Defines a Stock item for the pirate ship
 * 
 * @author Jake Miranda
 * @version Fall 2025
 */
public class Stock {

	private String name;
	private int quantity;
	private Condition condition;
	private Set<SpecialQuality> qualities;
	private LocalDate expirationDate;
	private LocalDateTime addedOn;
	private StockType stockType;

	/**
	 * Creates a new stock item with name, quantity, condition, special qualities,
	 * and an optional expiration date (required if perishable).
	 * 
	 * @precondition name != null && !name.isBlank() && quantity > 0 && condition !=
	 *               null qualities != null &&
	 *               !(qualities.contains(SpecialQuality.PERISHABLE) &&
	 *               expirationDate == null)
	 * 
	 * @postcondition getName() == name && getQuantity() == quantity &&
	 *                getCondition() == condition && getQualities() == qualities &&
	 *                getExpirationDate() == expirationDate
	 * 
	 * @param name           name of the stock item
	 * @param quantity       quantity of the stock
	 * @param condition      condition of the stock (perfect, usable, unusable)
	 * @param qualities      set of special qualities (flammable, liquid,
	 *                       perishable)
	 * @param expirationDate expiration date if stock is perishable; otherwise null
	 * @param stockType      stockType to decide what the stockType
	 */
	public Stock(String name, int quantity, Condition condition, Set<SpecialQuality> qualities,
			LocalDate expirationDate, StockType stockType) {

		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Stock name is invalid.");
		}

		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}

		if (condition == null) {
			throw new IllegalArgumentException("A condition must be set");
		}

		if (qualities == null) {
			throw new IllegalArgumentException("A special quality must be set");
		}

		if (qualities.contains(SpecialQuality.NONE) && qualities.size() > 1) {
			throw new IllegalArgumentException("NONE cannot be combined with other special qualities");
		}

		if (qualities.contains(SpecialQuality.PERISHABLE) && expirationDate == null) {
			throw new IllegalArgumentException("Perishable stock must have an expiration date");
		}

		if (stockType == null) {
			throw new IllegalArgumentException("A stocktype must be set");
		}

		this.name = name;
		this.quantity = quantity;
		this.condition = condition;
		this.qualities = qualities;
		this.expirationDate = expirationDate;
		this.addedOn = LocalDateTime.now();
		this.stockType = stockType;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 * Gets the condition.
	 *
	 * @return the condition
	 */
	public Condition getCondition() {
		return this.condition;
	}

	/**
	 * Gets the qualities.
	 *
	 * @return the qualities
	 */
	public Set<SpecialQuality> getQualities() {
		return this.qualities;
	}

	/**
	 * Gets the expiration date.
	 *
	 * @return the expiration date
	 */
	public LocalDate getExpirationDate() {
		return this.expirationDate;
	}

	/**
	 * Gets the date the stock was added on
	 * 
	 * @return the added on date
	 */
	public LocalDateTime getAddedOn() {
		return this.addedOn;
	}
	
	@Override
	public String toString() {
	    return this.getName() + " (" + this.getQuantity() + " units) - " + this.getCondition();
	}

	/**
	 * Gets the stock type.
	 *
	 * @return the stock type
	 */
	public StockType getStockType() {
		return this.stockType;
	}

}
