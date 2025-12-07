package viewmodel.view_stock_changes;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Inventory;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.StockChange;
import edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel.ViewStockChangesViewModel;

class TestViewStockChangesViewModel {

	@Test
	public void testNoFilter() {
		Session.setInventory(new Inventory());

		User user = new User("John", "Doe");
		User user2 = new User("Jane", "Doe");
		Set<SpecialQuality> specialQual1 = new HashSet<SpecialQuality>();
		specialQual1.add(SpecialQuality.FLAMMABLE);

		Stock flammable = new Stock("Gas", 10, Condition.PERFECT, specialQual1, null, StockType.Other);

		Session.getInventory().addStockChange(new StockChange(flammable, user));
		Session.getInventory().addStockChange(new StockChange(flammable, user2));

		ViewStockChangesViewModel viewModel = new ViewStockChangesViewModel();

		assertEquals(2, viewModel.getFilteredChangesProperty().size(),
				"Ensures after filters that the specific crewmate shows up");
		assertEquals("Jane", viewModel.getFilteredChangesProperty().get(0).getUser().getName(),
				"Ensures after the filters that the specific crewmate matches the name");
		assertEquals("John", viewModel.getFilteredChangesProperty().get(1).getUser().getName(),
				"Ensures after the filters that the specific crewmate matches the name");
	}

	@Test
	public void testFilterByFlammableSpecialQualityOnly() {
		Session.setInventory(new Inventory());

		User user = new User("John", "Doe");
		Set<SpecialQuality> specialQual1 = new HashSet<SpecialQuality>();
		specialQual1.add(SpecialQuality.FLAMMABLE);
		Set<SpecialQuality> specialQual2 = new HashSet<SpecialQuality>();
		specialQual2.add(SpecialQuality.LIQUID);

		Stock flammable = new Stock("Gas", 10, Condition.PERFECT, specialQual1, null, StockType.Other);
		Stock liquid = new Stock("Water", 5, Condition.PERFECT, specialQual2, null, StockType.Other);

		Session.getInventory().addStockChange(new StockChange(flammable, user));
		Session.getInventory().addStockChange(new StockChange(liquid, user));

		ViewStockChangesViewModel viewModel = new ViewStockChangesViewModel();

		viewModel.getSelectedSpecialQuality().set(SpecialQuality.FLAMMABLE);
		viewModel.applyFilters();

		assertEquals(1, viewModel.getFilteredChangesProperty().size(), "Checks the size after filtering");
		assertEquals("Gas", viewModel.getFilteredChangesProperty().get(0).getStock().getName(),
				"Checks the stock name after filtering");
	}

	@Test
	public void testFilterBySpecificCrewmate() {
		Session.setInventory(new Inventory());

		User user = new User("John", "Doe");
		User user2 = new User("Jane", "Doe");
		Set<SpecialQuality> specialQual1 = new HashSet<SpecialQuality>();
		specialQual1.add(SpecialQuality.FLAMMABLE);

		Stock flammable = new Stock("Gas", 10, Condition.PERFECT, specialQual1, null, StockType.Other);

		Session.getInventory().addStockChange(new StockChange(flammable, user));
		Session.getInventory().addStockChange(new StockChange(flammable, user2));

		ViewStockChangesViewModel viewModel = new ViewStockChangesViewModel();
		viewModel.getSelectedCrewmateProperty().set(user);
		viewModel.applyFilters();

		assertEquals(1, viewModel.getFilteredChangesProperty().size(),
				"Ensures after filters that the specific crewmate shows up");
		assertEquals("John", viewModel.getFilteredChangesProperty().get(0).getUser().getName(),
				"Ensures after the filters that the specific crewmate matches the name");

	}

	@Test
	public void testWhenMockConstructorHasNullStock() {
		User user = new User("John", "Doe");
		LocalDateTime currentDay = LocalDateTime.now();
		assertThrows(IllegalArgumentException.class, () -> {
			new StockChange(null, user, currentDay);

		});
	}

