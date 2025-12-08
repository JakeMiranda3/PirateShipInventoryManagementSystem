package model.storage.stock;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.EnumSet;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;

class TestStockToString {

	  @Test
	    void testToStringWithNoExpiration() {
	        Stock stock = new Stock("Gold", 10, Condition.PERFECT, 
	                               EnumSet.of(SpecialQuality.NONE), null, StockType.OTHER);
	        
	        String expected = "Gold (10 units) - PERFECT";
	        assertEquals(expected, stock.toString());
	    }
	    
	    @Test
	    void testToStringWithExpiration() {
	        LocalDate expiration = LocalDate.of(2025, 12, 25);
	        Stock stock = new Stock("Milk", 5, Condition.PERFECT, 
	                               EnumSet.of(SpecialQuality.PERISHABLE), expiration, StockType.OTHER);
	        
	        String expected = "Milk (5 units) - PERFECT";
	        assertEquals(expected, stock.toString());
	    }
	    
	    @Test
	    void testToStringWithDifferentQuantities() {
	        Stock stock1 = new Stock("Item1", 1, Condition.UNUSABLE, 
	                                EnumSet.of(SpecialQuality.NONE), null, StockType.OTHER);
	        Stock stock2 = new Stock("Item2", 100, Condition.PERFECT, 
	                                EnumSet.of(SpecialQuality.NONE), null, StockType.OTHER);
	        
	        assertEquals("Item1 (1 units) - UNUSABLE", stock1.toString());
	        assertEquals("Item2 (100 units) - PERFECT", stock2.toString());
	    }

}
