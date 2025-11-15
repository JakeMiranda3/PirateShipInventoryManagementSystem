package edu.westga.cs3211.pirate_ship_inventory_manager.model.storage;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;

public class Inventory {

	private List<Compartment> compartments;

	public Inventory() {
		this.compartments = new ArrayList<>();
		this.initializeCompartments();
	}

	public void addCompartment(Compartment compartment) {
		if (compartment == null) {
			throw new IllegalArgumentException("Compartment cant be null");
		}
		this.compartments.add(compartment);
	}

	public List<Compartment> getCompartments() {
		return this.compartments;
	}

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
