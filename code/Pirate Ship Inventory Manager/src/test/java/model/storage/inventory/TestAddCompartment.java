package model.storage.inventory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Compartment;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Inventory;

public class TestAddCompartment {

	@Test
	public void testWhenAddCompartmentIsNull() {
		Inventory inventory = new Inventory();
		
		assertThrows(IllegalArgumentException.class, () -> {
			inventory.addCompartment(null);
		});
	}
	
	@Test
	public void testWhenAddCompartmentIsValid() {
		Inventory inventory = new Inventory();
		Compartment compartment = new Compartment("Regular Compartment", 60, SpecialQuality.NONE);
		
		inventory.addCompartment(compartment);
		
		assertEquals(5, inventory.getCompartments().size(), "Checks how many compartments are in inventory, 4 are created upon initialization");
	}

}
