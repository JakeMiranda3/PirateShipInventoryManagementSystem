package edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.StockChange;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

	/** The start hour. */
	private IntegerProperty startHour;

	/** The start minute. */
	private IntegerProperty startMinute;

	/** The end hour. */
	private IntegerProperty endHour;

	/** The end minute. */
	private IntegerProperty endMinute;

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

		this.startHour = new SimpleIntegerProperty(0);
		this.startMinute = new SimpleIntegerProperty(0);
		this.endHour = new SimpleIntegerProperty(23);
		this.endMinute = new SimpleIntegerProperty(59);

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
	 * Start hour property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty startHourProperty() {
		return this.startHour;
	}

	/**
	 * Start minute property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty startMinuteProperty() {
		return this.startMinute;
	}

	/**
	 * End hour property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty endHourProperty() {
		return this.endHour;
	}

	/**
	 * End minute property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty endMinuteProperty() {
		return this.endMinute;
	}

	/**
	 * Apply filters based on UI selection.
	 */
	public void applyFilters() {
		if (this.startDate.get() == null || this.endDate.get() == null) {
			if (this.startHour.get() != 0 || this.startMinute.get() != 0 || this.endHour.get() != 23
					|| this.endMinute.get() != 59) {
				throw new IllegalArgumentException(
						"Please set both start and end date before applying the time filters.");
			}
		}

		LocalDateTime localStartDateTime = null;
		LocalDateTime localEndDateTime = null;

		if (this.startDate.get() != null) {
			localStartDateTime = this.startDate.get().atTime(this.startHour.get(), this.startMinute.get());
		}

		if (this.endDate.get() != null) {
			localEndDateTime = this.endDate.get().atTime(this.endHour.get(), this.endMinute.get());
		}

		final LocalDateTime finalStart = localStartDateTime;
		final LocalDateTime finalEnd = localEndDateTime;

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

		if (finalStart != null && finalEnd != null) {
			if (!finalEnd.isAfter(finalStart)) {
				throw new IllegalArgumentException("End date/time must be after start date");
			}
		}

		if (finalStart != null) {
			filtered = filtered.stream().filter(stockChange -> !stockChange.getTimeAdded().isBefore(finalStart))
					.collect(Collectors.toList());
		}

		if (finalEnd != null) {
			filtered = filtered.stream().filter(stockChange -> !stockChange.getTimeAdded().isAfter(finalEnd))
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
		this.startHour.set(0);
		this.startMinute.set(0);
		this.endHour.set(23);
		this.endMinute.set(59);
		this.filteredChanges.setAll(this.allChanges);

	}

}
