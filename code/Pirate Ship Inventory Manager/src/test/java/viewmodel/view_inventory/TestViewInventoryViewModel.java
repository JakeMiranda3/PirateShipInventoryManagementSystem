package viewmodel.view_inventory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.*;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.*;
import edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel.ViewInventoryViewModel;
import java.util.EnumSet;

class TestViewInventoryViewModel {

	@BeforeEach
	void setUp() {
		Session.setInventory(new Inventory());
	}

	@AfterEach
	void tearDown() {
		Session.setInventory(null);
	}

	@Test
	void testEmptyInventoryShowsEmptyList() {
		ViewInventoryViewModel viewModel = new ViewInventoryViewModel();
		assertEquals(0, viewModel.getFilteredStock().size());
	}

	@Test
	void testFilterBySpecialQualityFlammable() {
		Inventory inventory = Session.getInventory();
		Compartment flammableCompartment = inventory.getCompartments().get(1);

		Stock flammableStock = new Stock("Gas", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.FLAMMABLE), null,
				StockType.OTHER);
		flammableCompartment.addStock(flammableStock);

		ViewInventoryViewModel viewModel = new ViewInventoryViewModel();
		viewModel.getSelectedQualityProperty().set(SpecialQuality.FLAMMABLE);
		viewModel.applyFilters();

