package edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel;

import java.util.List;
import java.util.stream.Collectors;

import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Inventory;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;

/**
 * The Class ViewInventoryViewModel.
 * 
 * @author fmensah1
 * @version fall 2025
 */
public class ViewInventoryViewModel {

    /** The inventory. */
    private Inventory inventory; 
    
    /** The filtered stock. */
    private ObservableList<Stock> filteredStock; 
    
    /** The selected quality. */
    private ObjectProperty<SpecialQuality> selectedQuality;
    
    /** The selected compartment. */
    private ObjectProperty<String> selectedCompartment;

	private ObjectProperty<StockType> selectedStockType;

    /**
     * Instantiates a new view inventory view model.
     */
    public ViewInventoryViewModel() { 
        this.inventory = Session.getInventory();
        this.filteredStock = FXCollections.observableArrayList(this.inventory.getStockItems());
        this.selectedQuality = new SimpleObjectProperty<>(null);
        this.selectedCompartment = new SimpleObjectProperty<>(null);
        this.selectedStockType = new SimpleObjectProperty<>(null);
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
     * Gets the selected quality property.
     *
     * @return the selected quality property
     */
    public ObjectProperty<SpecialQuality> getSelectedQualityProperty() {
        return this.selectedQuality;
    }

    /**
     * Gets the selected compartment property.
     *
     * @return the selected compartment property
     */
    public ObjectProperty<String> getSelectedCompartmentProperty() {
        return this.selectedCompartment;
    }
    
    /**
     * Gets the selected stock type property. 
     *
     * @return the selected stock type property
     */
    public ObjectProperty<StockType> getSelectedStockTypeProperty() {
        return this.selectedStockType;
    }
    
    /**
     * Apply filters.
     */
    public void applyFilters() {
        List<Stock> filtered = this.inventory.getStockItems();

        if (this.selectedQuality.get() != null) {
            SpecialQuality quality = this.selectedQuality.get();
            filtered = filtered.stream()
                    .filter(stock -> stock.getQualities().contains(quality))
                    .collect(Collectors.toList());
        }

        if (this.selectedCompartment.get() != null) {
        	   String compartmentName = this.selectedCompartment.get();
        	    filtered = filtered.stream()
        	            .filter(stock -> {
        	                return this.inventory.getCompartments().stream()
        	                    .filter(compartment -> compartment.getStoredStock().contains(stock))
        	                    .findFirst()
        	                    .map(compartment -> compartment.getCompartmentName().equals(compartmentName))
        	                    .orElse(false); 
        	            })
        	            .collect(Collectors.toList());
        }
        
        if (this.selectedStockType.get() != null) {
            StockType stockType = this.selectedStockType.get();
            filtered = filtered.stream()
                    .filter(stock -> stock.getStockType().equals(stockType))
                    .collect(Collectors.toList());
        }

        this.filteredStock.setAll(filtered);
    }

    /**
     * Clear filters.
     */
    public void clearFilters() {
        this.selectedQuality.set(null);
        this.selectedCompartment.set(null);
        this.selectedStockType.set(null);
        this.filteredStock.setAll(this.inventory.getStockItems());
    }
}
