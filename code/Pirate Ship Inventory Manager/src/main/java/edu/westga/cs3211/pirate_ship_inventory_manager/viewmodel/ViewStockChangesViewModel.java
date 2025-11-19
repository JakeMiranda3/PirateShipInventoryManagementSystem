package edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.StockChange;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The ViewStockChanges ViewModel.
 * 
 * @author CS 3211
 * @version Fall 2025
 */
public class ViewStockChangesViewModel {

	/** The all changes. */
	private ObservableList<StockChange> allChanges;

	/** The filtered changes. */
	private ObservableList<StockChange> filteredChanges;

	/** The selected quality. */
	private ObjectProperty<SpecialQuality> selectedQuality;

	/** The selected crewmate. */
	private ObjectProperty<User> selectedCrewmate;

	/** The start date. */
	private ObjectProperty<LocalDate> startDate;

	/** The end date. */
	private ObjectProperty<LocalDate> endDate;

	/**
	 * Instantiates a new view stock changes view model.
	 */
	public ViewStockChangesViewModel() {
		this.allChanges = FXCollections.observableArrayList(Session.getInventory().getStockChanges());
		this.filteredChanges = FXCollections.observableArrayList(this.allChanges);
		this.selectedQuality = new SimpleObjectProperty<>(null);
		this.selectedCrewmate = new SimpleObjectProperty<>(null);

		this.startDate = new SimpleObjectProperty<>(null);
		this.endDate = new SimpleObjectProperty<>(null);

	}

	/**
	 * Gets the filtered changes property.
	 *
	 * @return the filtered changes property
	 */
	public ObservableList<StockChange> getFilteredChangesProperty() {
		return this.filteredChanges;
	}

	/**
	 * Gets the selected special quality.
	 *
	 * @return the selected special quality
	 */
	public ObjectProperty<SpecialQuality> getSelectedSpecialQuality() {
		return this.selectedQuality;
	}

	/**
	 * Gets the selected crewmate property.
	 *
	 * @return the selected crewmate property
	 */
	public ObjectProperty<User> getSelectedCrewmateProperty() {
		return this.selectedCrewmate;
	}

	/**
	 * Start date property.
	 *
	 * @return the object property
	 */
	public ObjectProperty<LocalDate> startDateProperty() {
		return this.startDate;
	}

	/**
	 * End date property.
	 *
	 * @return the object property
	 */
	public ObjectProperty<LocalDate> endDateProperty() {
		return this.endDate;
	}

	/**
	 * Apply filters based on UI selection.
	 */
	public void applyFilters() {
		List<StockChange> filtered = new ArrayList<>(this.allChanges);

		if (this.selectedQuality.get() != null) {
			SpecialQuality quality = this.selectedQuality.get();
			filtered = filtered.stream().filter(stockChange -> stockChange.getStock().getQualities().contains(quality))
					.collect(Collectors.toList());
		}

		if (this.selectedCrewmate.get() != null) {
			User selectedUser = this.selectedCrewmate.get();
			filtered = filtered.stream().filter(stockChange -> stockChange.getUser().equals(selectedUser))
					.collect(Collectors.toList());
		}

		LocalDate startDate = this.startDate.get();
		LocalDate endDate = this.endDate.get();
		if (startDate != null && endDate != null) {
			if (!endDate.isAfter(startDate)) {
				throw new IllegalArgumentException("End date must be after start date");
			}
		}

		if (startDate != null) {
			filtered = filtered.stream()
					.filter(stockChange -> !stockChange.getTimeAdded().toLocalDate().isBefore(startDate))
					.collect(Collectors.toList());
		}

		if (endDate != null) {
			filtered = filtered.stream()
					.filter(stockChange -> !stockChange.getTimeAdded().toLocalDate().isAfter(endDate))
					.collect(Collectors.toList());
		}

		filtered.sort(Comparator.comparing(StockChange::getTimeAdded).reversed());

		this.filteredChanges.setAll(filtered);
	}

	/**
	 * Clears the filters of the stock changes list.
	 */
	public void clearFilters() {
		this.selectedQuality.set(null);
		this.selectedCrewmate.set(null);
		this.startDate.set(null);
		this.endDate.set(null);

		this.filteredChanges.setAll(this.allChanges);
	}

}
