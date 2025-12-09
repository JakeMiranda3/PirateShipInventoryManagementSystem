package model.storage.inventory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.ActionType;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Inventory;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.StockChange;

public class TestAddStockChange {

	@Test
	public void testWhenStockChangeIsNull() {
		Inventory inventory = new Inventory();

		assertThrows(IllegalArgumentException.class, () -> {
			inventory.addStockChange(null);
		});
	}

	@Test
	public void testValidAddStockChange() {
		Inventory inventory = new Inventory();
		User user = new User("Jack", "Pass");
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);

		Stock stockItem = new Stock("Gold", 30, Condition.PERFECT, specialQualities, null, StockType.OTHER);
		StockChange stockChange = new StockChange(stockItem, user, ActionType.ADDED);

		inventory.addStockChange(stockChange);
		assertEquals(1, inventory.getStockChanges().size(), "Checks the size of the list");
		assertEquals(stockChange, inventory.getStockChanges().get(0), "Gets the item in the first index");
		assertEquals(ActionType.ADDED, stockChange.getTypeOfStockChange(),
				"Checks the action type of the stock change");

	}

}