		assertEquals(1, viewModel.getFilteredStock().size());
		assertEquals("Gas", viewModel.getFilteredStock().get(0).getName());
	}

	@Test
	void testFilterBySpecialQualityNone() {
		Inventory inventory = Session.getInventory();
		Compartment noneCompartment = inventory.getCompartments().get(0);

		Stock noneStock = new Stock("Regular", 5, Condition.USABLE, EnumSet.of(SpecialQuality.NONE), null,
				StockType.OTHER);
		noneCompartment.addStock(noneStock);

		ViewInventoryViewModel viewModel = new ViewInventoryViewModel();
		viewModel.getSelectedQualityProperty().set(SpecialQuality.NONE);
		viewModel.applyFilters();

		assertEquals(1, viewModel.getFilteredStock().size());
		assertEquals("Regular", viewModel.getFilteredStock().get(0).getName());
	}

	@Test
	void testFilterByCompartment() {
		Inventory inventory = Session.getInventory();
		Compartment flammableCompartment = inventory.getCompartments().get(1);
		Compartment liquidCompartment = inventory.getCompartments().get(2);

		Stock flammableStock = new Stock("Gas", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.FLAMMABLE), null,
				StockType.OTHER);
		Stock liquidStock = new Stock("Water", 5, Condition.USABLE, EnumSet.of(SpecialQuality.LIQUID), null,
				StockType.OTHER);

		flammableCompartment.addStock(flammableStock);
		liquidCompartment.addStock(liquidStock);

		ViewInventoryViewModel viewModel = new ViewInventoryViewModel();

		viewModel.getSelectedCompartmentProperty().set("Flammable Compartment");
		viewModel.applyFilters();
		assertEquals(1, viewModel.getFilteredStock().size());
		assertEquals("Gas", viewModel.getFilteredStock().get(0).getName());

		viewModel.getSelectedCompartmentProperty().set("Liquid Compartment");
		viewModel.applyFilters();
		assertEquals(1, viewModel.getFilteredStock().size());
		assertEquals("Water", viewModel.getFilteredStock().get(0).getName());

		viewModel.getSelectedCompartmentProperty().set("Non-Existent");
		viewModel.applyFilters();
		assertEquals(0, viewModel.getFilteredStock().size());
	}

	@Test
	void testFilterByBothQualityAndCompartment() {
		Inventory inventory = Session.getInventory();
		Compartment flammableCompartment = inventory.getCompartments().get(1);
		Compartment liquidCompartment = inventory.getCompartments().get(2);

		Stock flammableStock = new Stock("Gas", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.FLAMMABLE), null,
				StockType.OTHER);
		Stock liquidStock = new Stock("Water", 5, Condition.USABLE, EnumSet.of(SpecialQuality.LIQUID), null,
				StockType.OTHER);

		flammableCompartment.addStock(flammableStock);
		liquidCompartment.addStock(liquidStock);

		ViewInventoryViewModel viewModel = new ViewInventoryViewModel();

		viewModel.getSelectedQualityProperty().set(SpecialQuality.FLAMMABLE);
		viewModel.getSelectedCompartmentProperty().set("Flammable Compartment");
		viewModel.applyFilters();

		assertEquals(1, viewModel.getFilteredStock().size());
		assertEquals("Gas", viewModel.getFilteredStock().get(0).getName());
	}

	@Test
	void testClearFilters() {
		Inventory inventory = Session.getInventory();
		Compartment flammableCompartment = inventory.getCompartments().get(1);

		Stock stock = new Stock("Gas", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.FLAMMABLE), null,
				StockType.OTHER);
		flammableCompartment.addStock(stock);

		ViewInventoryViewModel viewModel = new ViewInventoryViewModel();

		viewModel.getSelectedQualityProperty().set(SpecialQuality.FLAMMABLE);
		viewModel.getSelectedCompartmentProperty().set("Flammable Compartment");
		viewModel.applyFilters();
		viewModel.clearFilters();

		assertEquals(1, viewModel.getFilteredStock().size());
		assertNull(viewModel.getSelectedQualityProperty().get());
		assertNull(viewModel.getSelectedCompartmentProperty().get());
	}

	@Test
	void testApplyFiltersWithNullValues() {
		Inventory inventory = Session.getInventory();
		Compartment flammableCompartment = inventory.getCompartments().get(1);

		Stock stock = new Stock("Gas", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.FLAMMABLE), null,
				StockType.OTHER);
		flammableCompartment.addStock(stock);

		ViewInventoryViewModel viewModel = new ViewInventoryViewModel();

		viewModel.applyFilters();
		assertEquals(1, viewModel.getFilteredStock().size());

		viewModel.getSelectedQualityProperty().set(null);
		viewModel.getSelectedCompartmentProperty().set(null);
		viewModel.applyFilters();
		assertEquals(1, viewModel.getFilteredStock().size());
	}
	
	@Test
	void testFilterByStockTypeFood() {
	    Inventory inventory = Session.getInventory();
	    Compartment noneCompartment = inventory.getCompartments().get(0); // NONE compartment
	    
	    Stock foodStock = new Stock("Bread", 3, Condition.PERFECT, 
	                               EnumSet.of(SpecialQuality.NONE), null, StockType.FOOD);
	    Stock otherStock = new Stock("Tools", 5, Condition.PERFECT, 
	                                EnumSet.of(SpecialQuality.NONE), null, StockType.OTHER);
	    
	    noneCompartment.addStock(foodStock);
	    noneCompartment.addStock(otherStock);
	    
	    ViewInventoryViewModel viewModel = new ViewInventoryViewModel();
	    
	    viewModel.getSelectedStockTypeProperty().set(StockType.FOOD);
	    viewModel.applyFilters();
	    
	    assertEquals(1, viewModel.getFilteredStock().size());
	    assertEquals("Bread", viewModel.getFilteredStock().get(0).getName());
	}

	@Test
	void testFilterByStockTypeOther() {
	    Inventory inventory = Session.getInventory();
	    Compartment noneCompartment = inventory.getCompartments().get(0); 
	    
	    Stock foodStock = new Stock("Bread", 3, Condition.PERFECT, 
	                               EnumSet.of(SpecialQuality.NONE), null, StockType.FOOD);
	    Stock otherStock = new Stock("Tools", 5, Condition.PERFECT, 
	                                EnumSet.of(SpecialQuality.NONE), null, StockType.OTHER);
	    
	    noneCompartment.addStock(foodStock);
	    noneCompartment.addStock(otherStock);
	    
	    ViewInventoryViewModel viewModel = new ViewInventoryViewModel();
	    
	    viewModel.getSelectedStockTypeProperty().set(StockType.OTHER);
	    viewModel.applyFilters();
	    
	    assertEquals(1, viewModel.getFilteredStock().size());
	    assertEquals("Tools", viewModel.getFilteredStock().get(0).getName());
	}

	@Test
	void testClearFiltersResetsStockType() {
	    Inventory inventory = Session.getInventory();
	    Compartment flammableCompartment = inventory.getCompartments().get(1); 

	    Stock stock = new Stock("Gas", 3, Condition.PERFECT, 
	                           EnumSet.of(SpecialQuality.FLAMMABLE), null, StockType.OTHER);
	    flammableCompartment.addStock(stock);
	    
	    ViewInventoryViewModel viewModel = new ViewInventoryViewModel();
	    
	    viewModel.getSelectedStockTypeProperty().set(StockType.OTHER);
	    viewModel.applyFilters();
	    viewModel.clearFilters();
	    
	    assertEquals(1, viewModel.getFilteredStock().size());
	    assertNull(viewModel.getSelectedStockTypeProperty().get());
	}
}