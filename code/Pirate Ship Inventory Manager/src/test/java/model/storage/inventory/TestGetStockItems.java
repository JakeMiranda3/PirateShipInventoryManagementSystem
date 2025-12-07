package model.storage.inventory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Compartment;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Inventory;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;

class TestGetStockItems {

private Inventory inventory;
    
    @BeforeEach
    void setUp() {
        this.inventory = new Inventory();
    }
    
    @Test
    void testEmptyInventoryReturnsEmptyList() {
        assertTrue(this.inventory.getStockItems().isEmpty());
    }
    
    @Test
    void testAddStockToCompartmentThenGetStockItems() {
        Compartment compartment = this.inventory.getCompartments().get(0);
        Stock stock = new Stock("Test Item", 5, Condition.PERFECT, 
                               EnumSet.of(SpecialQuality.NONE), null);
        
        compartment.addStock(stock);
        
        assertEquals(1, this.inventory.getStockItems().size());
        assertTrue(this.inventory.getStockItems().contains(stock));
    }
}
