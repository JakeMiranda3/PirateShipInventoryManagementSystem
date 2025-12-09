package edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel;

import java.util.List;
import java.util.stream.Collectors;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.ActionType;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Inventory;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.StockChange;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * ViewModel for the Pantry view. Provides filtering and taking food behavior.
 *
 * @author Auto-generated
 * @version 1.0
 */
public class ViewPantryViewModel {

	private Inventory inventory;
	private ObservableList<Stock> filteredStock;

	private StringProperty searchText;
	private StringProperty selectedFilter; 
	private IntegerProperty amountToTake;

	/**
	 * Instantiates a new view pantry view model.
	 */
	public ViewPantryViewModel() {
		this.inventory = Session.getInventory();
		// Initialize the observable list that the view binds to
		this.filteredStock = FXCollections.observableArrayList(this.inventory.getStockItems().stream()
				.filter(s -> s.getStockType() == StockType.FOOD).collect(Collectors.toList()));

		this.searchText = new SimpleStringProperty("");
		this.selectedFilter = new SimpleStringProperty(null);
		this.amountToTake = new SimpleIntegerProperty(1);

		// react to search changes
		this.searchText.addListener((obs, oldVal, newVal) -> this.applyFilters());
		this.selectedFilter.addListener((obs, oldVal, newVal) -> this.applyFilters());
	}

	/**
	 * Gets the filtered stock.
	 *
	 * @return the filtered stock
	 */
	public ObservableList<Stock> getFilteredStock() {
		return this.filteredStock;
	}

	/**
	 * Gets the search text property.
	 *
	 * @return the search text property
	 */
	public StringProperty getSearchTextProperty() {
		return this.searchText;
	}

	/**
	 * Gets the selected filter property.
	 *
	 * @return the selected filter property
	 */
	public StringProperty getSelectedFilterProperty() {
		return this.selectedFilter;
	}

	/**
	 * Gets the amount to take property.
	 *
	 * @return the amount to take property
	 */
	public IntegerProperty getAmountToTakeProperty() {
		return this.amountToTake;
	}

	/**
	 * Apply filters.
	 */
	public void applyFilters() {
		List<Stock> filtered = this.inventory.getStockItems();
		String filter = this.selectedFilter.get();
		if (filter != null && !filter.isBlank()) {
			try {
				StockType stockType = StockType.valueOf(filter);
				filtered = filtered.stream()
						.filter(s -> s.getStockType() == stockType)
						.collect(Collectors.toList());
			} catch (IllegalArgumentException ex) {
			}
		}

		String text = this.searchText.get();
		if (text != null && !text.isBlank()) {
			String lower = text.toLowerCase();
			filtered = filtered.stream().filter(s -> s.getName().toLowerCase().contains(lower))
					.collect(Collectors.toList());
		}
		this.filteredStock.setAll(filtered);
	}

	/**
	 * Take food from the selected stock item. Reduces quantity or removes the item
	 * and records a StockChange REMOVED.
	 *
	 * @param stock the stock item to take from (must be in inventory)
	 * @return null if success; otherwise an error message
	 */
	public String takeFood(Stock stock) {
		if (stock == null) {
			return "No pantry item selected.";
		}

		int amount = this.amountToTake.get();
		if (amount <= 0) {
			return "Amount must be at least 1.";
		}

		if (!this.inventory.getStockItems().contains(stock)) {
			return "Selected item no longer exists in inventory.";
		}

		if (amount > stock.getQuantity()) {
			return "Not enough quantity available.";
		}

		// If removing all units, remove the stock object from its compartment
		if (amount == stock.getQuantity()) {
			// find the compartment that contains the stock and remove it
			this.inventory.getCompartments().forEach(comp -> comp.getStoredStock().remove(stock));
		} else {
			// stock is immutable in this model (no setter), so we need to create a new
			// Stock
			// with the reduced quantity and replace it in its compartment.
			// To preserve other fields, create a new Stock instance.
			Stock reduced = new Stock(stock.getName(), stock.getQuantity() - amount, stock.getCondition(),
					stock.getQualities(), stock.getExpirationDate(), stock.getStockType());

			this.inventory.getCompartments().forEach(comp -> {
				int idx = comp.getStoredStock().indexOf(stock);
				if (idx >= 0) {
					comp.getStoredStock().set(idx, reduced);
				}
			});
		}

		// record the change(s)
		Stock removedStock = new Stock(stock.getName(), amount, stock.getCondition(), stock.getQualities(),
				stock.getExpirationDate(), stock.getStockType());
		this.inventory.addStockChange(new StockChange(removedStock, Session.getCurrentUser(), ActionType.REMOVED));

		this.applyFilters();
		return null;
	}
}