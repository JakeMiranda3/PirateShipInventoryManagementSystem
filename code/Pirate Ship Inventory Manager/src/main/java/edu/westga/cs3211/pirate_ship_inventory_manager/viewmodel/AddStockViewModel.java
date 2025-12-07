package edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Compartment;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

/**
 * View Model for AddStock
 * 
 * @author Jake Miranda
 * @version Fall 2025
 */
public class AddStockViewModel {

	private StringProperty name;
	private IntegerProperty quantity;
	private ObjectProperty<Condition> condition;
	private ObjectProperty<StockType> stockType;

	private BooleanProperty noneSelected;
	private BooleanProperty flammableSelected;
	private BooleanProperty liquidSelected;
	private BooleanProperty perishableSelected;
	private BooleanProperty expirationDateEnabled;

	private ObjectProperty<LocalDate> expirationDate;
	private ObjectProperty<Compartment> selectedCompartment;

	/**
	 * Instantiates a new adds the stock view model.
	 */
	public AddStockViewModel() {
		this.name = new SimpleStringProperty("");
		this.quantity = new SimpleIntegerProperty(1);
		this.condition = new SimpleObjectProperty<>();
		this.stockType = new SimpleObjectProperty<>();

		this.noneSelected = new SimpleBooleanProperty(false);
		this.flammableSelected = new SimpleBooleanProperty(false);
		this.liquidSelected = new SimpleBooleanProperty(false);
		this.perishableSelected = new SimpleBooleanProperty(false);
		this.expirationDateEnabled = new SimpleBooleanProperty(false);
		this.expirationDate = new SimpleObjectProperty<>(null);

		this.selectedCompartment = new SimpleObjectProperty<>();

		this.setupSpecialQualityRules();
	}

	private void setupSpecialQualityRules() {
		this.noneSelected.addListener((obs, oldVal, newVal) -> {
			if (newVal) {
				this.flammableSelected.set(false);
				this.liquidSelected.set(false);
				this.perishableSelected.set(false);
				this.noneSelected.set(true);
			}
		});

		this.perishableSelected.addListener((obs, oldVal, newVal) -> {
			if (!newVal) {
				this.expirationDate.set(null);
			}
			this.expirationDateEnabled.set(newVal);
		});

		ChangeListener<Boolean> otherSelectedListener = (obs, oldVal, newVal) -> {
			if (this.flammableSelected.get() || this.liquidSelected.get() || this.perishableSelected.get()) {
				this.noneSelected.set(false);
			}
		};

		this.flammableSelected.addListener(otherSelectedListener);
		this.liquidSelected.addListener(otherSelectedListener);
		this.perishableSelected.addListener(otherSelectedListener);
	}

	/**
	 * Builds the special qualities.
	 *
	 *
	 * @return the sets the
	 */
	public Set<SpecialQuality> buildSpecialQualities() {
		Set<SpecialQuality> qualities = EnumSet.noneOf(SpecialQuality.class);

		if (this.noneSelected.get()) {
			qualities.add(SpecialQuality.NONE);
		}

		if (this.flammableSelected.get()) {
			qualities.add(SpecialQuality.FLAMMABLE);
		}

		if (this.liquidSelected.get()) {
			qualities.add(SpecialQuality.LIQUID);
		}

		if (this.perishableSelected.get()) {
			qualities.add(SpecialQuality.PERISHABLE);
		}

		return qualities;
	}

	/**
	 * Name property.
	 *
	 * @return the string property
	 */
	public StringProperty nameProperty() {
		return this.name;
	}

	/**
	 * Quantity property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty quantityProperty() {
		return this.quantity;
	}

	/**
	 * Condition property.
	 *
	 * @return the object property
	 */
	public ObjectProperty<Condition> conditionProperty() {
		return this.condition;
	}

	/**
	 * Stock type property.
	 *
	 * @return the object property
	 */
	public ObjectProperty<StockType> stockTypeProperty() {
		return this.stockType;
	}

	/**
	 * None selected property.
	 *
	 * @return the boolean property
	 */
	public BooleanProperty noneSelectedProperty() {
		return this.noneSelected;
	}

	/**
	 * Flammable selected property.
	 *
	 * @return the boolean property
	 */
	public BooleanProperty flammableSelectedProperty() {
		return this.flammableSelected;
	}

	/**
	 * Liquid selected property.
	 *
	 * @return the boolean property
	 */
	public BooleanProperty liquidSelectedProperty() {
		return this.liquidSelected;
	}

	/**
	 * Perishable selected property.
	 *
	 * @return the boolean property
	 */
	public BooleanProperty perishableSelectedProperty() {
		return this.perishableSelected;
	}

	/**
	 * Expiration date property.
	 *
	 * @return the object property
	 */
	public ObjectProperty<LocalDate> expirationDateProperty() {
		return this.expirationDate;
	}

	/**
	 * Selected compartment property.
	 *
	 * @return the object property
	 */
	public ObjectProperty<Compartment> selectedCompartmentProperty() {
		return this.selectedCompartment;
	}

	/**
	 * Expiration date enabled property.
	 *
	 * @return the boolean property
	 */
	public BooleanProperty expirationDateEnabledProperty() {
		return this.expirationDateEnabled;
	}

	/**
	 * Validates all input
	 *
	 * @return the string returns null if input is valid, otherwise leaves a self
	 *         documenting error message
	 */
	public String validateInput() {
		if (this.name.get() == null || this.name.get().isBlank()) {
			return "Stock name cannot be empty.";
		}

		if (this.quantity.get() <= 0) {
			return "Quantity must be greater than zero.";
		}

		if (this.condition.get() == null) {
			return "Please select a condition.";
		}

		Set<SpecialQuality> qualities = this.buildSpecialQualities();
		if (qualities.isEmpty()) {
			return "Please select at least one special quality.";
		}

		if (this.stockType.get() == null) {
			return "Please select a stock type.";
		}

		if (qualities.contains(SpecialQuality.PERISHABLE) && this.expirationDate.get() == null) {
			return "Perishable items must have an expiration date.";
		}

		if (!qualities.contains(SpecialQuality.PERISHABLE) && this.expirationDate.get() != null) {
			return "Only perishable items can have an expiration date.";
		}

		return null;
	}

}
