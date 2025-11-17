package model.storage.inventory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Compartment;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Inventory;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;

class TestAddStockToCompartment {

	@Test
	public void testWhenStockIsNull() {
		Inventory inventory = new Inventory();

		assertThrows(IllegalArgumentException.class, () -> {
			inventory.addStockToCompartment(null, inventory.getCompartments().get(0));
		});
	}

	@Test
	public void testWhenCompartmentIsNull() {
		Inventory inventory = new Inventory();
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		Stock stock = new Stock("Gold", 1, Condition.PERFECT, specialQualities, null);

		assertThrows(IllegalArgumentException.class, () -> {
			inventory.addStockToCompartment(stock, null);
		});
	}

	@Test
	public void testWhenAddStockIsValid() {
		Inventory inventory = new Inventory();
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		Stock stock = new Stock("Gold", 1, Condition.PERFECT, specialQualities, null);

		inventory.addStockToCompartment(stock, inventory.getCompartments().get(0));

		Compartment currentCompartment = inventory.getCompartments().get(0);

		assertEquals(1, currentCompartment.getUsedCapacity(), "Checks how much capacity was used after add stock");
		assertEquals("Gold", currentCompartment.getStoredStock().get(0).getName(),
				"Checks the name of the stock added");
	}

}
