package edu.westga.cs3211.pirate_ship_inventory_manager.model.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.ActionType;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;

/**
 * Defines a stock change
 * 
 * @author Jake Miranda
 * @version Fall 2025
 */
public class StockChange {

	private Stock stock;
	private User user;
	private LocalDateTime timeAdded;
	private ActionType actionType;

	/**
	 * Instantiates a new stock change.
	 * 
	 * @precondition stock != null && user != null
	 * @param stock      the stock
	 * @param user       the user
	 * @param actionType the type of action the stock change is
	 * 
	 */
	public StockChange(Stock stock, User user, ActionType actionType) {
		if (stock == null) {
			throw new IllegalArgumentException("Stock cannot be null");
		}

		if (user == null) {
			throw new IllegalArgumentException("User cannot be null.");
		}

		if (actionType == null) {
			throw new IllegalArgumentException("Action type cannot be null");
		}

		this.stock = stock;
		this.user = user;
		this.timeAdded = LocalDateTime.now();
		this.actionType = actionType;
	}

	/**
	 * This is a constructor to be able to have more controllability over the test
	 * 
	 * @precondition stock != null && user != null
	 * @param stock     the stock
	 * @param user      the user
	 * @param timeAdded the timeAdded
	 */
	public StockChange(Stock stock, User user, LocalDateTime timeAdded) {
		if (stock == null) {
			throw new IllegalArgumentException("Stock cannot be null");
		}
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null.");
		}

		this.stock = stock;
		this.user = user;
		this.timeAdded = timeAdded;
	}

	/**
	 * Gets the stock.
	 *
	 * @return the stock
	 */
	public Stock getStock() {
		return this.stock;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * Gets the time added.
	 *
	 * @return the time added
	 */
	public LocalDateTime getTimeAdded() {
		return this.timeAdded;
	}

	/**
	 * Gets the type of stock change.
	 *
	 * @return the type of stock change
	 */
	public ActionType getTypeOfStockChange() {
		return this.actionType;
	}

	@Override
	public String toString() {
		DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
		String formattedTime = this.timeAdded.format(formatTime);
		return this.user.getName() + " " + this.actionType.name().toLowerCase() + " " + this.stock.getQuantity() + " "
				+ this.stock.getName() + " at " + formattedTime;
	}

}
