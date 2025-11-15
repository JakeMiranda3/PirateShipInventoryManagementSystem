package edu.westga.cs3211.pirate_ship_inventory_manager.model.storage;

import java.util.ArrayList;
import java.util.List;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;

public class Compartment {

	private int maxCapacity;
	private int usedCapacity;
	private SpecialQuality allowedQuality;
	private List<Stock> storedStock;

	public Compartment(int maxCapacity, SpecialQuality allowedQuality) {

		if (maxCapacity <= 0) {
			throw new IllegalArgumentException("Max capacity must be greater than zero");
		}

		if (allowedQuality == null) {
			throw new IllegalArgumentException("Allowed special quality cant be null");
		}

		this.maxCapacity = maxCapacity;
		this.usedCapacity = 0;
		this.allowedQuality = allowedQuality;
		this.storedStock = new ArrayList<>();

	}

	public int getMaxCapacity() {
		return this.maxCapacity;
	}

	public int getUsedCapacity() {
		return this.usedCapacity;
	}

	public int getRemainingCapacity() {
		return this.maxCapacity - this.usedCapacity;
	}

	public SpecialQuality getAllowedQuality() {
		return this.allowedQuality;
	}

	public void addStock(Stock stock) {
		if (!this.canStoreStock(stock)) {
			throw new IllegalStateException("Compartment cannot store this type of stock.");
		}

		this.storedStock.add(stock);
		this.usedCapacity += stock.getQuantity();
	}

	public List<Stock> getStoredStock() {
		return this.storedStock;
	}

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
