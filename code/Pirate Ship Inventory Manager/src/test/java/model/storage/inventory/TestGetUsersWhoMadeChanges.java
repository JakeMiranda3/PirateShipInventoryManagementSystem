package model.storage.inventory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Inventory;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.StockChange;

public class TestGetUsersWhoMadeChanges {

	@Test
	public void testUserWhoDidntMakeChange() {
		Inventory inventory = new Inventory();
		assertEquals(0, inventory.getUsersWhoMadeChanges().size(), "Gets the amount of users who made changes");
	}

	@Test
	public void testValidGetUsersWhoMadeChanges() {
		Inventory inventory = new Inventory();
		User user = new User("Jack", "Pass");
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);

		Stock stockItem = new Stock("Gold", 30, Condition.PERFECT, specialQualities, null, StockType.OTHER);
		StockChange stockChange = new StockChange(stockItem, user);

		inventory.addStockChange(stockChange);
		assertEquals(1, inventory.getStockChanges().size(), "Checks the size of the list");
		assertEquals(stockChange, inventory.getStockChanges().get(0), "Gets the item in the first index");
		assertEquals(user, inventory.getUsersWhoMadeChanges().get(0), "Gets the most recent user to make a change");

	}

	@Test
	public void testReturnsUsersInOrderOfAppearance() {
		Inventory inventory = new Inventory();
		User john = new User("John", "Doe");
		User jane = new User("Jane", "Doe");

		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		Stock stockItem = new Stock("Gold", 30, Condition.PERFECT, specialQualities, null, StockType.OTHER);

		inventory.addStockChange(new StockChange(stockItem, john));
		inventory.addStockChange(new StockChange(stockItem, jane));

		List<User> users = inventory.getUsersWhoMadeChanges();

		assertEquals(jane, users.get(0), "First user should be Jane");
		assertEquals(john, users.get(1), "Second user should be John");
	}

	@Test
	public void testGetUsersWhoMadeChangesDuplicateUsers() {
		Inventory inventory = new Inventory();
		User john = new User("John", "Doe");

		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		Stock stockItem = new Stock("Gold", 30, Condition.PERFECT, specialQualities, null, StockType.OTHER);

		inventory.addStockChange(new StockChange(stockItem, john));
		inventory.addStockChange(new StockChange(stockItem, john));

		List<User> users = inventory.getUsersWhoMadeChanges();

		assertEquals(1, users.size(), "Checks to see how many users made changes");
		assertEquals(john, users.get(0), "Ensures John was the only user who made changes");
	}

}
