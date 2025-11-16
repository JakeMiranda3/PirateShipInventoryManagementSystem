package model.storage.compartment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Compartment;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;

class TestAddStock {

	@Test
	public void testWhenCompartmentCantFitStock() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		Stock stock1 = new Stock("Gold", 70, Condition.PERFECT, specialQualities, null);

		Compartment compartment = new Compartment("Regular Compartment", 60, SpecialQuality.NONE);

		assertThrows(IllegalStateException.class, () -> {
			compartment.addStock(stock1);
		});
	}

	@Test
	public void testWhenCompartmentCantStoreNoSpecialQualityStock() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.LIQUID);
		Stock stock1 = new Stock("Water", 30, Condition.PERFECT, specialQualities, null);

		Compartment compartment = new Compartment("Liquid Compartment", 60, SpecialQuality.NONE);

		assertThrows(IllegalStateException.class, () -> {
			compartment.addStock(stock1);
		});
	}

	@Test
	public void testWhenCompartmentCantStoreSpecialQualityStock() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.LIQUID);
		Stock stock1 = new Stock("Water", 30, Condition.PERFECT, specialQualities, null);

		Compartment compartment = new Compartment("Liquid Compartment", 60, SpecialQuality.FLAMMABLE);

		assertThrows(IllegalStateException.class, () -> {
			compartment.addStock(stock1);
		});
	}

	@Test
	public void testWhenCompartmentHasNoSpecialQuality() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		Stock stock1 = new Stock("Gold", 30, Condition.PERFECT, specialQualities, null);

		Compartment compartment = new Compartment("Regular Compartment", 70, SpecialQuality.NONE);
		compartment.addStock(stock1);

		assertTrue(compartment.canStoreStock(stock1), "Checks if the compartment can store stock");
		assertEquals(40, compartment.getRemainingCapacity(), "Checks the compartment remaining capacity");
		assertEquals(30, compartment.getUsedCapacity(), "Checks the compartment used capacity");
		assertEquals(1, compartment.getStoredStock().size(), "Checks how many stock items are in the compartment");
	}

	@Test
	public void testWhenStockHasMultipleSpecialQualities() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.LIQUID);
		specialQualities.add(SpecialQuality.FLAMMABLE);
		Stock stock1 = new Stock("Gas", 30, Condition.PERFECT, specialQualities, null);

		Compartment compartment = new Compartment("Regular Compartment", 70, SpecialQuality.FLAMMABLE);
		compartment.addStock(stock1);

		assertTrue(compartment.canStoreStock(stock1), "Checks if the compartment can store stock");
		assertEquals(40, compartment.getRemainingCapacity(), "Checks the compartment remaining capacity");
		assertEquals(30, compartment.getUsedCapacity(), "Checks the compartment used capacity");
		assertEquals(1, compartment.getStoredStock().size(), "Checks how many stock items are in the compartment");
	}

}
