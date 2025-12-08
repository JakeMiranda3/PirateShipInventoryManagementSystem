package model.storage.stockchange;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.ActionType;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.StockChange;

public class TestToString {

	@Test
	public void testToStringHasCorrectFormat() {
		User user = new User("John", "Doe");
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.PERISHABLE);
		LocalDate date = LocalDate.parse("2025-11-16");
		Stock stockItem = new Stock("Gold", 30, Condition.UNUSABLE, specialQualities, date, StockType.OTHER);
		StockChange stockChange = new StockChange(stockItem, user, ActionType.ADDED);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
		String timeAdded = stockChange.getTimeAdded().format(formatter);
		String expected = "John added 30 Gold at " + timeAdded;

		assertEquals(expected, stockChange.toString(), "Checks toString format");
	}

}
