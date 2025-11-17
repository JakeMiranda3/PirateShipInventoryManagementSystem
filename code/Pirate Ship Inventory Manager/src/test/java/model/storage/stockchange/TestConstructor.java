package model.storage.stockchange;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.StockChange;

public class TestConstructor {

	@Test
	public void testWhenStockIsNull() {
		User user = new User("John", "Doe");
		assertThrows(IllegalArgumentException.class, () -> {
			new StockChange(null, user);

		});
	}

	@Test
	public void testWhenUserIsNull() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.PERISHABLE);
		LocalDate date = LocalDate.parse("2025-11-16");
		Stock stockItem = new Stock("Gold", 30, Condition.UNUSABLE, specialQualities, date);

		assertThrows(IllegalArgumentException.class, () -> {
			new StockChange(stockItem, null);

		});
	}

	@Test
	public void testValidConstructor() {
		User user = new User("John", "Doe");
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.PERISHABLE);
		LocalDate date = LocalDate.parse("2025-11-16");
		Stock stockItem = new Stock("Gold", 30, Condition.UNUSABLE, specialQualities, date);

		StockChange stockChange = new StockChange(stockItem, user);

		assertEquals(stockItem, stockChange.getStock(), "Checks if stock was added correctly");
		assertEquals(user, stockChange.getUser(), "Checks if user was added correctly");
		assertNotNull(stockChange.getTimeAdded(), "Ensures time added exist");

	}

}