	@Test
	public void testWhenUserIsNull() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.PERISHABLE);
		LocalDate date = LocalDate.parse("2025-11-16");
		Stock stockItem = new Stock("Gold", 30, Condition.UNUSABLE, specialQualities, date, StockType.Other);
		LocalDateTime currentDay = LocalDateTime.now();

		assertThrows(IllegalArgumentException.class, () -> {
			new StockChange(stockItem, null, currentDay);

		});
	}

	@Test
	public void testFilterByStartDate() {
		Session.setInventory(new Inventory());

		User user = new User("John", "Doe");
		Set<SpecialQuality> specialQual1 = new HashSet<SpecialQuality>();
		specialQual1.add(SpecialQuality.FLAMMABLE);

		Stock flammable = new Stock("Gas", 10, Condition.PERFECT, specialQual1, null, StockType.Other);
		LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
		LocalDateTime currentDay = LocalDateTime.now();
		StockChange change1 = new StockChange(flammable, user, yesterday);
		StockChange change2 = new StockChange(flammable, user, currentDay);

		Session.getInventory().addStockChange(change1);
		Session.getInventory().addStockChange(change2);

		ViewStockChangesViewModel viewModel = new ViewStockChangesViewModel();

		viewModel.startDateProperty().set(LocalDate.now());

		viewModel.applyFilters();

		assertEquals(1, viewModel.getFilteredChangesProperty().size(), "Checks the size after the filter is applied");
		assertTrue(viewModel.getFilteredChangesProperty().contains(change2),
				"Checks the list after the filter is applied");

	}

	@Test
	public void testFilterByEndDate() {
		Session.setInventory(new Inventory());

		User user = new User("John", "Doe");
		Set<SpecialQuality> specialQual1 = new HashSet<SpecialQuality>();
		specialQual1.add(SpecialQuality.FLAMMABLE);
		Stock flammable = new Stock("Gas", 10, Condition.PERFECT, specialQual1, null, StockType.Other);

		LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
		LocalDateTime currentDay = LocalDateTime.now();

		StockChange change1 = new StockChange(flammable, user, yesterday);
		StockChange change2 = new StockChange(flammable, user, currentDay);

		Session.getInventory().addStockChange(change1);
		Session.getInventory().addStockChange(change2);

		ViewStockChangesViewModel viewModel = new ViewStockChangesViewModel();

		viewModel.endDateProperty().set(LocalDate.now().minusDays(1));

		viewModel.applyFilters();

		assertEquals(1, viewModel.getFilteredChangesProperty().size(), "Checks the size after the filter is applied");
		assertTrue(viewModel.getFilteredChangesProperty().contains(change1),
				"Checks the list after the filter is applied");

	}

	@Test
	public void testInvalidDateRangeThrowsException() {
		Session.setInventory(new Inventory());

		ViewStockChangesViewModel viewModel = new ViewStockChangesViewModel();

		viewModel.startDateProperty().set(LocalDate.now());
		viewModel.endDateProperty().set(LocalDate.now().minusDays(1));

		assertThrows(IllegalArgumentException.class, () -> {
			viewModel.applyFilters();

		});

	}

	@Test
	public void TestvalidDateRange() {
		Session.setInventory(new Inventory());

		User user = new User("John", "Doe");
		Set<SpecialQuality> specialQual1 = new HashSet<>();
		specialQual1.add(SpecialQuality.FLAMMABLE);
		Stock stock = new Stock("Gas", 10, Condition.PERFECT, specialQual1, null, StockType.Other);

		StockChange stockChange1 = new StockChange(stock, user, LocalDateTime.now().minusDays(3));
		StockChange stockChange2 = new StockChange(stock, user, LocalDateTime.now());

		Session.getInventory().addStockChange(stockChange1);
		Session.getInventory().addStockChange(stockChange2);

		ViewStockChangesViewModel viewModel = new ViewStockChangesViewModel();

		viewModel.startDateProperty().set(LocalDate.now().minusDays(5));
		viewModel.endDateProperty().set(LocalDate.now());
		viewModel.applyFilters();

		assertEquals(2, viewModel.getFilteredChangesProperty().size(), "Valid date range");

	}

	@Test
	public void testClearFilters() {
		Session.setInventory(new Inventory());

		User user = new User("John", "Doe");
		Set<SpecialQuality> specialQual1 = new HashSet<SpecialQuality>();
		specialQual1.add(SpecialQuality.FLAMMABLE);
		Set<SpecialQuality> specialQual2 = new HashSet<SpecialQuality>();
		specialQual2.add(SpecialQuality.LIQUID);

		Stock flammable = new Stock("Gas", 10, Condition.PERFECT, specialQual1, null, StockType.Other);
		Stock liquid = new Stock("Water", 5, Condition.PERFECT, specialQual2, null, StockType.Other);

		Session.getInventory().addStockChange(new StockChange(flammable, user));
		Session.getInventory().addStockChange(new StockChange(liquid, user));

		ViewStockChangesViewModel viewModel = new ViewStockChangesViewModel();

		viewModel.getSelectedSpecialQuality().set(SpecialQuality.FLAMMABLE);
		viewModel.applyFilters();

		viewModel.clearFilters();

		assertEquals(2, viewModel.getFilteredChangesProperty().size(),
				"Checks the size of the list after clearing the filters");
		assertNull(viewModel.getSelectedSpecialQuality().get(), "Checks the value of selected special quality");
		assertNull(viewModel.getSelectedCrewmateProperty().get(), "Checks the value of selected crewmate");
		assertNull(viewModel.startDateProperty().get(), "Checks the value of selected start date");
		assertNull(viewModel.endDateProperty().get(), "Checks the value of selected end date");
	}

}
