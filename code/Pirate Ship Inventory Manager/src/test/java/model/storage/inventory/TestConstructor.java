package model.storage.inventory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Inventory;

class TestConstructor {

	@Test
	public void testValidConstructor() {
		Inventory inventory = new Inventory();
		
		assertEquals(4, inventory.getCompartments().size(), "Checks the amount of compartments when initialized");
		
	}


}
